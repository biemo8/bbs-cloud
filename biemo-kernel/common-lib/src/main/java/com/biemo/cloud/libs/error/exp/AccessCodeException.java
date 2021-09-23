package com.biemo.cloud.libs.error.exp;

import com.biemo.cloud.libs.error.enums.AuthExceptionEnum;
import com.biemo.cloud.kernel.model.exception.ServiceException;
import lombok.Getter;

/**
 * 认证失败异常
 *
 *
 * @Date 2019/4/19 21:38
 */
@Getter
public class AccessCodeException extends ServiceException {

    private AuthExceptionEnum authExceptionEnum;

    public AccessCodeException(AuthExceptionEnum authExceptionEnum) {
        super(authExceptionEnum);
        this.authExceptionEnum = authExceptionEnum;
    }

    public AuthExceptionEnum getAuthExceptionEnum() {
        return authExceptionEnum;
    }
}
