package com.biemo.cloud.system.modular.sys.controller;

import cn.hutool.core.bean.BeanUtil;
import com.biemo.cloud.auth.api.context.LoginContext;
import com.biemo.cloud.system.core.error.SysExceptionEnum;
import com.biemo.cloud.system.modular.sys.entity.SysRole;
import com.biemo.cloud.system.modular.sys.model.RoleParam;
import com.biemo.cloud.system.modular.sys.model.params.SysRoleParam;
import com.biemo.cloud.system.modular.sys.service.SysResourceService;
import com.biemo.cloud.system.modular.sys.service.SysRoleService;
import com.biemo.cloud.core.base.controller.BaseController;
import com.biemo.cloud.core.page.PageFactory;
import com.biemo.cloud.core.request.RequestData;
import com.biemo.cloud.kernel.model.response.ResponseData;
import com.biemo.cloud.kernel.scanner.modular.annotation.GetResource;
import com.biemo.cloud.kernel.scanner.modular.annotation.PostResource;
import com.biemo.cloud.kernel.scanner.modular.stereotype.ApiResource;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 角色管理控制器
 *
 *
 * @Date 2019/9/26 22:51
 */
@RestController
@ApiResource(path = "/role")
@Api(tags = "角色管理接口")
public class RoleController extends BaseController {

    @Autowired
    private SysRoleService roleService;

    @Autowired
    private SysResourceService resourceService;

    /**
     * 获取角色列表(分页)
     *
     *
     * @Date 2019/9/26 22:52
     */
    @PostResource(name = "获取角色列表(分页)", path = "/page")
    @ApiOperation("获取角色列表(分页)")
    public ResponseData findRolePage(@RequestBody SysRoleParam sysRoleParam) {
        SysRole role = BeanUtil.toBean(sysRoleParam, SysRole.class);
        Page<RoleParam> roleList = roleService.findRolePage(role);
        return ResponseData.success(roleList);
    }

    /**
     * 获取角色列表
     *
     *
     * @Date 2019/9/26 22:52
     */
    @PostResource(name = "获取所有角色列表", path = "/list")
    @ApiOperation("获取所有角色列表")
    public ResponseData findAllRole(@RequestBody SysRoleParam sysRoleParam) {
        SysRole role = BeanUtil.toBean(sysRoleParam, SysRole.class);
        List<RoleParam> roleList = roleService.findRoleList(role);
        return ResponseData.success(roleList);
    }

    /**
     * 添加角色
     *
     *
     * @Date 2019/9/26 22:51
     */
    @PostResource(name = "添加角色", path = "/add")
    @ApiOperation("添加角色")
    public ResponseData addRole(@RequestBody SysRoleParam sysRoleParam) {
        SysRole role = BeanUtil.toBean(sysRoleParam, SysRole.class);
        roleService.addRole(role);
        return ResponseData.success();
    }

    /**
     * 删除角色
     *
     *
     * @Date 2019/9/26 22:52
     */
    @PostResource(name = "删除角色", path = "/delete")
    @ApiOperation("删除角色")
    public ResponseData deleteRole(RequestData requestData) {
        Long roleId = requestData.getLong("roleId");
        roleService.deleteRole(roleId);
        return ResponseData.success();
    }

    /**
     * 更新角色
     *
     *
     * @Date 2019/9/26 22:52
     */
    @PostResource(name = "更新角色", path = "/update")
    @ApiOperation("更新角色")
    public ResponseData updateRole(@RequestBody SysRoleParam sysRoleParam) {
        SysRole role = BeanUtil.toBean(sysRoleParam, SysRole.class);
        roleService.updateRole(role);
        return ResponseData.success();
    }

    /**
     * 设置角色状态
     *
     *
     * @Date 2019/9/26 22:52
     */
    @PostResource(name = "设置角色状态", path = "/status")
    @ApiOperation("设置角色状态")
    public ResponseData setStatus(RequestData requestData) {
        Long roleId = requestData.getLong("roleId");
        Integer status = requestData.getInteger("status");
        roleService.updateStatus(roleId, status);
        return ResponseData.success();
    }

    /**
     * 获取角色详情
     *
     *
     * @Date 2019/9/26 22:52
     */
    @PostResource(name = "获取角色详情", path = "/detail")
    @ApiOperation("获取角色详情")
    public ResponseData findRoleDetail(@RequestBody SysRoleParam sysRoleParam) {
        SysRole role = BeanUtil.toBean(sysRoleParam, SysRole.class);
        return ResponseData.success(roleService.findRoleDetail(role));
    }

    /**
     * 校验角色名称
     *
     *
     * @Date 2019/9/26 22:52
     */
    @PostResource(name = "校验角色名称", path = "/validate/name")
    @ApiOperation("校验角色名称")
    public ResponseData validateName(RequestData requestData) {
        Long roleId = requestData.getLong("roleId");
        Long appId = requestData.getLong("appId");
        String roleName = requestData.getString("roleName");
        boolean result = roleService.validateName(appId, roleId, roleName);
        if (result) {
            return ResponseData.success("验证通过");
        }
        return ResponseData.error(SysExceptionEnum.ROLE_NAME_REPEAT.getCode(), "角色名称：" + roleName + "已存在！");
    }

    /**
     * 校验角色编码
     *
     *
     * @Date 2019/9/26 22:52
     */
    @PostResource(name = "校验角色编码", path = "/validate/code")
    @ApiOperation("校验角色编码")
    public ResponseData validateCode(RequestData requestData) {
        Long roleId = requestData.getLong("roleId");
        Long appId = requestData.getLong("appId");
        String roleCode = requestData.getString("roleCode");
        boolean result = roleService.validateCode(appId, roleId, roleCode);
        if (result) {
            return ResponseData.success("验证通过");
        }
        return ResponseData.error(SysExceptionEnum.ROLE_CODE_REPEAT.getCode(), "角色编码：" + roleCode + "已存在！");
    }


    /**
     * 查询角色分配的权限
     *
     *
     * @Date 2019/9/26 22:53
     */
    @GetResource(name = "查询角色分配的权限", path = "/permissions")
    @ApiOperation("查询角色分配的权限")
    public ResponseData findPermissions(@RequestParam("roleId") Long roleId, @RequestParam("appId") Long appId) {
        List<Map<String, Object>> result = roleService.findPermissions(roleId, appId);
        return ResponseData.success(result);
    }

    /**
     * 查询角色分配的所有权限
     *
     *
     * @Date 2019/9/26 22:53
     */
    @PostResource(name = "查询角色分配的所有权限", path = "/findRoleAllPermissions")
    @ApiOperation("查询角色分配的所有权限")
    public ResponseData findRoleAllPermissions(RequestData requestData) {
        Long roleId = requestData.getLong("roleId");
        List<Long> result = roleService.findRoleAllPermissions(roleId);
        return ResponseData.success(result);
    }

    /**
     * 为角色分配权限
     *
     *
     * @Date 2019/9/26 22:53
     */
    @PostResource(name = "为角色分配权限", path = "/assign/permission")
    @ApiOperation("为角色分配权限")
    public ResponseData assignPermission(RequestData requestData) {
        Long roleId = requestData.getLong("roleId");
        List<Long> permissionIds = requestData.getList("permissionIds", Long.class);
        roleService.assignPermission(roleId, permissionIds);
        return ResponseData.success();
    }

    /**
     * 查询个人角色信息
     *
     *
     * @Date 2019/9/26 22:53
     */
    @GetResource(name = "查询个人角色信息", path = "/selectRoleByAccountId")
    @ApiOperation("查询个人角色信息")
    public ResponseData selectRoleByAccountId(@RequestParam("accountId") Long accountId) {
        Page page = PageFactory.defaultPage();
        Page appList = roleService.selectRoleByAccountId(page, accountId);
        return ResponseData.success(appList);
    }

    /**
     * 获取用户所有资源列表
     *
     *
     * @Date 2019/9/26 22:53
     */
    @GetResource(name = "获取用户所有资源列表", path = "/getAllResPermissions")
    @ApiOperation("获取用户所有资源列表")
    public ResponseData getAllResPermissions(@RequestParam("appId") Long appId) {
        Long accountId = LoginContext.me().getAccountId();
        Set<String> userResources = roleService.getUserResourcesByApp(accountId, appId);
        return ResponseData.success(userResources);
    }

    /**
     * 获取用户所有资源的url列表
     *
     *
     * @Date 2019/9/26 22:53
     */
    @GetResource(name = "获取用户所有资源的url列表", path = "/getAllResPermissionUrls")
    @ApiOperation("获取用户所有资源的url列表")
    public ResponseData getAllResPermissionUrls(@RequestParam("appId") Long appId) {
        Long accountId = LoginContext.me().getAccountId();
        Set<String> userResources = roleService.getUserResourcesByApp(accountId, appId);

        //获取资源对应的url
        Set<String> resourceUrls = resourceService.getResourceUrls(userResources);

        return ResponseData.success(resourceUrls);
    }

}
