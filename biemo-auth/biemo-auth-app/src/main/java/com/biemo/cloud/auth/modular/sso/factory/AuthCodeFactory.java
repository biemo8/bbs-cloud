package com.biemo.cloud.auth.modular.sso.factory;

import cn.hutool.core.date.DateUtil;
import com.biemo.cloud.auth.modular.sso.model.AuthCode;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;

import java.util.Date;

/**
 * 授权码生成器，用于登录
 *
 *
 * @Date 2019/12/8 20:42
 */
public class AuthCodeFactory {

    /**
     * 授权码对应的缓存前缀
     */
    public static final String CODE_PREFIX = "AUTH_CODE_";

    /**
     * 授权码过期时间
     */
    public static final Long CODE_DEFAULT_EXPIRE_SECONDS = 300L;

    /**
     * 创建授权码
     *
     *
     * @Date 2019/12/8 20:44
     */
    public static AuthCode createAuthCode() {
        String code = IdWorker.get32UUID();
        AuthCode authCode = new AuthCode();
        authCode.setCode(code);
        authCode.setTime(DateUtil.formatDateTime(new Date()));
        authCode.setExpire(CODE_DEFAULT_EXPIRE_SECONDS);
        return authCode;
    }

}
