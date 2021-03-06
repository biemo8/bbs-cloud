package com.biemo.cloud.bbs.modular.domain;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.biemo.cloud.bbs.core.search.IndexSerializable;
import com.biemo.cloud.core.constant.Constants;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.biemo.cloud.bbs.modular.annotation.Excel;
import org.slf4j.Logger;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Set;


/**
 * 对象 b_topic
 *
 * @author makesoft
 * @date 2021-08-11
 */
@TableName("b_topic")
public class BTopic extends BaseEntity implements IndexSerializable
{
    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private Long id;

    @TableField
    private Long type;

    @TableField
    private Long nodeId;

    @TableField
    private Long userId;

    @TableField
    private String title;

    @TableField
    private String content;

    @TableField
    private String imageList;

    @TableField
    private Integer recommend;

    @TableField
    private Long recommendTime;

    @TableField
    private Long viewCount;

    @TableField
    private Long commentCount;

    @TableField
    private Long likeCount;

    @TableField
    private Long status;

    @TableField
    private Long lastCommentTime;

    @TableField
    private Long lastCommentUserId;

    @TableField
    private String extraData;

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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("type", getType())
            .append("nodeId", getNodeId())
            .append("userId", getUserId())
            .append("title", getTitle())
            .append("content", getContent())
            .append("imageList", getImageList())
            .append("recommend", getRecommend())
            .append("recommendTime", getRecommendTime())
            .append("viewCount", getViewCount())
            .append("commentCount", getCommentCount())
            .append("likeCount", getLikeCount())
            .append("status", getStatus())
            .append("lastCommentTime", getLastCommentTime())
            .append("lastCommentUserId", getLastCommentUserId())
            .append("createTime", getCreateTime())
            .append("extraData", getExtraData())
            .toString();
    }

    @Override
    public String toElasticsearchId() {
        return Objects.nonNull(id)?id.toString():null;
    }

    public BTopic toElasticsearchObject(){
        BTopic topic = new BTopic();
        topic.setId(id);
        topic.setTitle(title);
        topic.setContent(content);
        return topic;
    }

    @Override
    public String toElasticsearchJson(Logger logger, ObjectMapper objectMapper) {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        try {
            return objectMapper.writeValueAsString(this.toElasticsearchObject());
        } catch (Exception e) {
            logger.error("对象转换JSON错误，错误如下：{}", e.getMessage());
        }
        return Constants.EMPTY;
    }

    /** 标红 */
    public void highlighterText(Set<String> exps){
        exps.forEach(exp -> {
            title = title == null ? null : title.replaceAll(exp, "<b style='color:red'>"+ exp +"</b>");
            content = content == null ? null : content.replaceAll(exp, "<b style='color:red'>"+ exp +"</b>");
        });
    }
}
