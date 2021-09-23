package com.biemo.cloud.auth.modular.sso.model.params;

import com.biemo.cloud.kernel.model.validator.BaseValidatingParam;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 应用表
 * </p>
 *
 *
 * @since 2019-09-27
 */
@Data
public class AuthClientParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /**
     * 客户端id
     */
    private Long clientId;

    /**
     * appId应用（企业表appId）
     */
    private Long appId;

    /**
     * 客户端名称
     */
    private String name;

    /**
     * 客户端接受sso回调的地址
     */
    private String ssoUrl;

    /**
     * 客户端类型 1-WEB浏览器，2-APP手机
     */
    private Integer clientType;

    /**
     * 登录类型: 1-应用自定义页面, 2-统一的登录页面
     */
    private Integer loginType;

    /**
     * 颁发和解析token用的私钥
     */
    private String privateKey;

    /**
     * 签名私钥
     */
    private String signPrivateKey;

    /**
     * 数据私钥
     */
    private String dataPrivateKey;

    /**
     * token过期时间，单位是秒
     */
    private Integer tokenExp;

    /**
     * 刷新token过期时间，单位是秒
     */
    private Integer refreshTokenExp;

    /**
     * 退出登录的url
     */
    private String loginOutUrl;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private Long createUser;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 更新人
     */
    private Long updateUser;

    /**
     * 是否删除：Y-被删除，N-未删除
     */
    private String delFlag;

    @Override
    public String checkParam() {
        return null;
    }

}
