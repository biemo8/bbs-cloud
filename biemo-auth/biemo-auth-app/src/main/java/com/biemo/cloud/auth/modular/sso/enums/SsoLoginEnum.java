package com.biemo.cloud.auth.modular.sso.enums;

/**
 * SSO单点登录状态枚举类
 *
 *
 * @Date 2019/9/25 22:28
 */
public enum SsoLoginEnum {
    SUCCESS(1, "成功"),
    TOKEN_DELETE(2, "token已删除"),

    PASSWORD_ERROR(-100, "账号或密码错误!"),
    TOKEN_ERROR(-101, "token错误"),
    TOKEN_IS_EXPIRED(-102, "token已过期"),
    SIGN_ERROR(-103, "签名错误"),
    APP_IS_ERROR(-104, "应用未配置正确"),
    APP_CONFIG_ERROR(-105, "应用未找到或未配置私钥"),
    GEN_DIRECT_ERROR(-106, "生成重定向地址错误"),
    ORIGIN_URL_ERROR(-107, "目标地址URL格式错误"),
    LOGIN_USER_ERROR(-108, "查询用户登录信息失败"),
    GENERATE_TOKEN_ERROR(-109, "生成token信息失败"),

    SECOND_LOGIN(-200, "需要二次认证"),
    SEND_ERROR(-201, "验证码发送失败"),
    AUTH_CODE_ERROR(-202, "验证码错误或已过期"),
    SESSION_ERROR(-203, "SESSION异常"),
    PHONE_ERROR(-204, "手机号为空"),

    LOGIN_LIMIT_ERROR(-300, "登录失败次数过多");

    private Integer code;
    private String message;

    SsoLoginEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
