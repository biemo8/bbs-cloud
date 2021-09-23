package com.biemo.cloud.auth.modular.sso.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 登录日志表
 * </p>
 *
 *
 * @since 2019-09-27
 */
@TableName("biemo_auth_login_log")
public class AuthLoginLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "login_log_id", type = IdType.AUTO)
    private Long loginLogId;

    /**
     * 账号
     */
    @TableField("account")
    private String account;

    /**
     * 姓名
     */
    @TableField("name")
    private String name;

    /**
     * 公司id
     */
    @TableField("company_id")
    private Long companyId;

    /**
     * 应用客户端id
     */
    @TableField("client_id")
    private Long clientId;

    /**
     * 操作内容
     */
    @TableField("operation")
    private String operation;

    /**
     * 登录ip
     */
    @TableField("ip_address")
    private String ipAddress;

    /**
     * 登录地点
     */
    @TableField("local_address")
    private String localAddress;

    /**
     * 登陆时间
     */
    @TableField("login_time")
    private Date loginTime;


    public Long getLoginLogId() {
        return loginLogId;
    }

    public void setLoginLogId(Long loginLogId) {
        this.loginLogId = loginLogId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getLocalAddress() {
        return localAddress;
    }

    public void setLocalAddress(String localAddress) {
        this.localAddress = localAddress;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    @Override
    public String toString() {
        return "AuthLoginLog{" +
        "loginLogId=" + loginLogId +
        ", account=" + account +
        ", name=" + name +
        ", companyId=" + companyId +
        ", clientId=" + clientId +
        ", operation=" + operation +
        ", ipAddress=" + ipAddress +
        ", localAddress=" + localAddress +
        ", loginTime=" + loginTime +
        "}";
    }
}
