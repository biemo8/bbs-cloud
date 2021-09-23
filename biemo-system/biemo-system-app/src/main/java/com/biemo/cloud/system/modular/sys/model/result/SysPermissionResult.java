package com.biemo.cloud.system.modular.sys.model.result;

import lombok.Data;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 权限表
 * </p>
 *
 *
 * @since 2019-10-15
 */
@Data
@ApiModel
public class SysPermissionResult implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 主键id
     */
    @ApiModelProperty("主键id")
    private Long permissionId;

    /**
     * 权限名称
     */
    @ApiModelProperty("权限名称")
    private String permissionName;

    /**
     * 权限描述
     */
    @ApiModelProperty("权限描述")
    private String permissionDesc;

    /**
     * 排序编号
     */
    @ApiModelProperty("排序编号")
    private BigDecimal orderNo;

    /**
     * 父级id
     */
    @ApiModelProperty("父级id")
    private Long parentId;

    /**
     * 应用ID
     */
    @ApiModelProperty("应用ID")
    private Long appId;

    /**
     * 权限状态(1:启用,2:禁用)
     */
    @ApiModelProperty("权限状态(1:启用,2:禁用)")
    private Integer status;

    /**
     * 创建人
     */
    @ApiModelProperty(hidden = true)
    private Long createUser;

    /**
     * 创建时间
     */
    @ApiModelProperty(hidden = true)
    private Date createTime;

    /**
     * 更新人
     */
    @ApiModelProperty(hidden = true)
    private Long updateUser;

    /**
     * 更新时间
     */
    @ApiModelProperty(hidden = true)
    private Date updateTime;

    /**
     * 删除标记(Y:已删除 N:未删除)
     */
    @ApiModelProperty(hidden = true)
    private String delFlag;

}
