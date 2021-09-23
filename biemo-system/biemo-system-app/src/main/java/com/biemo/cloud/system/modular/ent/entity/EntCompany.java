package com.biemo.cloud.system.modular.ent.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 企业信息
 * </p>
 *
 *
 * @since 2019-10-15
 */
@TableName("biemo_ent_company")
public class EntCompany implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 公司id
     */
    @TableId(value = "company_id", type = IdType.ASSIGN_ID)
    private Long companyId;

    /**
     * 公司名称
     */
    @TableField("name")
    private String name;

    /**
     * 公司简称
     */
    @TableField("short_name")
    private String shortName;

    /**
     * 公司编码
     */
    @TableField("cp_code")
    private String cpCode;

    /**
     * 上级id
     */
    @TableField("parent_id")
    private Long parentId;

    /**
     * 上级id的集合
     */
    @TableField("parent_ids")
    private String parentIds;

    /**
     * 公司门户地址
     */
    @TableField("url")
    private String url;

    /**
     * 排序
     */
    @TableField("order_no")
    private BigDecimal orderNo;

    /**
     * 公司简介
     */
    @TableField("description")
    private String description;

    /**
     * 状态（1启用，2禁用）
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


    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getCpCode() {
        return cpCode;
    }

    public void setCpCode(String cpCode) {
        this.cpCode = cpCode;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public BigDecimal getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(BigDecimal orderNo) {
        this.orderNo = orderNo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    @Override
    public String toString() {
        return "EntCompany{" +
        "companyId=" + companyId +
        ", name=" + name +
        ", shortName=" + shortName +
        ", cpCode=" + cpCode +
        ", parentId=" + parentId +
        ", parentIds=" + parentIds +
        ", url=" + url +
        ", orderNo=" + orderNo +
        ", description=" + description +
        ", status=" + status +
        ", createUser=" + createUser +
        ", createTime=" + createTime +
        ", updateUser=" + updateUser +
        ", updateTime=" + updateTime +
        "}";
    }
}
