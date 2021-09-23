package com.biemo.cloud.auth.modular.sso.controller;

import cn.hutool.core.bean.BeanUtil;
import com.biemo.cloud.auth.modular.sso.factory.RedirectUrlBuilder;
import com.biemo.cloud.auth.modular.sso.model.AuthCode;
import com.biemo.cloud.auth.modular.sso.model.AuthReq;
import com.biemo.cloud.auth.modular.sso.model.SsoLoginReq;
import com.biemo.cloud.auth.modular.sso.service.AuthService;
import com.biemo.cloud.libs.error.exp.AccessCodeException;
import com.biemo.cloud.libs.error.exp.SpsAuthException;
import com.biemo.cloud.core.util.HttpContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * 登录退出控制器
 *
 *
 * @Date 2019/9/25 22:02
 */
@Controller
@RequestMapping("/login")
@Slf4j
public class LoginController {

    @Autowired
    private AuthService authService;

    /**
     * 登陆页面
     *
     *
     * @Date 2019-04-19 14:30
     */
    @RequestMapping(value = "/loginView", method = RequestMethod.GET)
    public String loginPage(@Validated SsoLoginReq ssoLoginReq) {

        //渲染客户端传来的参数
        HttpContext.getRequest().setAttribute("clientInfo", ssoLoginReq);

        //跳转到登录页面
        return "/sps/login.html";
    }

    /**
     * 登录认证（登陆页面表单提交）
     *
     *
     * @Date 2019/4/19 21:07
     */
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public String doAuth(AuthReq authReq) {

        //获取授权码
        AuthCode authCode;
        try {
            authCode = authService.accessCode(authReq.getAccount(), authReq.getPassword());
        } catch (AccessCodeException e) {
            Map<String, Object> paramsMap = BeanUtil.beanToMap(authReq);
            throw new SpsAuthException(e.getAuthExceptionEnum(), paramsMap);
        }

        //渲染到单点登录的地址
        return RedirectUrlBuilder
                .create("/sso/login")
                .add("clientId", authReq.getClientId().toString())
                .add("redirectUrl", authReq.getRedirectUrl())
                .add("code", authCode.getCode())
                .build();
    }

    /**
     * 退出页面
     *
     *
     * @Date 2019/9/25 22:02
     */
    @RequestMapping(value = "/logoutView", method = RequestMethod.GET)
    public String logoutPage(@Validated SsoLoginReq ssoLoginReq, Model model) {
        model.addAttribute("clientInfo", ssoLoginReq);
        return "/sps/logout.html";
    }

}
