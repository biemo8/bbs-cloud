package com.biemo.cloud.system.modular.sys.model.result;

import lombok.Data;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 角色表
 * </p>
 *
 *
 * @since 2019-10-15
 */
@Data
@ApiModel
public class SysRoleResult implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 角色id
     */
    @ApiModelProperty("角色id")
    private Long roleId;

    /**
     * 角色编码
     */
    @ApiModelProperty("角色编码")
    private String roleCode;

    /**
     * 角色名称
     */
    @ApiModelProperty("角色名称")
    private String roleName;

    /**
     * 角色描述
     */
    @ApiModelProperty("角色描述")
    private String roleDesc;

    /**
     * 应用id
     */
    @ApiModelProperty("应用id")
    private Long appId;

    /**
     * 排序码
     */
    @ApiModelProperty("排序码")
    private BigDecimal orderNo;

    /**
     * 状态(1:启用  2:禁用)
     */
    @ApiModelProperty("状态(1:启用  2:禁用)")
    private Integer status;

    /**
     * 创建时间
     */
    @ApiModelProperty(hidden = true)
    private Date createTime;

    /**
     * 创建人
     */
    @ApiModelProperty(hidden = true)
    private Long createUser;

    /**
     * 更新时间
     */
    @ApiModelProperty(hidden = true)
    private Date updateTime;

    /**
     * 更新人
     */
    @ApiModelProperty(hidden = true)
    private Long updateUser;

    /**
     * 删除标记(Y:已删除 N:未删除)
     */
    @ApiModelProperty(hidden = true)
    private String delFlag;

}
