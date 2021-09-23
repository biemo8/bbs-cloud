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
 * 对象 b_topic_node
 *
 * @author makesoft
 * @date 2021-08-11
 */
@TableName("b_topic_node")
public class BTopicNode implements Serializable
{
    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private Long id;

    @TableField
    private String name;

    @TableField
    private String description;

    @TableField
    private String logo;

    @TableField
    private Long sortNo;

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
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }
    public void setLogo(String logo)
    {
        this.logo = logo;
    }

    public String getLogo()
    {
        return logo;
    }
    public void setSortNo(Long sortNo)
    {
        this.sortNo = sortNo;
    }

    public Long getSortNo()
    {
        return sortNo;
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
            .append("name", getName())
            .append("description", getDescription())
            .append("logo", getLogo())
            .append("sortNo", getSortNo())
            .append("status", getStatus())
            .append("createTime", getCreateTime())
            .toString();
    }
}
