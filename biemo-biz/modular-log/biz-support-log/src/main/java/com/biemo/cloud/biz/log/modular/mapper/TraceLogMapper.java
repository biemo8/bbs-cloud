package com.biemo.cloud.biz.log.modular.mapper;

import com.biemo.cloud.biz.log.api.entity.TraceLog;
import com.biemo.cloud.biz.log.modular.model.TraceLogCondition;
import com.biemo.cloud.biz.log.modular.model.TraceLogParams;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 *
 * @since 2018-07-30
 */
public interface TraceLogMapper extends BaseMapper<TraceLog> {

    /**
     * 获取traceLog的总长度
     *
     *
     * @Date 2018/8/1 下午1:36
     */
    Long getTraceLogCount();

    /**
     * 获取调用链日志列表
     *
     *
     * @Date 2018/7/31 下午5:51
     */
    List<TraceLog> getTraceLogList(TraceLogParams traceLogParams);

    /**
     * 获取调用链日志列表(条件查询)
     *
     *
     * @Date 2018/7/31 下午5:51
     */
    List<TraceLog> getTraceLogListByCondition(TraceLogCondition traceLogCondition);

    /**
     * 获取调用链日志列表(条件查询和分页)
     *
     *
     * @Date 2018/7/31 下午5:51
     */
    List<TraceLog> getTraceLogListPageByCondition(Page<TraceLog> page, TraceLogCondition traceLogCondition);
}
