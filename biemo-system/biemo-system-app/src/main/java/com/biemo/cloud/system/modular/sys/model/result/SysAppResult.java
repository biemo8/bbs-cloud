package com.biemo.cloud.system.modular.sys.model.result;

import lombok.Data;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 应用管理
 * </p>
 *
 *
 * @since 2019-10-15
 */
@Data
@ApiModel
public class SysAppResult implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 应用id
     */
    @ApiModelProperty("应用id")
    private Long appId;

    /**
     * 应用编码
     */
    @ApiModelProperty("应用编码")
    private String appCode;

    /**
     * 应用名称
     */
    @ApiModelProperty("应用名称")
    private String appName;

    /**
     * 应用图标地址
     */
    @ApiModelProperty("应用图标地址")
    private String appIcon;

    /**
     * 应用类型（字典）
     */
    @ApiModelProperty("应用类型（字典）")
    private Long appType;

    /**
     * 应用描述
     */
    @ApiModelProperty("应用描述")
    private String description;

    /**
     * 排序
     */
    @ApiModelProperty("排序")
    private BigDecimal orderNo;

    /**
     * 应用状态(1:启用,2:禁用)
     */
    @ApiModelProperty("应用状态(1:启用,2:禁用)")
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
     * 删除标记
     */
    @ApiModelProperty(hidden = true)
    private String delFlag;

}
