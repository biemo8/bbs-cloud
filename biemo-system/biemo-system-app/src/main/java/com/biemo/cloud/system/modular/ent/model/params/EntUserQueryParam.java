package com.biemo.cloud.system.modular.ent.model.params;

import com.biemo.cloud.core.util.ToolUtil;
import com.biemo.cloud.kernel.model.validator.BaseValidatingParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
public class EntUserQueryParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /**
     * 公司id
     */
    @ApiModelProperty("公司id")
    private Long companyId;

    /**
     * 部门id
     */
    @ApiModelProperty("部门id")
    private Long deptId;

    /**
     * 人员姓名
     */
    @ApiModelProperty("人员姓名")
    private String name;

    /**
     * 账号
     */
    @ApiModelProperty("账号")
    private String account;



}
