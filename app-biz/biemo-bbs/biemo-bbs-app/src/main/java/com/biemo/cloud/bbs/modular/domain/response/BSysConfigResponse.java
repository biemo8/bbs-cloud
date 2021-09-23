package com.biemo.cloud.bbs.modular.domain.response;

import java.util.List;
import java.util.Map;

public class BSysConfigResponse {

    private boolean articlePending;

    private boolean createArticleEmailVerified;

    private boolean createCommentEmailVerified;

    private boolean createTopicEmailVerified;

    private Long defaultNodeId;

    private Map loginMethod;

    private List<String> recommendTags;

    private Map scoreConfig;

    private String siteDescription;

    private List<String> siteKeywords;

    private List<Map<String,Object>> siteNavs;

    private String siteNotification;

    private String siteTitle;

    private Long tokenExpireDays;

    private boolean topicCaptcha;

    private boolean urlRedirect;

    private boolean userObserveSeconds;

    public BSysConfigResponse() {
    }

    public boolean isArticlePending() {
        return articlePending;
    }

    public void setArticlePending(boolean articlePending) {
        this.articlePending = articlePending;
    }

    public boolean isCreateArticleEmailVerified() {
        return createArticleEmailVerified;
    }

    public void setCreateArticleEmailVerified(boolean createArticleEmailVerified) {
        this.createArticleEmailVerified = createArticleEmailVerified;
    }

    public boolean isCreateCommentEmailVerified() {
        return createCommentEmailVerified;
    }

    public void setCreateCommentEmailVerified(boolean createCommentEmailVerified) {
        this.createCommentEmailVerified = createCommentEmailVerified;
    }

    public boolean isCreateTopicEmailVerified() {
        return createTopicEmailVerified;
    }

    public void setCreateTopicEmailVerified(boolean createTopicEmailVerified) {
        this.createTopicEmailVerified = createTopicEmailVerified;
    }

    public Long getDefaultNodeId() {
        return defaultNodeId;
    }

    public void setDefaultNodeId(Long defaultNodeId) {
        this.defaultNodeId = defaultNodeId;
    }

    public Map getLoginMethod() {
        return loginMethod;
    }

    public void setLoginMethod(Map loginMethod) {
        this.loginMethod = loginMethod;
    }

    public List<String> getRecommendTags() {
        return recommendTags;
    }

    public void setRecommendTags(List<String> recommendTags) {
        this.recommendTags = recommendTags;
    }

    public Map getScoreConfig() {
        return scoreConfig;
    }

    public void setScoreConfig(Map scoreConfig) {
        this.scoreConfig = scoreConfig;
    }

    public String getSiteDescription() {
        return siteDescription;
    }

    public void setSiteDescription(String siteDescription) {
        this.siteDescription = siteDescription;
    }

    public List<String> getSiteKeywords() {
        return siteKeywords;
    }

    public void setSiteKeywords(List<String> siteKeywords) {
        this.siteKeywords = siteKeywords;
    }

    public List<Map<String, Object>> getSiteNavs() {
        return siteNavs;
    }

    public void setSiteNavs(List<Map<String, Object>> siteNavs) {
        this.siteNavs = siteNavs;
    }

    public String getSiteNotification() {
        return siteNotification;
    }

    public void setSiteNotification(String siteNotification) {
        this.siteNotification = siteNotification;
    }

    public String getSiteTitle() {
        return siteTitle;
    }

    public void setSiteTitle(String siteTitle) {
        this.siteTitle = siteTitle;
    }

    public Long getTokenExpireDays() {
        return tokenExpireDays;
    }

    public void setTokenExpireDays(Long tokenExpireDays) {
        this.tokenExpireDays = tokenExpireDays;
    }

    public boolean isTopicCaptcha() {
        return topicCaptcha;
    }

    public void setTopicCaptcha(boolean topicCaptcha) {
        this.topicCaptcha = topicCaptcha;
    }

    public boolean isUrlRedirect() {
        return urlRedirect;
    }

    public void setUrlRedirect(boolean urlRedirect) {
        this.urlRedirect = urlRedirect;
    }

    public boolean isUserObserveSeconds() {
        return userObserveSeconds;
    }

    public void setUserObserveSeconds(boolean userObserveSeconds) {
        this.userObserveSeconds = userObserveSeconds;
    }
}
