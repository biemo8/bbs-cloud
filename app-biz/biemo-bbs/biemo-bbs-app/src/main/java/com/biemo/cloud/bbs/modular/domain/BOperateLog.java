package com.biemo.cloud.bbs.modular.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.biemo.cloud.bbs.modular.annotation.Excel;

import java.io.Serializable;
import java.util.Date;


/**
 * 对象 b_operate_log
 *
 * @author makesoft
 * @date 2021-08-11
 */
@TableName("b_operate_log")
public class BOperateLog implements Serializable
{
    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private Long id;

    @TableField
    private Long userId;

    @TableField
    private String opType;

    @TableField
    private String dataType;

    @TableField
    private Long dataId;

    @TableField
    private String description;

    @TableField
    private String ip;

    @TableField
    private String userAgent;

    @TableField
    private String referer;

    @TableField
    private Long createTime;


    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getUserId()
    {
        return userId;
    }
    public void setOpType(String opType)
    {
        this.opType = opType;
    }

    public String getOpType()
    {
        return opType;
    }
    public void setDataType(String dataType)
    {
        this.dataType = dataType;
    }

    public String getDataType()
    {
        return dataType;
    }
    public void setDataId(Long dataId)
    {
        this.dataId = dataId;
    }

    public Long getDataId()
    {
        return dataId;
    }
    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }
    public void setIp(String ip)
    {
        this.ip = ip;
    }

    public String getIp()
    {
        return ip;
    }
    public void setUserAgent(String userAgent)
    {
        this.userAgent = userAgent;
    }

    public String getUserAgent()
    {
        return userAgent;
    }
    public void setReferer(String referer)
    {
        this.referer = referer;
    }

    public String getReferer()
    {
        return referer;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("opType", getOpType())
            .append("dataType", getDataType())
            .append("dataId", getDataId())
            .append("description", getDescription())
            .append("ip", getIp())
            .append("userAgent", getUserAgent())
            .append("referer", getReferer())
            .append("createTime", getCreateTime())
            .toString();
    }
}
