package com.biemo.cloud.system.modular.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 用户角色表
 * </p>
 *
 *
 * @since 2019-10-15
 */
@TableName("biemo_sys_user_role")
public class SysUserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户角色id
     */
    @TableId(value = "user_role_id", type = IdType.ASSIGN_ID)
    private Long userRoleId;

    /**
     * 账户id
     */
    @TableField("account_id")
    private Long accountId;

    /**
     * 角色id
     */
    @TableField("role_id")
    private Long roleId;


    public Long getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(Long userRoleId) {
        this.userRoleId = userRoleId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "SysUserRole{" +
        "userRoleId=" + userRoleId +
        ", accountId=" + accountId +
        ", roleId=" + roleId +
        "}";
    }
}
