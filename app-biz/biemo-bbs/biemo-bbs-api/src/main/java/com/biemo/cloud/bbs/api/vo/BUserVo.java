package com.biemo.cloud.bbs.api.vo;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;


/**
 * 对象 b_user
 *
 * @author makesoft
 * @date 2021-08-11
 */
public class BUserVo implements Serializable
{
    private static final long serialVersionUID = 1L;

    private Long id;

    private String username;

    private String email;

    private Integer emailVerified;

    private String nickname;

    private String avatar;

    private String backgroundImage;

    private String password;

    private String homePage;

    private String description;

    private Long score;

    private Long status;

    private Long topicCount;

    private Long commentCount;

    private String roles;

    private Long type;

    private Long forbiddenEndTime;

    private Long createTime;

    private Long updateTime;

    private String captchaId;

    private String captchaCode;

    private String rePassword;

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

    public String getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
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

}
