package com.biemo.cloud.libs.error.exp;

import com.biemo.cloud.libs.error.enums.AuthExceptionEnum;
import com.biemo.cloud.kernel.model.exception.ServiceException;
import lombok.Getter;

import java.util.Map;

/**
 * 认证失败异常
 *
 *
 * @Date 2019/4/19 21:38
 */
@Getter
public class SpsAuthException extends ServiceException {

    /**
     * 渲染到前端的参数
     */
    private Map<String, Object> renderMap;

    public SpsAuthException(AuthExceptionEnum authExceptionEnum, Map<String, Object> renderMap) {
        super(authExceptionEnum);
        this.renderMap = renderMap;
    }

}
