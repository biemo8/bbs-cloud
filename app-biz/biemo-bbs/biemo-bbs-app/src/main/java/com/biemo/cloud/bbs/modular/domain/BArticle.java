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
 * 对象 b_article
 *
 * @author makesoft
 * @date 2021-08-11
 */
@TableName("b_article")
public class BArticle implements Serializable
{
    private static final long serialVersionUID = 1L;


    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;


    @TableField
    private Long userId;


    @TableField
    private String title;


    @TableField
    private String summary;


    @TableField
    private String content;


    @TableField
    private String contentType;


    @TableField
    private Long status;


    @TableField
    private Integer share;


    @TableField
    private String sourceUrl;

    @TableField
    private Long createTime;

    @TableField
    private Long updateTime;

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    @TableField
    private Long viewCount;

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
    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getTitle()
    {
        return title;
    }
    public void setSummary(String summary)
    {
        this.summary = summary;
    }

    public String getSummary()
    {
        return summary;
    }
    public void setContent(String content)
    {
        this.content = content;
    }

    public String getContent()
    {
        return content;
    }
    public void setContentType(String contentType)
    {
        this.contentType = contentType;
    }

    public String getContentType()
    {
        return contentType;
    }
    public void setStatus(Long status)
    {
        this.status = status;
    }

    public Long getStatus()
    {
        return status;
    }
    public void setShare(Integer share)
    {
        this.share = share;
    }

    public Integer getShare()
    {
        return share;
    }
    public void setSourceUrl(String sourceUrl)
    {
        this.sourceUrl = sourceUrl;
    }

    public String getSourceUrl()
    {
        return sourceUrl;
    }
    public void setViewCount(Long viewCount)
    {
        this.viewCount = viewCount;
    }

    public Long getViewCount()
    {
        return viewCount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("title", getTitle())
            .append("summary", getSummary())
            .append("content", getContent())
            .append("contentType", getContentType())
            .append("status", getStatus())
            .append("share", getShare())
            .append("sourceUrl", getSourceUrl())
            .append("viewCount", getViewCount())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
