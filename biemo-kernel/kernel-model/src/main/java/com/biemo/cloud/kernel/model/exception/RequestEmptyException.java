/**
 * Copyright  2018-2020 &   (admin@makesoft.cn)
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
package com.biemo.cloud.kernel.model.exception;

import lombok.Data;

/**
 * 业务异常的封装
 *
 *
 * @date 2016年11月12日 下午5:05:10
 */
@Data
public class RequestEmptyException extends ServiceException {

    public RequestEmptyException() {
        super(400, "请求数据不完整或格式错误！");
    }

    public RequestEmptyException(String errorMessage) {
        super(400, errorMessage);
    }

    /**
     * 不拷贝栈信息，提高性能
     *
     *
     * @Date 2018/7/25 下午1:48
     */
    @Override
    public synchronized Throwable fillInStackTrace() {
        return null;
    }
}
