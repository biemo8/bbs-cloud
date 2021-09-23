package com.biemo.cloud.system.modular.ent.enums;

import com.biemo.cloud.kernel.model.exception.AbstractBaseExceptionEnum;

/**
 * 组织信息的异常枚举
 *
 *
 * @Date 2018年1月23日 15:20:33
 */
public enum EntException implements AbstractBaseExceptionEnum {
    /**
     * 请求参数为空
     */
    REQ_PARAM_EXIST_NULL(401,"请求参数存在必填项为空！"),
    PID_NOT_EXIST(402,"父id为空"),
    CHECKED_FAILED(403,"禁用失败！该节点下还有启用的子节点");

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 状态描述
     */
    private String message;

    EntException(Integer code, String message) {
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
