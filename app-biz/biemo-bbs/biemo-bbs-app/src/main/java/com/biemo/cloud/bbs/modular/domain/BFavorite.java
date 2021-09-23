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
 * 对象 b_favorite
 *
 * @author makesoft
 * @date 2021-08-11
 */
@TableName("b_favorite")
public class BFavorite implements Serializable
{
    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private Long id;

    @TableField
    private Long userId;

    @TableField
    private String entityType;

    @TableField
    private Long entityId;

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
    public void setEntityType(String entityType)
    {
        this.entityType = entityType;
    }

    public String getEntityType()
    {
        return entityType;
    }
    public void setEntityId(Long entityId)
    {
        this.entityId = entityId;
    }

    public Long getEntityId()
    {
        return entityId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("entityType", getEntityType())
            .append("entityId", getEntityId())
            .append("createTime", getCreateTime())
            .toString();
    }
}
