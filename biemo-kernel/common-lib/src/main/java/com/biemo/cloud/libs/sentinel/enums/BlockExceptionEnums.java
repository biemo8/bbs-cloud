package com.biemo.cloud.libs.sentinel.enums;

import com.biemo.cloud.kernel.model.response.ErrorResponseData;
import lombok.Getter;

/**
 * sentinel流控降解的响应
 *
 *
 * @Date 2019/9/1 16:01
 */
@Getter
public enum BlockExceptionEnums {

    /**
     * 当前请求被限流
     */
    FLOW_EXCEPTION(450, "当前请求被限流"),

    /**
     * 当前请求被降级
     */
    DEGRADE_EXCEPTION(451, "当前请求被降级"),

    /**
     * 热点参数限流
     */
    PARAM_FLOW_EXCEPTION(452, "热点参数限流"),

    /**
     * 系统规则限流
     */
    SYSTEM_BLOCK_EXCEPTION(453, "系统规则限流"),

    /**
     * 授权规则不通过
     */
    AUTHORITY_EXCEPTION(454, "授权规则不通过");

    private ErrorResponseData errorResponseData;

    BlockExceptionEnums(Integer code, String message) {
        this.errorResponseData = new ErrorResponseData(code, message);
    }

}
