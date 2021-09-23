package com.biemo.cloud.libs.error.enums;

import com.biemo.cloud.kernel.model.exception.AbstractBaseExceptionEnum;

/**
 * 所有业务异常的枚举
 *
 *
 * @date 2016年11月12日 下午5:04:51
 */
public enum BizExceptionEnum implements AbstractBaseExceptionEnum {

    /**
     * 服务器异常
     */
    SERVER_ERROR(41600, "服务器异常"),

    /**
     * token异常
     */
    TOKEN_EXPIRED(41601, "token过期"),
    TOKEN_ERROR(41602, "token验证失败"),

    /**
     * 签名异常
     */
    SIGN_ERROR(41603, "签名验证失败"),

    /**
     * session
     */
    NO_CURRENT_USER(41604, "没有登录用户");

    BizExceptionEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private Integer code;

    private String message;

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
