/**
 * Copyright  2021-2025 &   (9094908@qq.com)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.biemo.cloud.libs.feign;

import cn.hutool.core.io.IoUtil;
import com.biemo.cloud.core.util.ToolUtil;
import com.biemo.cloud.kernel.model.exception.AbstractBaseExceptionEnum;
import com.biemo.cloud.kernel.model.exception.ApiServiceException;
import com.biemo.cloud.kernel.model.exception.ServiceException;
import com.biemo.cloud.kernel.model.exception.enums.CoreExceptionEnum;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * 自己的feign错误解码器（为了feign接收到错误的返回，转化成可识别的ServiceException）
 *
 *
 * @Date 2018/4/20 23:14
 */
@Slf4j
public class FeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {

        /**
         * 首先解析出来ResponseBody内容，如果body解析异常则直接抛出ServiceException
         */
        String resposeBody;
        try {
            if (response == null || response.body() == null) {
                if (response != null && response.status() == 404) {
                    return new ServiceException(CoreExceptionEnum.REMOTE_SERVICE_NULL);
                } else {
                    return new ServiceException(CoreExceptionEnum.SERVICE_ERROR);
                }
            }
            resposeBody = IoUtil.read(response.body().asInputStream(), "UTF-8");
        } catch (IOException e) {
            return new ServiceException(CoreExceptionEnum.IO_ERROR);
        }

        /**
         * 解析出来ResponseBody后，用json反序列化
         */
        JSONObject jsonObject = JSON.parseObject(resposeBody);
        if (log.isDebugEnabled()) {
            log.debug("FeignErrorDecoder收到错误响应结果：" + resposeBody);
        }

        /**
         * 获取有效信息
         */
        String exceptionClazz = jsonObject.getString("exceptionClazz");
        Integer code = jsonObject.getInteger("code");
        String message = jsonObject.getString("message");

        /**
         * 首先判断是否有exceptionClazz字段，如果有，代表是服务抛出的业务接口异常ApiServiceException的子类，那么需要返回这个异常
         */
        if (ToolUtil.isNotEmpty(exceptionClazz)) {
            ApiServiceException apiServiceExceptionByClassName =
                    this.getApiServiceExceptionByClassName(exceptionClazz, code, message);
            if (apiServiceExceptionByClassName != null) {
                return apiServiceExceptionByClassName;
            }
        }

        /**
         * 如果不是ApiServiceException的子类，则抛出ServiceException
         */
        if (message == null) {
            message = CoreExceptionEnum.SERVICE_ERROR.getMessage();
        }
        if (code == null) {

            //status为spring默认返回的数据
            Integer status = jsonObject.getInteger("status");

            if (status == null) {
                return new ServiceException(CoreExceptionEnum.SERVICE_ERROR.getCode(), message);
            } else {
                return new ServiceException(status, message);
            }
        } else {
            return new ServiceException(code, message);
        }
    }

    /**
     * 通过类名称（字符串）反射获取具体的异常类
     */
    private ApiServiceException getApiServiceExceptionByClassName(String className, Integer code, String message) {

        try {
            Class<?> clazz = Class.forName(className);
            Constructor constructor = clazz.getConstructor(AbstractBaseExceptionEnum.class);
            return (ApiServiceException) constructor.newInstance(new AbstractBaseExceptionEnum() {
                @Override
                public Integer getCode() {
                    return code;
                }

                @Override
                public String getMessage() {
                    return message;
                }
            });
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            return null;
        }
    }
}
