package com.biemo.cloud.libs.error;

import cn.hutool.core.bean.BeanUtil;
import com.biemo.cloud.kernel.model.exception.ServiceException;
import com.biemo.cloud.kernel.model.exception.enums.CoreExceptionEnum;
import com.biemo.cloud.kernel.model.response.ErrorResponseData;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

/**
 * 系统管理未知错误异常重写
 *
 *
 * @Date 2019/5/31 21:40
 */
public class SystemErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        Throwable throwable = this.getError(webRequest);
        if (throwable instanceof ServiceException) {
            ServiceException serviceException = (ServiceException) throwable;
            return BeanUtil.beanToMap(new ErrorResponseData(serviceException.getCode(), serviceException.getMessage(), null));
        } else {
            return BeanUtil.beanToMap(new ErrorResponseData(CoreExceptionEnum.SERVICE_ERROR.getCode(), CoreExceptionEnum.SERVICE_ERROR.getMessage(), null));
        }
    }

}
