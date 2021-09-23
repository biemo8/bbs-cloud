package com.biemo.cloud.auth.modular.sso.model.params;

import com.biemo.cloud.kernel.model.validator.BaseValidatingParam;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 账号二次认证表
 * </p>
 *
 *
 * @since 2019-09-27
 */
@Data
public class AuthCertAccountParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 账号(工卡号)
     */
    private String account;

    /**
     * 二次认证标识（0：禁用；1：开启）
     */
    private Integer secondCheck;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 创建人
     */
    private Long createUser;

    /**
     * 修改人
     */
    private Long updateUser;

    @Override
    public String checkParam() {
        return null;
    }

}
