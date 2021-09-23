/**
 * Copyright  2018-2020 &   (https://gitee.com/stylefeng)
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
package com.biemo.cloud.system.core.error;

import com.biemo.cloud.kernel.model.exception.AbstractBaseExceptionEnum;

/**
 * 所有业务异常的枚举
 *
 *
 * @Date 2019/9/26 19:07
 */
public enum SysExceptionEnum implements AbstractBaseExceptionEnum {

    /**
     * 用户和角色类
     */
    USER_NOT_FOUND(6001, "用户不存在"),

    /**
     * 权限和数据问题
     */
    DB_PERMISSION_NULL(400, "数据库中没有该权限"),
    ROLE_NAME_REPEAT(401, "角色名称重复"),
    ROLE_CODE_REPEAT(401, "角色编码重复"),

    /**
     * 其他
     */
    REQUEST_NULL(400, "请求有错误");

    SysExceptionEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private Integer code;

    private String message;

    @Override
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
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
