package com.biemo.cloud.system.modular.mgr.enums;

import lombok.Getter;

/**
 * 系统默认初始化的账号信息的枚举
 *
 *
 * @date 2018-03-13 15:11
 */
@Getter
public enum ManagerInfoEnums {

    /**
     * 系统管理员初始化账号
     */
    SYSTEM_ADMIN(1L, "admin", "111111", "biemo-system-app", "系统管理员权限", "system_role", "系统管理员角色");

    ManagerInfoEnums(Long infoId, String account, String password, String appCode, String permissionName, String roleCode, String roleName) {
        this.infoId = infoId;
        this.account = account;
        this.password = password;
        this.appCode = appCode;
        this.permissionName = permissionName;
        this.roleCode = roleCode;
        this.roleName = roleName;
    }

    /**
     * 用户信息id
     */
    private Long infoId;

    /**
     * 账号
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 应用的编码
     */
    private String appCode;

    /**
     * 权限编码
     */
    private String permissionName;

    /**
     * 角色编码
     */
    private String roleCode;

    /**
     * 角色名称
     */
    private String roleName;

}
