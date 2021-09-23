package com.biemo.cloud.bbs.api.vo;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import java.io.Serializable;


/**
 * 对象 b_article
 *
 * @author makesoft
 * @date 2021-08-11
 */
public class BArticleVo implements Serializable
{
    private static final long serialVersionUID = 1L;


    private Long id;


    private Long userId;


    private String title;


    private String summary;


    private String content;


    private String contentType;


    private Long status;


    private Integer share;


    private String sourceUrl;

    private Long createTime;

    private Long updateTime;

    private String tags;

    private String captchaId;

    private String captchaCode;

    public BArticleVo() {
    }
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


    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getCaptchaId() {
        return captchaId;
    }

    public void setCaptchaId(String captchaId) {
        this.captchaId = captchaId;
    }

    public String getCaptchaCode() {
        return captchaCode;
    }

    public void setCaptchaCode(String captchaCode) {
        this.captchaCode = captchaCode;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
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
