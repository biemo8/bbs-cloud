package com.biemo.cloud.system.modular.mgr.service.impl;

import com.biemo.cloud.system.core.enums.MgrEnum;
import com.biemo.cloud.system.core.menus.SystemMenuFlagController;
import com.biemo.cloud.system.modular.ent.entity.EntUser;
import com.biemo.cloud.system.modular.ent.entity.EntUserAccount;
import com.biemo.cloud.system.modular.ent.service.EntUserAccountService;
import com.biemo.cloud.system.modular.ent.service.EntUserService;
import com.biemo.cloud.system.modular.mgr.enums.ManagerInfoEnums;
import com.biemo.cloud.system.modular.mgr.factory.ManagerFactory;
import com.biemo.cloud.system.modular.mgr.model.InitManagerParam;
import com.biemo.cloud.system.modular.mgr.service.InitManagerService;
import com.biemo.cloud.system.modular.res.enums.AppExceptionEnum;
import com.biemo.cloud.system.modular.sys.entity.*;
import com.biemo.cloud.system.modular.sys.service.*;
import com.biemo.cloud.kernel.model.enums.YesOrNotEnum;
import com.biemo.cloud.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 初始化默认管理员服务
 *
 *
 * @Date 2019-09-27 15:54
 */
@Service
public class InitManagerServiceImpl implements InitManagerService {

    @Autowired
    private SysResourceService resourceService;

    @Autowired
    private EntUserAccountService entUserAccountService;

    @Autowired
    private EntUserService entUserInfoService;

    @Autowired
    private SysAppService sysAppService;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Autowired
    private SysRolePermissionService sysRolePermissionService;

    @Autowired
    private SysPermissionService sysPermissionService;

    @Autowired
    private SysPermissionResourceService sysPermissionResourceService;

    @Override
    public void initSystemManagerAccount() {

        // 创建管理员对象
        InitManagerParam initManagerParam = new InitManagerParam(ManagerInfoEnums.SYSTEM_ADMIN);

        // 查询所有当前系统所有资源
        List<SysResource> resources = resourceService.list();

        // 给管理员分配资源和菜单
        ArrayList<String> resourceIds = new ArrayList<>();
        for (SysResource resource : resources) {
            resourceIds.add(resource.getResourceId());
        }
        initManagerParam.setResources(resourceIds);

        // 初始化创建管理员账号
        this.initSystemAccount(initManagerParam);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void initSystemAccount(InitManagerParam initManagerParam) {

        // 检查当前系统有没有指定账号的管理员,没有则创建
        EntUserAccount mgrAccount = entUserAccountService.getOne(
                new QueryWrapper<EntUserAccount>().eq("account", initManagerParam.getAccount()));
        if (mgrAccount == null) {
            mgrAccount = ManagerFactory.createAccount(initManagerParam.getAccount(), initManagerParam.getPassword(), initManagerParam.getInfoId());
            entUserAccountService.save(mgrAccount);
        }

        // 检查当前系统有没有指定人员信息，没有则创建
        EntUser entUser = entUserInfoService.getById(initManagerParam.getInfoId());
        if (entUser == null) {
            entUser = ManagerFactory.createUserInfo(initManagerParam.getInfoId());
            entUserInfoService.save(entUser);
        }

        // 通过appCode获取appId
        SysApp app = sysAppService.getAppByCode(initManagerParam.getAppCode());
        if (app == null) {
            throw new ServiceException(AppExceptionEnum.APP_NOT_FOUND);
        }

        // 检查当前系统有没有指定角色编码的角色,没有则创建
        SysRole role = sysRoleService.getOne(new QueryWrapper<SysRole>().eq("role_code", initManagerParam.getRoleCode()));
        if (role == null) {

            // 添加企业类管理员角色
            role = ManagerFactory.createRole(initManagerParam.getRoleCode(),
                    initManagerParam.getRoleName(), app.getAppId(), MgrEnum.MANAGER.getCode());
            sysRoleService.save(role);
        }

        // 检查当前系统是该账户是否绑定角色信息
        SysRole finalRole = role;
        SysUserRole userRole = sysUserRoleService.getOne(
                new QueryWrapper<SysUserRole>()
                        .eq("account_id", mgrAccount.getAccountId())
                        .and(i -> i.eq("role_id", finalRole.getRoleId())));
        if (userRole == null) {
            SysUserRole user_Role = new SysUserRole();
            user_Role.setAccountId(mgrAccount.getAccountId());
            user_Role.setRoleId(role.getRoleId());
            user_Role.setUserRoleId(IdWorker.getId());
            sysUserRoleService.save(user_Role);
        }

        initRoleAndPermission(role.getRoleId(), initManagerParam.getPermissionName(),
                app.getAppId(), initManagerParam.getResources());
    }

    /**
     * 初始化角色、权限、资源及相互的应关系
     */
    private void initRoleAndPermission(Long roleId, String permissionName, Long appId, List<String> resourceList) {

        // 检查当前系统有没有指定权限编码,没有则创建
        SysPermission permission = sysPermissionService.getOne(
                new QueryWrapper<SysPermission>().eq("permission_name", permissionName));
        if (permission == null) {
            permission = ManagerFactory.createPermission(permissionName, appId);
            sysPermissionService.save(permission);
        }
        SysPermission finalPermission = permission;
        SysRolePermission rolePermission1 = sysRolePermissionService.getOne(
                new QueryWrapper<SysRolePermission>().eq("role_id", roleId)
                        .and(i -> i.eq("permission_id", finalPermission.getPermissionId()))
        );
        if (rolePermission1 == null) {

            // 绑定角色和权限
            SysRolePermission rolePermission = new SysRolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(permission.getPermissionId());
            sysRolePermissionService.save(rolePermission);
        }

        // 删除权限对应的资源
        sysPermissionResourceService.remove(
                new QueryWrapper<SysPermissionResource>().eq("permission_id", permission.getPermissionId()));
        ArrayList<SysPermissionResource> permissionResources = new ArrayList<>();
        for (String resourceId : resourceList) {
            SysPermissionResource permissionResource = new SysPermissionResource();
            permissionResource.setResourceId(resourceId);
            permissionResource.setPermissionId(permission.getPermissionId());
            permissionResources.add(permissionResource);
        }
        sysPermissionResourceService.saveBatch(permissionResources, permissionResources.size());
    }

}
