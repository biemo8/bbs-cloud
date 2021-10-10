package com.biemo.cloud.bbs.modular.service;

import org.springframework.context.ApplicationEvent;

/**
 * ES索引的 创建、构建、保存和删除操作
 * @param <T>
 */
public interface IndexService<T> extends SearcherService {
    // 批量构建时的每次从数据库查询数量
    int BATCH_SIZE = 1000;
    // 加载数据超过200 时用批量加载 减少数据库连接
    int BATCH_LOAD_SIZE = 200;

    /**
     * 创建索引
     * @param shards  分片数
     * @param replicas 副本数
     * @param rebuild 是否重新创建索引
     * @return
     */
    boolean createIndex(int shards, int replicas, boolean rebuild);

    /**
     * 构建索引
     */
    void buildIndex();

    /**
     * 保存索引 事件驱动  异步操作
     * @param event 索引保存事件
     */
    void saveIndex(ApplicationEvent event);

    /**
     * 删除索引 事件驱动  异步操作
     * @param event 索引删除事件
     */
    void deleteIndex(ApplicationEvent event);

}
