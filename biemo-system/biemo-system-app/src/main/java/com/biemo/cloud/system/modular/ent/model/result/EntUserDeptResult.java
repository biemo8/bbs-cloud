package com.biemo.cloud.system.modular.ent.model.result;

import lombok.Data;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 用户部门关联表
 * </p>
 *
 *
 * @since 2019-10-15
 */
@Data
@ApiModel
public class EntUserDeptResult implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */
    @ApiModelProperty("主键")
    private Long userDeptId;

    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    private Long userId;

    /**
     * 职务id
     */
    @ApiModelProperty("职务id")
    private Long dutyId;

    /**
     * 部门id
     */
    @ApiModelProperty("部门id")
    private Long deptId;

    /**
     * 是否为 默认部门('N'代表否,'Y'代表是)
     */
    @ApiModelProperty("是否为 默认部门('N'代表否,'Y'代表是)")
    private String defaultFlag;

}
