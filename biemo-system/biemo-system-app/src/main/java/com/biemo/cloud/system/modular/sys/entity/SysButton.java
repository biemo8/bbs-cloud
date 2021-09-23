package com.biemo.cloud.system.modular.sys.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 按钮表
 * </p>
 *
 *
 * @since 2019-11-02
 */
@TableName("biemo_sys_button")
public class SysButton implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 按钮id
     */
    @TableId(value = "button_id", type = IdType.ASSIGN_ID)
    private Long buttonId;

    /**
     * 菜单id
     */
    @TableField("menu_id")
    private Long menuId;

    /**
     * 资源编码
     */
    @TableField("res_code")
    private String resCode;

    /**
     * 按钮名称
     */
    @TableField("button_name")
    private String buttonName;

    /**
     * 按钮编码
     */
    @TableField("button_code")
    private String buttonCode;

    /**
     * 按钮编码
     */
    @TableField("res_code_str")
    private String resCodeStr;

    /**
     * 按钮描述
     */
    @TableField("description")
    private String description;

    /**
     * 按钮状态(1:启用 2:禁用)
     */
    @TableField("status")
    private Integer status;

    /**
     * 按钮排序
     */
    @TableField("order_no")
    private BigDecimal orderNo;

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
     * 修改人
     */
    @TableField(value = "update_user", fill = FieldFill.UPDATE)
    private Long updateUser;

    /**
     * 修改时间
     */
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private Date updateTime;


    public Long getButtonId() {
        return buttonId;
    }

    public void setButtonId(Long buttonId) {
        this.buttonId = buttonId;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public String getButtonName() {
        return buttonName;
    }

    public void setButtonName(String buttonName) {
        this.buttonName = buttonName;
    }

    public String getButtonCode() {
        return buttonCode;
    }

    public void setButtonCode(String buttonCode) {
        this.buttonCode = buttonCode;
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

    public BigDecimal getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(BigDecimal orderNo) {
        this.orderNo = orderNo;
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

    public String getResCodeStr() {
        return resCodeStr;
    }

    public void setResCodeStr(String resCodeStr) {
        this.resCodeStr = resCodeStr;
    }

    @Override
    public String toString() {
        return "SysButton{" +
                "buttonId=" + buttonId +
                ", menuId=" + menuId +
                ", resCode='" + resCode + '\'' +
                ", buttonName='" + buttonName + '\'' +
                ", buttonCode='" + buttonCode + '\'' +
                ", resCodeStr='" + resCodeStr + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", orderNo=" + orderNo +
                ", createUser=" + createUser +
                ", createTime=" + createTime +
                ", updateUser=" + updateUser +
                ", updateTime=" + updateTime +
                '}';
    }
}
