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
 * 对象 b_check_in
 *
 * @author makesoft
 * @date 2021-08-11
 */
@TableName("b_check_in")
public class BCheckIn implements Serializable
{
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;


    @TableField
    private Long userId;


    @TableField
    private Long latestDayName;


    @TableField
    private Long consecutiveDays;

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
    public void setLatestDayName(Long latestDayName)
    {
        this.latestDayName = latestDayName;
    }

    public Long getLatestDayName()
    {
        return latestDayName;
    }
    public void setConsecutiveDays(Long consecutiveDays)
    {
        this.consecutiveDays = consecutiveDays;
    }

    public Long getConsecutiveDays()
    {
        return consecutiveDays;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("latestDayName", getLatestDayName())
            .append("consecutiveDays", getConsecutiveDays())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
