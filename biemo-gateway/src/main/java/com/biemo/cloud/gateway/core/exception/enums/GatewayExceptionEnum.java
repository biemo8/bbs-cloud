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
package com.biemo.cloud.gateway.core.exception.enums;


import com.biemo.cloud.kernel.model.exception.AbstractBaseExceptionEnum;

/**
 * 签名异常
 *
 *
 * @date 2018-01-05 14:48
 */
public enum GatewayExceptionEnum implements AbstractBaseExceptionEnum {

    /**
     * 权限校验异常
     */
    TOKEN_EMPTY(401, "登录令牌为空，请检查是否已经登录"),
    TOKEN_ERROR(401, "token无效"),
    NO_PERMISSION(403, "没有访问该资源的权限"),
    CACHE_URL_NULL(404, "url转发规则为空"),
    TOKEN_EXPIRED(41601, "token过期"),

    /**
     * 其他异常
     */
    GATEWAY_ERROR(502, "网关异常");

    GatewayExceptionEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private int code;

    private String message;

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
