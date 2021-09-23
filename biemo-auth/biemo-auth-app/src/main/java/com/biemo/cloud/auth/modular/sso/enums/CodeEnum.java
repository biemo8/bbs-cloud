package com.biemo.cloud.auth.modular.sso.enums;

import com.biemo.cloud.kernel.model.exception.AbstractBaseExceptionEnum;

/**
 * 单点登录退出错误码
 *
 *
 * @Date 2019/9/25 22:03
 */
public enum CodeEnum implements AbstractBaseExceptionEnum {

    TOKEN_ERROR(-1, "token错误"),
    TOKEN_IS_EXPIRED(-2, "token已过期"),
    APP_IS_ERROR(-3, "应用未配置正确"),
    PASSWORD_ERROR(-4, "账号或密码错误!"),
    APP_CONFIG_ERROR(-5, "应用未找到或未配置私钥"),
    SECOND_LOGIN(-6, "需要二次认证"),
    SEND_ERROR(-7, "验证码发送失败"),
    CODE_ERROR(-8, "验证码错误或已过期"),
    SIGN_ERROR(-9, "签名错误"),
    RPC_ERROR(-10, "远程调用出错"),
    USER_NOT_FOUND(-11, "用户未找到或已过期"),
    PASSWORD_FORMAT_ERROR(-12, "密码格式不正确"),
    NEW_PASSWORD_SAME(-13, "新密码与旧密码不能一致"),
    INIT_PASSWORD_ERROR(-14, "新密码不能为初始密码"),
    AUTH_CODE_EXPIRED(-15, "认证码过期"),
    SUCCESS(1, "成功"),
    TOKEN_DELETE(2, "token已删除"),
    ACCOUNT_FAILURE(3, "账号已失效"),
    PASSWORD_RESET(4, "需重置初始密码");

    private Integer code;
    private String message;

    CodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
