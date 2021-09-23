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
 * 对象 b_message
 *
 * @author makesoft
 * @date 2021-08-11
 */
@TableName("b_message")
public class BMessage implements Serializable
{
    private static final long serialVersionUID = 1L;
    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private Long id;

    @TableField
    private Long fromId;

    @TableField
    private Long userId;

    @TableField
    private String title;

    @TableField
    private String content;

    @TableField
    private String quoteContent;

    @TableField
    private Long type;

    @TableField
    private String extraData;

    @TableField
    private Long status;

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
    public void setFromId(Long fromId)
    {
        this.fromId = fromId;
    }

    public Long getFromId()
    {
        return fromId;
    }
    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getUserId()
    {
        return userId;
    }
    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getTitle()
    {
        return title;
    }
    public void setContent(String content)
    {
        this.content = content;
    }

    public String getContent()
    {
        return content;
    }
    public void setQuoteContent(String quoteContent)
    {
        this.quoteContent = quoteContent;
    }

    public String getQuoteContent()
    {
        return quoteContent;
    }
    public void setType(Long type)
    {
        this.type = type;
    }

    public Long getType()
    {
        return type;
    }
    public void setExtraData(String extraData)
    {
        this.extraData = extraData;
    }

    public String getExtraData()
    {
        return extraData;
    }
    public void setStatus(Long status)
    {
        this.status = status;
    }

    public Long getStatus()
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("fromId", getFromId())
            .append("userId", getUserId())
            .append("title", getTitle())
            .append("content", getContent())
            .append("quoteContent", getQuoteContent())
            .append("type", getType())
            .append("extraData", getExtraData())
            .append("status", getStatus())
            .append("createTime", getCreateTime())
            .toString();
    }
}
