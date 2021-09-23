package com.biemo.cloud.system.modular.ent.model.params;

import com.biemo.cloud.core.util.ToolUtil;
import com.biemo.cloud.kernel.model.validator.BaseValidatingParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

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
public class EntUserRoleParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    private Long userId;

    /**
     * 角色id的集合
     */
    @ApiModelProperty("角色id的集合")
    private Long[] roleIds;


    @Override
    public String checkParam() {
        if (ToolUtil.isEmpty(userId)){
            return "用户id为空";
        }
        if (ToolUtil.isEmpty(roleIds)){
            return "角色id信息为空";
        }
        return null;
    }

}
