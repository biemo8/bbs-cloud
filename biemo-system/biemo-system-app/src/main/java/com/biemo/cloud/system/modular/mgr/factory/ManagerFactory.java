package com.biemo.cloud.system.modular.mgr.factory;

import com.biemo.cloud.system.core.constant.SystemConstants;
import com.biemo.cloud.system.modular.ent.entity.EntUserAccount;
import com.biemo.cloud.system.modular.ent.entity.EntUser;
import com.biemo.cloud.system.modular.sys.entity.SysPermission;
import com.biemo.cloud.system.modular.sys.entity.SysRole;
import com.biemo.cloud.core.util.ToolUtil;
import com.biemo.cloud.kernel.model.enums.StatusEnum;
import com.biemo.cloud.kernel.model.enums.YesOrNotEnum;

import java.util.Date;

/**
 * 超级管理员创建器
 *
 *
 * @date 2018-03-01 17:16
 */
public class ManagerFactory {

    /**
     * 创建指定账号密码的账号
     */
    public static EntUserAccount createAccount(String account, String password, Long infoId) {
        EntUserAccount temp = new EntUserAccount();
        temp.setAccount(account);
        temp.setUserId(infoId);
        temp.setCompanyId(null);
        temp.setCreateTime(new Date());
        String randomSalt = ToolUtil.getRandomString(ToolUtil.SALT_LENGTH);
        temp.setPassword(ToolUtil.md5Hex(password, randomSalt));
        temp.setSalt(randomSalt);
        return temp;
    }

    /**
     * 创建用户信息详情
     */
    public static EntUser createUserInfo(Long infoId) {
        EntUser temp = new EntUser();
        temp.setUserId(infoId);
        temp.setUserCode("SYSTEM_USER_1");
        temp.setCreateTime(new Date());
        temp.setStatus(1);
        return temp;
    }

    /**
     * 创建默认code的角色
     */
    public static SysRole createRole(String roleCode, String roleName, Long appId, String mgrFlag) {
        SysRole role = new SysRole();
        role.setCreateTime(new Date());
        role.setAppId(appId);
        role.setDelFlag(YesOrNotEnum.N.name());
        role.setRoleCode(roleCode);
        role.setRoleName(roleName);
        role.setStatus(StatusEnum.ENABLE.getCode());
        return role;
    }

    /**
     * 创建默认权限
     */
    public static SysPermission createPermission(String permissionName, Long appId) {
        SysPermission permission = new SysPermission();
        permission.setAppId(appId);
        permission.setCreateTime(new Date());
        permission.setDelFlag(YesOrNotEnum.N.name());
        permission.setStatus(StatusEnum.ENABLE.getCode());
        permission.setParentId(SystemConstants.PARENT_ID);
        permission.setPermissionName(permissionName);
        return permission;
    }
}
