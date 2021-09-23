package com.biemo.cloud.auth.modular.sso.service;

import com.biemo.cloud.auth.api.context.LoginUser;
import com.biemo.cloud.auth.modular.sso.model.SsoLoginReq;

/**
 * SSO登录服务类
 *
 *
 * @since 2019/2/16 14:12
 */
public interface SsoService {

    /**
     * Sso登录服务
     *
     * @param ssoLoginReq 请求参数
     * @return java.lang.String sso返回重定向地址
     * @auther
     * @Date 2019/6/13 15:24
     */
    String ssoLogin(SsoLoginReq ssoLoginReq);

    /**
     * Sso登出服务
     *
     * @param ssoLoginReq 请求参数
     * @return java.lang.String sso返回重定向地址
     * @auther
     * @Date 2019/6/13 15:24
     */
    String ssoLogout(SsoLoginReq ssoLoginReq);

    /**
     * 登录认证成功后的业务，返回重定向地址
     *
     * @param ssoLoginReq 请求参数
     * @param loginUser   登录用户
     * @return 登录成功后重定向地址
     * @auther
     * @Date 2019/9/12 10:49
     */
    String afterLoginAuthentication(SsoLoginReq ssoLoginReq, LoginUser loginUser);

}
