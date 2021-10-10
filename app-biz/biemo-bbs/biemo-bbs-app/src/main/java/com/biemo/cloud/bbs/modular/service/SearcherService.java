package com.biemo.cloud.bbs.modular.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biemo.cloud.bbs.core.search.IndexQueryContext;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.search.SearchHit;

import java.util.List;

/**
 * ES 查询服务
 * @param <T>
 */
public interface SearcherService<T> {
    // ES 默认最大返回 10000 与 scroll查询有关
    int MAX_RESULT_WINDOW = 10000;

    // ES 滚动查询 有效时间 与 scroll查询有关
    TimeValue SCROLL_TIME =  TimeValue.timeValueMinutes(1);

    /**
     * 根据查询上下文返回T的PageHelper 分页对象
     * @param context
     * @return
     */
    Page<T> search(IndexQueryContext context);

    /**
     * 根据查询上下文返回原生SearchHit
     * @param context
     * @return
     */
    List<SearchHit> searchHits(IndexQueryContext context);
}
