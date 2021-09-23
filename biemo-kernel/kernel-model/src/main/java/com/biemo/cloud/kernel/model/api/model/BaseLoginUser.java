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
package com.biemo.cloud.kernel.model.api.model;


import com.biemo.cloud.kernel.model.auth.AbstractLoginUser;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;


/**
 * 当前用户的登录信息
 *
 *
 * @Date 2018/8/22 下午6:19
 */
@SuppressWarnings("ALL")
@Data
public class BaseLoginUser implements AbstractLoginUser, Serializable {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户账号
     */
    private String account;

    /**
     * 应用id
     */
    private Long appId;

    /**
     * 角色id集合
     */
    private Set<Long> roleIds;

    /**
     * 角色名称集合
     */
    private Set<String> roleNames;

    /**
     * 可用资源集合
     */
    private Set<String> resourceUrls;

    /**
     * 部门id
     */
    private Long deptId;

    /**
     * 头像
     */
    private String avatar;

    @Override
    public Long getUserUniqueId() {
        return userId;
    }

    @Override
    public Long getAppId() {
        return appId;
    }

    @Override
    public Set<Long> getRoleIds() {
        return roleIds;
    }

    @Override
    public Set<String> getRoleCodes() {
        return roleNames;
    }

    @Override
    public Set<String> getResourceUrls() {
        return resourceUrls;
    }
}
