package com.biemo.cloud.system.modular.ent.model.params;

import com.biemo.cloud.core.util.ToolUtil;
import lombok.Data;
import com.biemo.cloud.kernel.model.validator.BaseValidatingParam;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.NonNull;

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
public class EntUserDeptParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

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
     * 公司id
     */
    @ApiModelProperty("公司id")
    private Long companyId;

    /**
     * 是否为 默认部门('N'代表否,'Y'代表是)
     */
    @ApiModelProperty("是否为 默认部门('N'代表否,'Y'代表是)")
    private String defaultFlag;

    @Override
    public String checkParam() {
        return null;
    }

}
