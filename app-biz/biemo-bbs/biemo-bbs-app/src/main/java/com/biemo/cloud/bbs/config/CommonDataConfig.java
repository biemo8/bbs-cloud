package com.biemo.cloud.bbs.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
@DependsOn("springContextHolder")
public class CommonDataConfig {

    //es 帖子索引名称
    @Value("${spring.elasticsearch.indexName.topicIndexName}")
    private String topicIndexName;

    //es 文章索引名称
    @Value("${spring.elasticsearch.indexName.articleIndexName}")
    private String articleIndexName;

    public CommonDataConfig() {
    }

    public String getTopicIndexName() {
        return topicIndexName;
    }

    public void setTopicIndexName(String topicIndexName) {
        this.topicIndexName = topicIndexName;
    }

    public String getArticleIndexName() {
        return articleIndexName;
    }

    public void setArticleIndexName(String articleIndexName) {
        this.articleIndexName = articleIndexName;
    }
}
