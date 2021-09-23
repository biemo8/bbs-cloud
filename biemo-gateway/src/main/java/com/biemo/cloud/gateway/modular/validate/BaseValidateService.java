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
package com.biemo.cloud.gateway.modular.validate;

/**
 * 权限校验的服务
 *
 *
 * @date 2018-08-13 21:50
 */
public abstract class BaseValidateService {

    /**
     * 基础权限校验
     *
     * @return true 校验通过 false 校验失败
     *
     * @Date 2018/8/13 21:50
     */
    public abstract boolean validate(String token, String requestPath);

}
