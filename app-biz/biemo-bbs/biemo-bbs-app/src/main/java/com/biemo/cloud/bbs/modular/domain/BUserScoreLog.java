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
 * 对象 b_user_score_log
 *
 * @author makesoft
 * @date 2021-08-11
 */
@TableName("b_user_score_log")
public class BUserScoreLog implements Serializable
{
    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private Long id;


    @TableField
    private Long userId;


    @TableField
    private String sourceType;


    @TableField
    private String sourceId;

    @TableField
    private String description;

    @TableField
    private Long type;

    @TableField
    private Long score;

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
    public void setSourceType(String sourceType)
    {
        this.sourceType = sourceType;
    }

    public String getSourceType()
    {
        return sourceType;
    }
    public void setSourceId(String sourceId)
    {
        this.sourceId = sourceId;
    }

    public String getSourceId()
    {
        return sourceId;
    }
    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }
    public void setType(Long type)
    {
        this.type = type;
    }

    public Long getType()
    {
        return type;
    }
    public void setScore(Long score)
    {
        this.score = score;
    }

    public Long getScore()
    {
        return score;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("sourceType", getSourceType())
            .append("sourceId", getSourceId())
            .append("description", getDescription())
            .append("type", getType())
            .append("score", getScore())
            .append("createTime", getCreateTime())
            .toString();
    }
}
