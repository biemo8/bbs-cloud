package com.biemo.cloud.system.modular.sys.model.params;

import com.biemo.cloud.kernel.model.validator.BaseValidatingParam;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 角色信息
 * </p>
 *
 *
 * @since 2019-09-10
 */
@Data
public class RoleInfo implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 角色名称
     */
    private String roleName;

    private Long accountId;

    @Override
    public String checkParam() {
        return null;
    }

}
