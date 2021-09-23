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
 * 对象 b_topic_tag
 *
 * @author makesoft
 * @date 2021-08-11
 */
@TableName("b_topic_tag")
public class BTopicTag implements Serializable
{
    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private Long id;

    @TableField
    private Long topicId;

    @TableField
    private Long tagId;

    @TableField
    private Long status;

    @TableField
    private Long lastCommentTime;

    @TableField
    private Long lastCommentUserId;

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
    public void setTopicId(Long topicId)
    {
        this.topicId = topicId;
    }

    public Long getTopicId()
    {
        return topicId;
    }
    public void setTagId(Long tagId)
    {
        this.tagId = tagId;
    }

    public Long getTagId()
    {
        return tagId;
    }
    public void setStatus(Long status)
    {
        this.status = status;
    }

    public Long getStatus()
    {
        return status;
    }
    public void setLastCommentTime(Long lastCommentTime)
    {
        this.lastCommentTime = lastCommentTime;
    }

    public Long getLastCommentTime()
    {
        return lastCommentTime;
    }
    public void setLastCommentUserId(Long lastCommentUserId)
    {
        this.lastCommentUserId = lastCommentUserId;
    }

    public Long getLastCommentUserId()
    {
        return lastCommentUserId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("topicId", getTopicId())
            .append("tagId", getTagId())
            .append("status", getStatus())
            .append("lastCommentTime", getLastCommentTime())
            .append("lastCommentUserId", getLastCommentUserId())
            .append("createTime", getCreateTime())
            .toString();
    }
}
