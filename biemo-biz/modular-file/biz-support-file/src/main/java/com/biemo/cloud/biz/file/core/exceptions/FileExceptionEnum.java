package com.biemo.cloud.biz.file.core.exceptions;


import com.biemo.cloud.kernel.model.exception.AbstractBaseExceptionEnum;

/**
 * 文件异常枚举
 *
 * @author wangzhongqiang
 * @data 2018/4/19 9:22
 */
public enum FileExceptionEnum implements AbstractBaseExceptionEnum {

    IO_ERROR(3001, "文件流错误，请检查网络是否连通！"),
    FILE_NOT_FOUND(3002, "文件未找到!"),
    FILE_NAME_FORMAT_ERROR(3003, "文件名称转化错误，文件名不合法！"),
    FILE_NAME_ERROR(3004, "文件id格式错误！");

    FileExceptionEnum(int code, String message) {
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
