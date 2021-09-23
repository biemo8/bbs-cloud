package com.biemo.cloud.system.modular.ent.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import org.apache.kafka.common.protocol.types.Field;

import java.io.Serializable;

/**
 * <p>
 * 职务管理
 * </p>
 *
 *
 * @since 2019-10-15
 */
@TableName("biemo_ent_duty")
public class EntDuty implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 职务管理ID
     */
    @TableId(value = "duty_id", type = IdType.ASSIGN_ID)
    private Long dutyId;

    /**
     * 职务唯一业务编码
     */
    @TableField("duty_code")
    private String dutyCode;

    /**
     * 职务名称
     */
    @TableField("duty_name")
    private String dutyName;

    /**
     * 排序码
     */
    @TableField("order_no")
    private BigDecimal orderNo;

    /**
     * 排序码
     */
    @TableField("description")
    private String description;

    /**
     * 状态(1:启用,2:禁用)
     */
    @TableField("status")
    private Integer status;

    /**
     * 创建人
     */
    @TableField(value = "create_user", fill = FieldFill.INSERT)
    private Long createUser;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新人
     */
    @TableField(value = "update_user", fill = FieldFill.UPDATE)
    private Long updateUser;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private Date updateTime;


    public Long getDutyId() {
        return dutyId;
    }

    public void setDutyId(Long dutyId) {
        this.dutyId = dutyId;
    }

    public String getDutyCode() {
        return dutyCode;
    }

    public void setDutyCode(String dutyCode) {
        this.dutyCode = dutyCode;
    }

    public String getDutyName() {
        return dutyName;
    }

    public void setDutyName(String dutyName) {
        this.dutyName = dutyName;
    }

    public BigDecimal getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(BigDecimal orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "EntDuty{" +
                "dutyId=" + dutyId +
                ", dutyCode='" + dutyCode + '\'' +
                ", dutyName='" + dutyName + '\'' +
                ", orderNo=" + orderNo +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", createUser=" + createUser +
                ", createTime=" + createTime +
                ", updateUser=" + updateUser +
                ", updateTime=" + updateTime +
                '}';
    }
}
