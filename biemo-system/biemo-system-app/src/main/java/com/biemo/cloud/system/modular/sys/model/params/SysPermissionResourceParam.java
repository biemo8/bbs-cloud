package com.biemo.cloud.system.modular.sys.model.params;

import lombok.Data;
import com.biemo.cloud.kernel.model.validator.BaseValidatingParam;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 权限资源表
 * </p>
 *
 *
 * @since 2019-10-15
 */
@Data
@ApiModel
public class SysPermissionResourceParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /**
     * 权限资源id
     */
    @ApiModelProperty("权限资源id")
    private Long permissionResourceId;

    /**
     * 权限ID
     */
    @ApiModelProperty("权限ID")
    private Long permissionId;

    /**
     * 资源ID
     */
    @ApiModelProperty("资源ID")
    private String resourceId;

    @Override
    public String checkParam() {
        return null;
    }

}
