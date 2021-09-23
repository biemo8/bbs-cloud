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
package com.biemo.cloud.system.api;

import com.biemo.cloud.system.api.model.BaseUserInfo;
import com.biemo.cloud.system.api.model.UserInfo;
import com.biemo.cloud.system.api.model.WorkflowRoleInfo;
import com.biemo.cloud.system.api.model.req.UserByRoleReq;
import com.biemo.cloud.system.api.model.req.UserListReq;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 登录相关的api
 *
 *
 * @date 2018-02-07 9:57
 */
@RequestMapping("/baseSystem")
public interface BaseSystemApi {

    /**
     * 校验账号密码，获取用户
     *
     *
     * @Date 2019-09-11 15:46
     */
    @RequestMapping(value = "/getUserByAccount", method = RequestMethod.POST)
    BaseUserInfo getUserByAccount(@RequestParam(value = "account") String account);

    /**
     * 获取用户名称通过账号id
     *
     *
     * @Date 2019/12/1 12:51
     */
    @RequestMapping(value = "/getNameByAccountId", method = RequestMethod.POST)
    String getNameByAccountId(@RequestParam(value = "accountId") Long accountId);

    /**
     * 获取用户名称通过用户详情id
     *
     *
     * @Date 2019/12/1 12:51
     */
    @RequestMapping(value = "/getNameByUserId", method = RequestMethod.POST)
    String getNameByUserId(@RequestParam(value = "userId") Long userId);

    /**
     * 获取角色列表
     *
     *
     * @Date 2019/12/1 13:21
     */
    @RequestMapping(value = "/getRoleList", method = RequestMethod.POST)
    List<WorkflowRoleInfo> getRoleList();

    /**
     * 获取用户列表通过角色id
     *
     *
     * @Date 2019/12/1 13:22
     */
    @RequestMapping(value = "/getUserListByRoleId", method = RequestMethod.POST)
    List<UserInfo> getUserListByRoleId(@RequestParam(value = "roleId") Long roleId);

    /**
     * 获取用户列表通过角色id(带分页)
     *
     *
     * @Date 2019/12/1 13:22
     */
    @RequestMapping(value = "/getUsersByRoleIdByPage", method = RequestMethod.POST)
    Page<UserInfo> getUsersByRoleIdByPage(@RequestBody UserByRoleReq userByRoleReq);

    /**
     * 获取用户列表，通过用户id或角色id的集合
     *
     *
     * @Date 2019/12/1 16:21
     */
    @RequestMapping(value = "/getUserListByUserIdsOrRoleIds", method = RequestMethod.POST)
    List<UserInfo> getUserListByUserIdsOrRoleIds(@RequestBody UserListReq userListReq);

}
