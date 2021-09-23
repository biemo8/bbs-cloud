package com.biemo.cloud.auth.modular.sso.service;

import com.biemo.cloud.auth.api.context.LoginUser;
import com.biemo.cloud.auth.modular.sso.model.AuthCode;

/**
 * 鉴权服务
 *
 *
 * @Date 2019/9/25 22:33
 */
public interface AuthService {

    /**
     * token退出
     *
     *
     * @Date 2019/9/25 22:33
     */
    void remove(String token);

    /**
     * 获取AuthCode
     *
     *
     * @Date 2019/9/25 22:33
     */
    AuthCode accessCode(String account, String password);

    /**
     * 根据授权码获取登录用户
     *
     *
     * @Date 2019/9/25 22:33
     */
    LoginUser getLoginUser(String code);

}
