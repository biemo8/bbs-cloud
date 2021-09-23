package com.biemo.cloud.auth.modular.sso.enums;

/**
 * 操作枚举
 *
 *
 * @Date 2019/9/25 22:27
 */
public enum OperationEnum {

    LOGIN(1, "登录"),
    LOGIN_OUT(2, "退出");

    private Integer code;
    private String message;

    OperationEnum(Integer code, String message) {
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
