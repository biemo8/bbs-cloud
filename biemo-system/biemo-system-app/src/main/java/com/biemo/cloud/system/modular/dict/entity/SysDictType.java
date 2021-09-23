package com.biemo.cloud.system.modular.dict.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 字典类型表
 * </p>
 *
 *
 * @since 2019-10-15
 */
@TableName("biemo_sys_dict_type")
@Data
public class SysDictType implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 字典类型id
     */
    @TableId(value = "dict_type_id", type = IdType.ASSIGN_ID)
    private Long dictTypeId;

    /**
     * 类型1：业务类型2：系统类型
     */
    @TableField("dict_type_class")
    private Integer dictTypeClass;

    /**
     * 字典类型编码
     */
    @TableField("dict_type_code")
    private String dictTypeCode;

    /**
     * 字典类型名称
     */
    @TableField("dict_type_name")
    private String dictTypeName;

    /**
     * 字典描述
     */
    @TableField("dict_type_desc")
    private String dictTypeDesc;

    /**
     * 状态1：启用2：禁用
     */
    @TableField("status")
    private Integer status;

    /**
     * 应用编码
     */
    @TableField("app_code")
    private String appCode;

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
     * 添加时间
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

    /**
     * 删除标记
     */
    @TableField("del_flag")
    private String delFlag;

}
