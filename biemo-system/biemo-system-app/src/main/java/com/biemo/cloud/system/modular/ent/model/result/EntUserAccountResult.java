package com.biemo.cloud.system.modular.ent.model.result;

import lombok.Data;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 登录账号
 * </p>
 *
 *
 * @since 2019-10-15
 */
@Data
@ApiModel
public class EntUserAccountResult implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 账号id
     */
    @ApiModelProperty("账号id")
    private Long accountId;

    /**
     * 账号(工卡号)
     */
    @ApiModelProperty("账号(工卡号)")
    private String account;

    /**
     * 密码
     */
    @ApiModelProperty("密码")
    private String password;

    /**
     * 密码盐
     */
    @ApiModelProperty("密码盐")
    private String salt;

    /**
     * 用户信息id
     */
    @ApiModelProperty("用户信息id")
    private Long userId;

    /**
     * 默认登录公司id
     */
    @ApiModelProperty("默认登录公司id")
    private Long companyId;

    /**
     * 创建时间
     */
    @ApiModelProperty(hidden = true)
    private Date createTime;

    /**
     * 创建人
     */
    @ApiModelProperty(hidden = true)
    private Long createUser;

    /**
     * 修改时间
     */
    @ApiModelProperty(hidden = true)
    private Date updateTime;

    /**
     * 修改人
     */
    @ApiModelProperty(hidden = true)
    private Long updateUser;

    /**
     * 删除标记
     */
    @ApiModelProperty(hidden = true)
    private String delFlag;

}
