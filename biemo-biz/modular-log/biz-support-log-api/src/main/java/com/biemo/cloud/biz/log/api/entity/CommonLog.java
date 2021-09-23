package com.biemo.cloud.biz.log.api.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 *
 * @since 2018-08-01
 */
@TableName("log_common_log")
public class CommonLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;
    /**
     * 应用编码
     */
    @TableField("APP_CODE")
    private String appCode;
    /**
     * 日志级别 info，error，warn，debug
     */
    @TableField("LEVEL")
    private String level;
    /**
     * 类名
     */
    @TableField("CLASS_NAME")
    private String className;
    /**
     * 打日志的方法的名称
     */
    @TableField("METHOD_NAME")
    private String methodName;
    /**
     * 远程访问IP地址
     */
    @TableField("IP")
    private String ip;
    /**
     * 用户账号id
     */
    @TableField("ACCOUNT_ID")
    private String accountId;
    /**
     * 日志号
     */
    @TableField("REQUEST_NO")
    private String requestNo;
    /**
     * 请求地址
     */
    @TableField("URL")
    private String url;
    /**
     * 请求的数据内容
     */
    @TableField("REQUEST_DATA")
    private String requestData;
    /**
     * 日志详情
     */
    @TableField("LOG_CONTENT")
    private String logContent;
    /**
     * 创建时间
     */
    @TableField("CREATE_TIMESTAMP")
    private Long createTimestamp;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getRequestNo() {
        return requestNo;
    }

    public void setRequestNo(String requestNo) {
        this.requestNo = requestNo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRequestData() {
        return requestData;
    }

    public void setRequestData(String requestData) {
        this.requestData = requestData;
    }

    public String getLogContent() {
        return logContent;
    }

    public void setLogContent(String logContent) {
        this.logContent = logContent;
    }

    public Long getCreateTimestamp() {
        return createTimestamp;
    }

    public void setCreateTimestamp(Long createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    @Override
    public String toString() {
        return "CommonLog{" +
                ", id=" + id +
                ", appCode=" + appCode +
                ", level=" + level +
                ", className=" + className +
                ", methodName=" + methodName +
                ", ip=" + ip +
                ", accountId=" + accountId +
                ", requestNo=" + requestNo +
                ", url=" + url +
                ", requestData=" + requestData +
                ", logContent=" + logContent +
                ", createTimestamp=" + createTimestamp +
                "}";
    }
}
