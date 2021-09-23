package com.biemo.cloud.system.modular.dict.enums;


import com.biemo.cloud.kernel.model.exception.AbstractBaseExceptionEnum;

/**
 * 字典类型异常枚举
 *
 *
 * @Date 2019/9/27 9:31
 */
public enum DictExceptionEnum implements AbstractBaseExceptionEnum {

    /**
     * 该编码字典已经存在
     */
    REPEAT_DICT_TYPE(103010, "该编码字典已经存在！"),

    /**
     * 字典不存在
     */
    NOT_EXISTED(103011, "字典不存在！"),

    /**
     * 父级字典不存在
     */
    PARENT_NOT_EXISTED(103012, "父级字典不存在！"),

    /**
     * 状态错误
     */
    WRONG_STATUS(103013, "状态错误！");

    DictExceptionEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private int code;

    private String message;

    @Override
    public Integer getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
