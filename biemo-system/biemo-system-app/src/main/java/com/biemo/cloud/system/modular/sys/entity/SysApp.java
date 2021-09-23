package com.biemo.cloud.system.modular.sys.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 应用管理
 * </p>
 *
 *
 * @since 2019-10-15
 */
@Data
@TableName("biemo_sys_app")
public class SysApp implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 应用id
     */
    @TableId(value = "app_id", type = IdType.ASSIGN_ID)
    private Long appId;

    /**
     * 应用编码
     */
    @TableField("app_code")
    private String appCode;

    /**
     * 应用名称
     */
    @TableField("app_name")
    private String appName;

    /**
     * 应用图标地址
     */
    @TableField("app_icon")
    private String appIcon;

    /**
     * 应用跳转地址
     */
    @TableField("app_jump_address")
    private String appJumpAddress;


    /**
     * 应用类型（字典）
     */
    @TableField("app_type")
    private Long appType;

    /**
     * 应用描述
     */
    @TableField("description")
    private String description;

    /**
     * 排序
     */
    @TableField("order_no")
    private BigDecimal orderNo;

    /**
     * 应用状态(1:启用,2:禁用)
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

    /**
     * 删除标记
     */
    @TableField("del_flag")
    private String delFlag;
}
