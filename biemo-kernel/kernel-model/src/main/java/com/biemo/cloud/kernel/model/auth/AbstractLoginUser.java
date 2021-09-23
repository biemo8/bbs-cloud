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
package com.biemo.cloud.kernel.model.auth;

import java.util.Set;

/**
 * 登录中的用户信息
 * <p>
 * 为何类型是泛型，因为具体的项目不知道类型是什么
 *
 *
 * @date 2017-11-09-下午5:47
 */
public interface AbstractLoginUser {

    /**
     * 获取用户唯一id
     *
     *
     * @Date 2018/8/10 下午5:38
     */
    <T> T getUserUniqueId();

    /**
     * 获取当前登录应用Id
     *
     *
     * @Date 2018/8/10 下午5:38
     */
    <T> T getAppId();

    /**
     * 获取角色id的集合
     *
     *
     * @Date 2018/8/10 下午5:40
     */
    <T> Set<T> getRoleIds();

    /**
     * 角色编码集合
     *
     *
     * @Date 2018/8/10 下午5:40
     */
    <T> Set<T> getRoleCodes();

    /**
     * 包含的资源权限url
     *
     *
     * @Date 2018/8/10 下午5:40
     */
    <T> Set<T> getResourceUrls();

}
