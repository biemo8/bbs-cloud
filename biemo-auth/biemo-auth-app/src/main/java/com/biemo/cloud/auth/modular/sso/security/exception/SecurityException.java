package com.biemo.cloud.auth.modular.sso.security.exception;

import com.biemo.cloud.kernel.model.exception.AbstractBaseExceptionEnum;
import com.biemo.cloud.kernel.model.exception.ServiceException;

/**
 * 安全问题异常
 *
 *
 * @date 2019-02-19-5:48 PM
 */
public class SecurityException extends ServiceException {

    public SecurityException(AbstractBaseExceptionEnum exception) {
        super(exception);
    }

    public SecurityException(Integer code, String errorMessage) {
        super(code, errorMessage);
    }
}
