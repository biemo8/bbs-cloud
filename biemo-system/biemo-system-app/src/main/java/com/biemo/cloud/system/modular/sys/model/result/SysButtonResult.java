package com.biemo.cloud.system.modular.sys.model.result;

import lombok.Data;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 按钮表
 * </p>
 *
 *
 * @since 2019-11-02
 */
@Data
@ApiModel
public class SysButtonResult implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 按钮id
     */
    @ApiModelProperty("按钮id")
    private Long buttonId;

    /**
     * 菜单id
     */
    @ApiModelProperty("菜单id")
    private Long menuId;

    /**
     * 资源id
     */
    @ApiModelProperty("资源id")
    private String resourceId;

    /**
     * 按钮名称
     */
    @ApiModelProperty("按钮名称")
    private String buttonName;

    /**
     * 按钮编码
     */
    @ApiModelProperty("按钮编码")
    private String buttonCode;

    /**
     * 按钮描述
     */
    @ApiModelProperty("按钮描述")
    private String description;

    /**
     * 按钮状态(1:启用 2:禁用)
     */
    @ApiModelProperty("按钮状态(1:启用 2:禁用)")
    private Integer status;

    /**
     * 按钮排序
     */
    @ApiModelProperty("按钮排序")
    private BigDecimal orderNo;

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
     * 修改人
     */
    @ApiModelProperty(hidden = true)
    private Long updateUser;

    /**
     * 修改时间
     */
    @ApiModelProperty(hidden = true)
    private Date updateTime;

}
