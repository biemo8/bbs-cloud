package com.biemo.cloud.auth.modular.sso.entity;

import com.baomidou.mybatisplus.annotation.*;

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
@TableName("biemo_auth_client")
public class AuthClient implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 客户端id
     */
    @TableId(value = "client_id", type = IdType.ASSIGN_ID)
    private Long clientId;

    /**
     * appId应用（企业表appId）
     */
    @TableField("app_id")
    private Long appId;

    /**
     * 客户端名称
     */
    @TableField("name")
    private String name;

    /**
     * 客户端接受sso回调的地址
     */
    @TableField("sso_url")
    private String ssoUrl;

    /**
     * 客户端类型 1-WEB浏览器，2-APP手机
     */
    @TableField("client_type")
    private Integer clientType;

    /**
     * 登录类型: 1-应用自定义页面, 2-统一的登录页面
     */
    @TableField("login_type")
    private Integer loginType;

    /**
     * 颁发和解析token用的私钥
     */
    @TableField("private_key")
    private String privateKey;

    /**
     * 签名私钥
     */
    @TableField("sign_private_key")
    private String signPrivateKey;

    /**
     * 数据私钥
     */
    @TableField("data_private_key")
    private String dataPrivateKey;

    /**
     * token过期时间，单位是秒
     */
    @TableField("token_exp")
    private Integer tokenExp;

    /**
     * 刷新token过期时间，单位是秒
     */
    @TableField("refresh_token_exp")
    private Integer refreshTokenExp;

    /**
     * 退出登录的url
     */
    @TableField("login_out_url")
    private String loginOutUrl;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 创建人
     */
    @TableField(value = "create_user", fill = FieldFill.INSERT)
    private Long createUser;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private Date updateTime;

    /**
     * 更新人
     */
    @TableField(value = "update_user", fill = FieldFill.UPDATE)
    private Long updateUser;

    /**
     * 是否删除：Y-被删除，N-未删除
     */
    @TableField("del_flag")
    private String delFlag;


    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSsoUrl() {
        return ssoUrl;
    }

    public void setSsoUrl(String ssoUrl) {
        this.ssoUrl = ssoUrl;
    }

    public Integer getClientType() {
        return clientType;
    }

    public void setClientType(Integer clientType) {
        this.clientType = clientType;
    }

    public Integer getLoginType() {
        return loginType;
    }

    public void setLoginType(Integer loginType) {
        this.loginType = loginType;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getSignPrivateKey() {
        return signPrivateKey;
    }

    public void setSignPrivateKey(String signPrivateKey) {
        this.signPrivateKey = signPrivateKey;
    }

    public String getDataPrivateKey() {
        return dataPrivateKey;
    }

    public void setDataPrivateKey(String dataPrivateKey) {
        this.dataPrivateKey = dataPrivateKey;
    }

    public Integer getTokenExp() {
        return tokenExp;
    }

    public void setTokenExp(Integer tokenExp) {
        this.tokenExp = tokenExp;
    }

    public Integer getRefreshTokenExp() {
        return refreshTokenExp;
    }

    public void setRefreshTokenExp(Integer refreshTokenExp) {
        this.refreshTokenExp = refreshTokenExp;
    }

    public String getLoginOutUrl() {
        return loginOutUrl;
    }

    public void setLoginOutUrl(String loginOutUrl) {
        this.loginOutUrl = loginOutUrl;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        return "AuthClient{" +
        "clientId=" + clientId +
        ", appId=" + appId +
        ", name=" + name +
        ", ssoUrl=" + ssoUrl +
        ", clientType=" + clientType +
        ", loginType=" + loginType +
        ", privateKey=" + privateKey +
        ", signPrivateKey=" + signPrivateKey +
        ", dataPrivateKey=" + dataPrivateKey +
        ", tokenExp=" + tokenExp +
        ", refreshTokenExp=" + refreshTokenExp +
        ", loginOutUrl=" + loginOutUrl +
        ", createTime=" + createTime +
        ", createUser=" + createUser +
        ", updateTime=" + updateTime +
        ", updateUser=" + updateUser +
        ", delFlag=" + delFlag +
        "}";
    }
}
