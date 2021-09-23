package com.biemo.cloud.gateway.core.exception;

import com.biemo.cloud.gateway.core.exception.enums.GatewayExceptionEnum;
import com.biemo.cloud.kernel.model.exception.ServiceException;

/**
 * 网关的业务异常封装
 *
 *
 * @Date 2019/5/12 21:14
 */
public class GatewayException extends ServiceException {

    public GatewayException(GatewayExceptionEnum exception) {
        super(exception);
    }

}
