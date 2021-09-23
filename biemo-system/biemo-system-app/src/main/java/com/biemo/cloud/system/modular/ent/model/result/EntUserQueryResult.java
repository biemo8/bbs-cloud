package com.biemo.cloud.system.modular.ent.model.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 用户信息
 * </p>
 *
 *
 * @since 2019-10-10
 */
@Data
@ApiModel
public class EntUserQueryResult implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户信息id")
    private Long userId;

    @ApiModelProperty("公司名称")
    private String companyName;

    @ApiModelProperty("部门名称")
    private String deptName;

    @ApiModelProperty("职务名称")
    private String dutyName;

    @ApiModelProperty("用户名称")
    private String userName;

    @ApiModelProperty("用户性别")
    private String sex;

    @ApiModelProperty("用户账户")
    private String account;

    @ApiModelProperty("用户状态")
    private Integer status;
}
