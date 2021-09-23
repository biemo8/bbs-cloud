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
 * 用户角色表
 * </p>
 *
 *
 * @since 2019-10-15
 */
@Data
@ApiModel
public class SysUserRoleParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /**
     * 用户角色id
     */
    @ApiModelProperty("用户角色id")
    private Long userRoleId;

    /**
     * 账户id
     */
    @ApiModelProperty("账户id")
    private Long accountId;

    /**
     * 角色id
     */
    @ApiModelProperty("角色id")
    private Long roleId;

    @Override
    public String checkParam() {
        return null;
    }

}
