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
 * 角色权限表
 * </p>
 *
 *
 * @since 2019-10-15
 */
@Data
@ApiModel
public class SysRolePermissionParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /**
     * 角色权限id
     */
    @ApiModelProperty("角色权限id")
    private Long rolePermissionId;

    /**
     * 角色ID
     */
    @ApiModelProperty("角色ID")
    private Long roleId;

    /**
     * 权限ID
     */
    @ApiModelProperty("权限ID")
    private Long permissionId;

    @Override
    public String checkParam() {
        return null;
    }

}
