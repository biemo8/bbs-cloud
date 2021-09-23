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
 * 对象 b_user
 *
 * @author makesoft
 * @date 2021-08-11
 */
@TableName("b_user")
public class BUser implements Serializable
{
    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    @TableField
    private String username;

    @TableField
    private String email;

    @TableField
    private Integer emailVerified;

    @TableField
    private String nickname;

    @TableField
    private String avatar;

    @TableField
    private String backgroundImage;

    @TableField
    private String password;

    @TableField
    private String homePage;

    @TableField
    private String description;

    @TableField
    private Long score;

    @TableField
    private Long status;

    @TableField
    private Long topicCount;

    @TableField
    private Long commentCount;

    @TableField
    private String roles;

    @TableField
    private Long type;

    @TableField
    private Long forbiddenEndTime;

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
    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getUsername()
    {
        return username;
    }
    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getEmail()
    {
        return email;
    }
    public void setEmailVerified(Integer emailVerified)
    {
        this.emailVerified = emailVerified;
    }

    public Integer getEmailVerified()
    {
        return emailVerified;
    }
    public void setNickname(String nickname)
    {
        this.nickname = nickname;
    }

    public String getNickname()
    {
        return nickname;
    }
    public void setAvatar(String avatar)
    {
        this.avatar = avatar;
    }

    public String getAvatar()
    {
        return avatar;
    }
    public void setBackgroundImage(String backgroundImage)
    {
        this.backgroundImage = backgroundImage;
    }

    public String getBackgroundImage()
    {
        return backgroundImage;
    }
    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getPassword()
    {
        return password;
    }
    public void setHomePage(String homePage)
    {
        this.homePage = homePage;
    }

    public String getHomePage()
    {
        return homePage;
    }
    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }
    public void setScore(Long score)
    {
        this.score = score;
    }

    public Long getScore()
    {
        return score;
    }
    public void setStatus(Long status)
    {
        this.status = status;
    }

    public Long getStatus()
    {
        return status;
    }
    public void setTopicCount(Long topicCount)
    {
        this.topicCount = topicCount;
    }

    public Long getTopicCount()
    {
        return topicCount;
    }
    public void setCommentCount(Long commentCount)
    {
        this.commentCount = commentCount;
    }

    public Long getCommentCount()
    {
        return commentCount;
    }
    public void setRoles(String roles)
    {
        this.roles = roles;
    }

    public String getRoles()
    {
        return roles;
    }
    public void setType(Long type)
    {
        this.type = type;
    }

    public Long getType()
    {
        return type;
    }
    public void setForbiddenEndTime(Long forbiddenEndTime)
    {
        this.forbiddenEndTime = forbiddenEndTime;
    }

    public Long getForbiddenEndTime()
    {
        return forbiddenEndTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("username", getUsername())
            .append("email", getEmail())
            .append("emailVerified", getEmailVerified())
            .append("nickname", getNickname())
            .append("avatar", getAvatar())
            .append("backgroundImage", getBackgroundImage())
            .append("password", getPassword())
            .append("homePage", getHomePage())
            .append("description", getDescription())
            .append("score", getScore())
            .append("status", getStatus())
            .append("topicCount", getTopicCount())
            .append("commentCount", getCommentCount())
            .append("roles", getRoles())
            .append("type", getType())
            .append("forbiddenEndTime", getForbiddenEndTime())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
