package com.biemo.cloud.auth.modular.sso.service.impl;

import cn.hutool.core.util.StrUtil;
import com.biemo.cloud.auth.api.context.LoginUser;
import com.biemo.cloud.auth.modular.sso.entity.AuthClient;
import com.biemo.cloud.auth.modular.sso.enums.CodeEnum;
import com.biemo.cloud.auth.modular.sso.enums.LoginTypeEnum;
import com.biemo.cloud.auth.modular.sso.factory.RedirectUrlBuilder;
import com.biemo.cloud.auth.modular.sso.model.SsoLoginReq;
import com.biemo.cloud.auth.modular.sso.model.TokenInfo;
import com.biemo.cloud.auth.modular.sso.security.exception.SecurityException;
import com.biemo.cloud.auth.modular.sso.service.*;
import com.biemo.cloud.auth.modular.sso.util.CookieUtil;
import com.biemo.cloud.core.util.HttpContext;
import com.biemo.cloud.core.util.ToolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * sso登录服务实现类
 *
 *
 * @Date 2019/9/25 22:31
 */
@Service
public class SsoServiceImpl implements SsoService {

    @Autowired
    private AuthClientService authClientService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private LoginLogService authLoginLogService;

    @Resource
    private AuthService authService;

    @Override
    public String ssoLogin(SsoLoginReq ssoLoginReq) {

        // 判断APP是否正确
        AuthClient client = authClientService.getById(ssoLoginReq.getClientId());
        if (client == null || StrUtil.isEmpty(client.getPrivateKey())) {
            throw new SecurityException(CodeEnum.APP_IS_ERROR);
        }

        //统一认证平台重定向返回地址
        String ssoRedirectUrl;

        // 解析code 判断来源是否合法,不合法返回错误码,如果token为空则去拿session
        if (ToolUtil.isNotEmpty(ssoLoginReq.getCode())) {
            ssoRedirectUrl = ssoLoginWithCode(ssoLoginReq, client);
        } else {
            ssoRedirectUrl = ssoLoginWithoutCode(ssoLoginReq, client);
        }
        return ssoRedirectUrl;
    }

    @Override
    public String ssoLogout(SsoLoginReq ssoLoginReq) {

        // 判断APP是否正确
        AuthClient client = authClientService.getById(ssoLoginReq.getClientId());
        if (client == null || StrUtil.isEmpty(client.getPrivateKey())) {
            throw new SecurityException(CodeEnum.APP_IS_ERROR);
        }

        // 删除缓存和记录退出日志
        TokenInfo tokenInfo = sessionService.destroy(client);
        authLoginLogService.logoutLog(HttpContext.getRequest(), tokenInfo);
        return redirectLogoutPageUrl(ssoLoginReq, client, CodeEnum.TOKEN_IS_EXPIRED);
    }

    @Override
    public String afterLoginAuthentication(SsoLoginReq ssoLoginReq, LoginUser loginUser) {
        AuthClient authClient = authClientService.getById(ssoLoginReq.getClientId());
        return afterLoginAuthentication(authClient, ssoLoginReq, loginUser);
    }

    /**
     * 携带token创建全局会话,返回sso重定向地址
     *
     *
     * @Date 2019/9/25 22:31
     */
    private String ssoLoginWithCode(SsoLoginReq ssoLoginReq, AuthClient client) {
        LoginUser loginUser = authService.getLoginUser(ssoLoginReq.getCode());

        // 查看token是否过期，过期重定向回去
        if (loginUser == null) {
            return redirectLoginPageUrl(ssoLoginReq, client, CodeEnum.TOKEN_IS_EXPIRED);
        }
        return afterLoginAuthentication(client, ssoLoginReq, loginUser);

    }

    /**
     * 检查是否sso存在全局会话,返回sso重定向地址
     *
     *
     * @Date 2019/9/25 22:31
     */
    private String ssoLoginWithoutCode(SsoLoginReq ssoLoginReq, AuthClient client) {
        String ssoRedirectUrl;
        String ticket = CookieUtil.get(HttpContext.getRequest(), SessionServiceImpl.TICKET_ID);
        LoginUser loginUser = sessionService.getLoginUserByTicket(ticket);
        if (loginUser != null) {
            TokenInfo tokenInfo = sessionService.refresh(client, loginUser, ticket);
            authLoginLogService.loginLog(HttpContext.getRequest(), tokenInfo);
            ssoRedirectUrl = successLoginRedirect(ssoLoginReq, tokenInfo);
        } else {
            ssoRedirectUrl = redirectLoginPageUrl(ssoLoginReq, client, CodeEnum.TOKEN_IS_EXPIRED);
        }
        return ssoRedirectUrl;
    }

    /**
     * 重定向到登录地址
     * 根据配置跳转到统一登录页面或者是用户自定义登录页面
     *
     *
     * @Date 2019/9/25 22:31
     */
    private String redirectLoginPageUrl(SsoLoginReq ssoLoginReq, AuthClient client, CodeEnum codeEnum) {

        // 判断是否是统一登录页面
        if (isSinglePage(client)) {
            return RedirectUrlBuilder
                    .create("/login/loginView")
                    .add("clientId", client.getClientId().toString())
                    .add("redirectUrl", ssoLoginReq.getRedirectUrl())
                    .build();
        } else {
            // 跳转用户自定义登录页面
            return failureLoginRedirect(ssoLoginReq, client, codeEnum);
        }
    }

    /**
     * 重定向到登出地址
     * 根据配置跳转到统一登录页面或者是用户自定义登录页面
     *
     *
     * @Date 2019/9/25 22:31
     */
    private String redirectLogoutPageUrl(SsoLoginReq ssoLoginReq, AuthClient client, CodeEnum codeEnum) {

        // 判断是否是统一登录页面
        if (isSinglePage(client)) {
            return RedirectUrlBuilder
                    .create("/login/logoutView")
                    .add("clientId", client.getClientId().toString())
                    .add("redirectUrl", client.getSsoUrl())
                    .build();
        } else {
            // 跳转用户自定义登录页面
            return failureLoginRedirect(ssoLoginReq, client, codeEnum);
        }
    }

    private boolean isSinglePage(AuthClient client) {
        return LoginTypeEnum.SPS.getCode() == client.getLoginType();
    }

    private String afterLoginAuthentication(AuthClient authClient, SsoLoginReq ssoLoginReq, LoginUser loginUser) {
        TokenInfo tokenInfo = sessionService.create(authClient, loginUser);
        authLoginLogService.loginLog(HttpContext.getRequest(), tokenInfo);
        return successLoginRedirect(ssoLoginReq, tokenInfo);
    }

    private String successLoginRedirect(SsoLoginReq ssoLoginReq, TokenInfo tokenInfo) {
        return RedirectUrlBuilder
                .create(tokenInfo.getAuthClient().getSsoUrl())
                .add("redirectUrl", ssoLoginReq.getRedirectUrl())
                .add("token", tokenInfo.getToken())
                .build();
    }

    private String failureLoginRedirect(SsoLoginReq ssoLoginReq, AuthClient client, CodeEnum codeEnum) {
        return RedirectUrlBuilder
                .create(client.getSsoUrl())
                .add("redirectUrl", ssoLoginReq.getRedirectUrl())
                .add("errCode", codeEnum.getCode().toString())
                .build();
    }
}
