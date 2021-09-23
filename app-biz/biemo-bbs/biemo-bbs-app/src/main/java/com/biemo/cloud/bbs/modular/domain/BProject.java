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
 * 对象 b_project
 *
 * @author makesoft
 * @date 2021-08-11
 */
@TableName("b_project")
public class BProject implements Serializable
{
    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private Long id;

    @TableField
    private Long userId;

    @TableField
    private String name;

    @TableField
    private String title;

    @TableField
    private String logo;

    @TableField
    private String url;

    @TableField
    private String docUrl;

    @TableField
    private String downloadUrl;

    @TableField
    private String contentType;

    @TableField
    private String content;

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
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getTitle()
    {
        return title;
    }
    public void setLogo(String logo)
    {
        this.logo = logo;
    }

    public String getLogo()
    {
        return logo;
    }
    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getUrl()
    {
        return url;
    }
    public void setDocUrl(String docUrl)
    {
        this.docUrl = docUrl;
    }

    public String getDocUrl()
    {
        return docUrl;
    }
    public void setDownloadUrl(String downloadUrl)
    {
        this.downloadUrl = downloadUrl;
    }

    public String getDownloadUrl()
    {
        return downloadUrl;
    }
    public void setContentType(String contentType)
    {
        this.contentType = contentType;
    }

    public String getContentType()
    {
        return contentType;
    }
    public void setContent(String content)
    {
        this.content = content;
    }

    public String getContent()
    {
        return content;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("name", getName())
            .append("title", getTitle())
            .append("logo", getLogo())
            .append("url", getUrl())
            .append("docUrl", getDocUrl())
            .append("downloadUrl", getDownloadUrl())
            .append("contentType", getContentType())
            .append("content", getContent())
            .append("createTime", getCreateTime())
            .toString();
    }
}
