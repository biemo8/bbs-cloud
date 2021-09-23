package com.biemo.cloud.system.modular.sys.entity;

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
 * 菜单
 * </p>
 *
 *
 * @since 2019-10-15
 */
@TableName("biemo_sys_menu")
public class SysMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单id
     */
    @TableId(value = "menu_id", type = IdType.ASSIGN_ID)
    private Long menuId;

    /**
     * 菜单名称
     */
    @TableField("name")
    private String name;

    /**
     * 菜单编码
     */
    @TableField("code")
    private String code;

    /**
     * 菜单父id
     */
    @TableField("pid")
    private Long pid;

    /**
     * 图标
     */
    @TableField("icon")
    private String icon;

    /**
     * 应用id
     */
    @TableField("app_id")
    private Long appId;

    /**
     * 资源code
     */
    @TableField("res_code")
    private String resCode;

    /**
     * 菜单提示
     */
    @TableField("menu_tips")
    private String menuTips;

    /**
     * 是否隐藏
     */
    @TableField("hidden_flag")
    private String hiddenFlag;

    /**
     * 排序编号
     */
    @TableField("order_no")
    private BigDecimal orderNo;

    /**
     * 请求地址
     */
    @TableField("url")
    private String url;

    /**
     * 菜单状态(1:启用,2:禁用)
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

    @TableField(value="component")
    private String component;

    //菜单类型（M目录 C菜单 F按钮）
    @TableField(value="menu_type")
    private String menuType;


    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public String getMenuTips() {
        return menuTips;
    }

    public void setMenuTips(String menuTips) {
        this.menuTips = menuTips;
    }

    public String getHiddenFlag() {
        return hiddenFlag;
    }

    public void setHiddenFlag(String hiddenFlag) {
        this.hiddenFlag = hiddenFlag;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "SysMenu{" +
                "menuId=" + menuId +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", pid=" + pid +
                ", icon='" + icon + '\'' +
                ", appId=" + appId +
                ", resCode='" + resCode + '\'' +
                ", menuTips='" + menuTips + '\'' +
                ", hiddenFlag='" + hiddenFlag + '\'' +
                ", orderNo=" + orderNo +
                ", url='" + url + '\'' +
                ", status=" + status +
                ", createUser=" + createUser +
                ", createTime=" + createTime +
                ", updateUser=" + updateUser +
                ", updateTime=" + updateTime +
                ", component='" + component + '\'' +
                '}';
    }
}
