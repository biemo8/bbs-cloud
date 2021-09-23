package com.biemo.cloud.system.modular.sys.model;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 角色表
 * </p>
 *
 * 123
 * @since 2018-02-05
 */
public class RoleParam extends Model<RoleParam> {

    private static final long serialVersionUID = 1L;

    /**
     * 角色id
     */
    private String roleId;
    /**
     * 角色编码
     */
    private String roleCode;
    /**
     * 角色名称
     */
    private String roleName;

    /**
     * roleType: 角色类型
     *
     * @since 1.0.0
     */
    private String roleType;
    /**
     * 角色描述
     */
    private String roleDesc;
    /**
     * 应用id
     */
    private String appId;

    private String appName;
    /**
     * 1:管理公司角色,2:执行公司角色
     */
    private String mgrFlag;

    private String mgrName;

    /**
     * 角色标记(项目角色--1，易招标标角色--2，其他--0或null)
     */
    private String type;

    /**
     * 集团公司标记(非集团公司：1，是集团公司：2)
     */
    private String blocFlag;
    /**
     * 是否隐藏（非隐藏：N，隐藏：Y）
     */
    private String hideFlag;
    /**
     * 角色排序码
     */
    private String orderNo;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 创建人
     */
    private String createUser;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 更新人
     */
    private String updateUser;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 删除标记(Y:已删除 N:未删除)
     */
    private String delFlag;


    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getMgrFlag() {
        return mgrFlag;
    }

    public void setMgrFlag(String mgrFlag) {
        this.mgrFlag = mgrFlag;
    }

    public String getBlocFlag() {
        return blocFlag;
    }

    public void setBlocFlag(String blocFlag) {
        this.blocFlag = blocFlag;
    }

    public String getHideFlag() {
        return hideFlag;
    }

    public void setHideFlag(String hideFlag) {
        this.hideFlag = hideFlag;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getMgrName() {
        return mgrName;
    }

    public void setMgrName(String mgrName) {
        this.mgrName = mgrName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    protected Serializable pkVal() {
        return this.roleId;
    }

    @Override
    public String toString() {
        return "Role [roleId=" + roleId + ", roleCode=" + roleCode + ", roleName=" + roleName + ", roleDesc=" + roleDesc
                + ", appId=" + appId + ", appName=" + appName + ", mgrFlag=" + mgrFlag + ", mgrName=" + mgrName
                + ", createTime=" + createTime + ", createUser=" + createUser + ", updateTime=" + updateTime
                + ", updateUser=" + updateUser + ", status=" + status + ", delFlag=" + delFlag + "]";
    }

}
