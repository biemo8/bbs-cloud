package com.biemo.cloud.system.modular.res.enums;

import com.biemo.cloud.kernel.model.exception.AbstractBaseExceptionEnum;

/**
 * 应用信息异常枚举
 *
 *
 * @Date 2019-05-29 13:49
 */
public enum AppExceptionEnum implements AbstractBaseExceptionEnum {

    /**
     * 应用不存在
     */
    APP_NOT_FOUND(3103, "应用不存在!");

    AppExceptionEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private int code;

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
