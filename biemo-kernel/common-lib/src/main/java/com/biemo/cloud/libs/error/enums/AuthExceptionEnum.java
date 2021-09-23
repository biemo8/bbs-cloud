package com.biemo.cloud.libs.error.enums;

import com.biemo.cloud.kernel.model.exception.AbstractBaseExceptionEnum;

/**
 * 登录认证失败的枚举
 *
 *
 * @date 2016年11月12日 下午5:04:51
 */
public enum AuthExceptionEnum implements AbstractBaseExceptionEnum {

    /**
     * 账号密码错误
     */
    PWD_ERROR(41610, "账号或密码错误");

    AuthExceptionEnum(int code, String message) {
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
