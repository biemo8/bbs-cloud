package com.biemo.cloud.biz.log.modular.service;

import com.biemo.cloud.biz.log.api.entity.TraceLog;
import com.biemo.cloud.biz.log.modular.factory.TraceLogFactory;
import com.biemo.cloud.biz.log.modular.mapper.TraceLogMapper;
import com.biemo.cloud.biz.log.modular.model.TraceLogCondition;
import com.biemo.cloud.biz.log.modular.model.TraceLogParams;
import com.biemo.cloud.kernel.model.page.PageResult;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 *
 * @since 2018-07-30
 */
@Service
public class TraceLogService extends ServiceImpl<TraceLogMapper, TraceLog> {

    /**
     * 获取调用链日志列表（没有查询条件的）
     *
     *
     * @Date 2018/8/1 下午4:10
     */
    public PageResult<TraceLog> getTraceLogList(TraceLogParams traceLogParams) {
        Long traceLogCount = this.baseMapper.getTraceLogCount();

        if (traceLogCount == null) {
            traceLogCount = 0L;
        }

        if (traceLogParams.getGtValue() == null) {
            traceLogParams.setGtValue(traceLogCount);
        }

        List<TraceLog> commonLogList = this.baseMapper.getTraceLogList(traceLogParams);
        return TraceLogFactory.getResponse(commonLogList, traceLogCount, traceLogParams);
    }

    /**
     * 获取调用链日志列表（带查询条件的）
     *
     *
     * @Date 2018/8/1 下午4:10
     */
    public PageResult<TraceLog> getTraceLogListByCondition(TraceLogCondition traceLogCondition) {
        List<TraceLog> commonLogList = this.baseMapper.getTraceLogListByCondition(traceLogCondition);
        return TraceLogFactory.getResponseCondition(commonLogList, traceLogCondition);
    }
}
