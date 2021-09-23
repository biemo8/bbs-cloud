package com.biemo.cloud.system.modular.ent.model.result;

import lombok.Data;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 职务管理
 * </p>
 *
 *
 * @since 2019-10-15
 */
@Data
@ApiModel
public class EntDutyResult implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 职务管理ID
     */
    @ApiModelProperty("职务管理ID")
    private Long dutyId;

    /**
     * 职务唯一业务编码
     */
    @ApiModelProperty("职务唯一业务编码")
    private String dutyCode;

    /**
     * 职务名称
     */
    @ApiModelProperty("职务名称")
    private String dutyName;

    /**
     * 排序码
     */
    @ApiModelProperty("排序码")
    private BigDecimal orderNo;

    /**
     * 状态(1:启用,2:禁用)
     */
    @ApiModelProperty("状态(1:启用,2:禁用)")
    private Integer status;

    /**
     * 职务描述
     */
    @ApiModelProperty("职务描述")
    private String description;

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

}
