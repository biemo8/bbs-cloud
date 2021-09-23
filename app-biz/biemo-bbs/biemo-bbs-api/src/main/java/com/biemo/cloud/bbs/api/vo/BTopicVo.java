package com.biemo.cloud.bbs.api.vo;

import java.io.Serializable;

/**
 * 对象 b_topic
 *
 * @author makesoft
 * @date 2021-08-11
 */
public class BTopicVo implements Serializable
{
    private static final long serialVersionUID = 1L;

    private Long id;

    private Long type;

    private Long nodeId;

    private Long userId;

    private String title;

    private String content;

    private String imageList;

    private Integer recommend;

    private Long recommendTime;

    private Long viewCount;

    private Long commentCount;

    private Long likeCount;

    private Long status;

    private Long lastCommentTime;

    private Long lastCommentUserId;

    private String extraData;

    private Long createTime;

    private String tags;

    private String captchaId;

    private String captchaCode;

    public BTopicVo() {
    }

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
    public void setType(Long type)
    {
        this.type = type;
    }

    public Long getType()
    {
        return type;
    }
    public void setNodeId(Long nodeId)
    {
        this.nodeId = nodeId;
    }

    public Long getNodeId()
    {
        return nodeId;
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
    public void setContent(String content)
    {
        this.content = content;
    }

    public String getContent()
    {
        return content;
    }
    public void setImageList(String imageList)
    {
        this.imageList = imageList;
    }

    public String getImageList()
    {
        return imageList;
    }
    public void setRecommend(Integer recommend)
    {
        this.recommend = recommend;
    }

    public Integer getRecommend()
    {
        return recommend;
    }
    public void setRecommendTime(Long recommendTime)
    {
        this.recommendTime = recommendTime;
    }

    public Long getRecommendTime()
    {
        return recommendTime;
    }
    public void setViewCount(Long viewCount)
    {
        this.viewCount = viewCount;
    }

    public Long getViewCount()
    {
        return viewCount;
    }
    public void setCommentCount(Long commentCount)
    {
        this.commentCount = commentCount;
    }

    public Long getCommentCount()
    {
        return commentCount;
    }
    public void setLikeCount(Long likeCount)
    {
        this.likeCount = likeCount;
    }

    public Long getLikeCount()
    {
        return likeCount;
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
    public void setExtraData(String extraData)
    {
        this.extraData = extraData;
    }

    public String getExtraData()
    {
        return extraData;
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
}
