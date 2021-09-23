package com.biemo.cloud.system.modular.mgr.model;

import com.biemo.cloud.system.modular.mgr.enums.ManagerInfoEnums;
import lombok.Data;

import java.util.List;

/**
 * 初始化系统管理员的参数
 *
 *
 * @date 2018-03-01 18:34
 */
@Data
public class InitManagerParam {

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
     * 角色编码
     */
    private String roleCode;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 权限编码
     */
    private String permissionName;

    /**
     * 应用的编码
     */
    private String appCode;

    /**
     * 权限需要绑定的资源id集合
     */
    private List<String> resources;

    public InitManagerParam() {
    }

    public InitManagerParam(ManagerInfoEnums managerInfoEnums) {
        this.infoId = managerInfoEnums.getInfoId();
        this.account = managerInfoEnums.getAccount();
        this.password = managerInfoEnums.getPassword();
        this.roleCode = managerInfoEnums.getRoleCode();
        this.roleName = managerInfoEnums.getRoleName();
        this.permissionName = managerInfoEnums.getPermissionName();
        this.appCode = managerInfoEnums.getAppCode();
    }

}
