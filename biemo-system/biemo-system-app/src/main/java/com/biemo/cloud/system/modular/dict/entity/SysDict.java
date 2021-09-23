package com.biemo.cloud.system.modular.dict.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 基础字典
 * </p>
 *
 *
 * @since 2019-10-15
 */
@TableName("biemo_sys_dict")
@Data
public class SysDict implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 字典id
     */
    @TableId(value = "dict_id", type = IdType.ASSIGN_ID)
    private Long dictId;

    /**
     * 字典类型编码
     */
    @TableField("dict_type_code")
    private String dictTypeCode;

    /**
     * 字典编码
     */
    @TableField("dict_code")
    private String dictCode;

    /**
     * 字典名称
     */
    @TableField("dict_name")
    private String dictName;

    /**
     * 简称
     */
    @TableField("dict_short_name")
    private String dictShortName;

    /**
     * 字典简拼
     */
    @TableField("dict_short_code")
    private String dictShortCode;

    /**
     * 上级代码id
     */
    @TableField("parent_id")
    private Long parentId;

    /**
     * 状态(1:启用,2:禁用)
     */
    @TableField("status")
    private Integer status;

    /**
     * 排序
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
