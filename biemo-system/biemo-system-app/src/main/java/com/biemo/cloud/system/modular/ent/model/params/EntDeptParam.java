package com.biemo.cloud.system.modular.ent.model.params;

import lombok.Data;
import com.biemo.cloud.kernel.model.validator.BaseValidatingParam;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 部门表
 * </p>
 *
 *
 * @since 2019-10-15
 */
@Data
@ApiModel
public class EntDeptParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /**
     * 部门id
     */
    @ApiModelProperty("部门id")
    private Long deptId;

    /**
     * 企业id
     */
    @ApiModelProperty("企业id")
    private Long companyId;

    /**
     * 部门名称
     */
    @ApiModelProperty("部门名称")
    private String deptName;

    /**
     * 部门简称
     */
    @ApiModelProperty("部门简称")
    private String deptShortName;

    /**
     * 部门编码
     */
    @ApiModelProperty("部门编码")
    private String deptCode;

    /**
     * 上级部门
     */
    @ApiModelProperty("上级部门")
    private Long parentId;

    /**
     * 状态（1:启用，2：禁用）
     */
    @ApiModelProperty("状态（1:启用，2：禁用）")
    private Integer status;

    /**
     * 排序
     */
    @ApiModelProperty("排序")
    private BigDecimal orderNo;

    /**
     * 部门描述
     */
    @ApiModelProperty("部门描述")
    private String description;

    @Override
    public String checkParam() {
        return null;
    }

}
