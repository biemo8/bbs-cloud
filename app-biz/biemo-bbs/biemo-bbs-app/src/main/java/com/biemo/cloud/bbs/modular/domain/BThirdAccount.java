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
 * 对象 b_third_account
 *
 * @author makesoft
 * @date 2021-08-11
 */
@TableName("b_third_account")
public class BThirdAccount implements Serializable
{
    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private Long id;

    @TableField
    private Long userId;

    @TableField
    private String avatar;

    @TableField
    private String nickname;

    @TableField
    private String thirdType;

    @TableField
    private String thirdId;

    @TableField
    private String extraData;

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
    public void setAvatar(String avatar)
    {
        this.avatar = avatar;
    }

    public String getAvatar()
    {
        return avatar;
    }
    public void setNickname(String nickname)
    {
        this.nickname = nickname;
    }

    public String getNickname()
    {
        return nickname;
    }
    public void setThirdType(String thirdType)
    {
        this.thirdType = thirdType;
    }

    public String getThirdType()
    {
        return thirdType;
    }
    public void setThirdId(String thirdId)
    {
        this.thirdId = thirdId;
    }

    public String getThirdId()
    {
        return thirdId;
    }
    public void setExtraData(String extraData)
    {
        this.extraData = extraData;
    }

    public String getExtraData()
    {
        return extraData;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("avatar", getAvatar())
            .append("nickname", getNickname())
            .append("thirdType", getThirdType())
            .append("thirdId", getThirdId())
            .append("extraData", getExtraData())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
