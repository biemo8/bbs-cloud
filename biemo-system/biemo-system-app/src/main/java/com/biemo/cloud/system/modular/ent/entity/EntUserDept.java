package com.biemo.cloud.system.modular.ent.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 用户部门关联表
 * </p>
 *
 *
 * @since 2019-10-15
 */
@TableName("biemo_ent_user_dept")
public class EntUserDept implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "user_dept_id", type = IdType.ASSIGN_ID)
    private Long userDeptId;

    /**
     * 用户id
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 职务id
     */
    @TableField("duty_id")
    private Long dutyId;

    /**
     * 部门id
     */
    @TableField("dept_id")
    private Long deptId;

    /**
     * 公司id
     */
    @TableField("company_id")
    private Long companyId;

    /**
     * 是否为 默认部门('N'代表否,'Y'代表是)
     */
    @TableField("default_flag")
    private String defaultFlag;


    public Long getUserDeptId() {
        return userDeptId;
    }

    public void setUserDeptId(Long userDeptId) {
        this.userDeptId = userDeptId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getDutyId() {
        return dutyId;
    }

    public void setDutyId(Long dutyId) {
        this.dutyId = dutyId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getDefaultFlag() {
        return defaultFlag;
    }

    public void setDefaultFlag(String defaultFlag) {
        this.defaultFlag = defaultFlag;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    @Override
    public String toString() {
        return "EntUserDept{" +
                "userDeptId=" + userDeptId +
                ", userId=" + userId +
                ", dutyId=" + dutyId +
                ", deptId=" + deptId +
                ", companyId=" + companyId +
                ", defaultFlag='" + defaultFlag + '\'' +
                '}';
    }
}
