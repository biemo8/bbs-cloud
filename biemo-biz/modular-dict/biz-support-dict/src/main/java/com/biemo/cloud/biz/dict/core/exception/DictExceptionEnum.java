package com.biemo.cloud.biz.dict.core.exception;


import com.biemo.cloud.kernel.model.exception.AbstractBaseExceptionEnum;

/**
 * 字典类型异常枚举
 *
 * @author wangzhongqiang
 * @data 2018/4/19 9:22
 */
public enum DictExceptionEnum implements AbstractBaseExceptionEnum {

    REPEAT_DICT_TYPE(2110, "该编码字典已经存在！"),
    NOT_EXISTED(2111, "字典不存在！"),
    PARENT_NOT_EXISTED(2112, "父级字典不存在！"),
    WRONG_STATUS(2113, "状态错误！");


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
