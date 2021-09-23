package com.biemo.cloud.biz.log.modular.factory;

import cn.hutool.core.date.DateUtil;
import com.biemo.cloud.biz.log.api.entity.TraceLog;
import com.biemo.cloud.biz.log.modular.model.TraceLogCondition;
import com.biemo.cloud.biz.log.modular.model.TraceLogParams;
import com.biemo.cloud.core.request.RequestData;
import com.biemo.cloud.core.util.ToolUtil;
import com.biemo.cloud.kernel.model.page.PageResult;

import java.util.List;

/**
 * 查询调用链日志的参数构造器
 *
 *
 * @date 2018-08-01-下午2:47
 */
public class TraceLogFactory {

    /**
     * 获取请求参数
     */
    public static Object getRequest(RequestData requestData) {

        String traceId = requestData.getString("traceId");
        String appCode = requestData.getString("appCode");
        String rpcPhase = requestData.getString("rpcPhase");
        String beginTime = requestData.getString("beginTime");
        String endTime = requestData.getString("endTime");

        if (ToolUtil.isAllEmpty(traceId, appCode, rpcPhase, beginTime, endTime)) {

            TraceLogParams traceLogParams = new TraceLogParams();

            Integer pageNo = requestData.getInteger("pageNo");
            Integer pageSize = requestData.getInteger("pageSize");
            Long gtValue = requestData.getLong("gtValue");

            if (pageNo != null) {
                traceLogParams.setPageNo(pageNo);
            } else {
                traceLogParams.setPageNo(1);
            }
            if (pageSize != null) {
                traceLogParams.setPageSize(pageSize);
            } else {
                traceLogParams.setPageSize(100);
            }
            if (gtValue != null) {
                traceLogParams.setGtValue(gtValue);
            }

            return traceLogParams;

        } else {
            TraceLogCondition traceLogCondition = requestData.parse(TraceLogCondition.class);

            if (traceLogCondition.getPageNo() == null) {
                traceLogCondition.setPageNo(1);
            }
            if (traceLogCondition.getPageSize() == null) {
                traceLogCondition.setPageSize(100);
            }

            if (beginTime != null) {
                traceLogCondition.setBeginTime(DateUtil.parse(beginTime).getTime());
            }
            if (endTime != null) {
                traceLogCondition.setEndTime(DateUtil.parse(endTime).getTime());
            }

            traceLogCondition.setLimitOffset((long) ((traceLogCondition.getPageNo() - 1) * traceLogCondition.getPageSize()));

            return traceLogCondition;
        }
    }

    /**
     * 创建分页的响应结果
     */
    public static PageResult<TraceLog> getResponse(List<TraceLog> traceLogs, Long traceLogCount, TraceLogParams traceLogParams) {
        PageResult<TraceLog> pageResult = new PageResult<>();
        pageResult.setRows(traceLogs);
        pageResult.setTotalRows(traceLogCount);
        pageResult.setPage(traceLogParams.getPageNo());
        pageResult.setPageSize(traceLogParams.getPageSize());

        long a = traceLogCount % traceLogParams.getPageSize() == 0 ? 0 : 1;
        pageResult.setTotalPage((int) (a + traceLogCount / traceLogParams.getPageSize()));

        return pageResult;
    }

    /**
     * 创建分页的响应结果(条件查询的)
     */
    public static PageResult<TraceLog> getResponseCondition(List<TraceLog> traceLogs, TraceLogCondition traceLogCondition) {
        PageResult<TraceLog> pageResult = new PageResult<>();
        pageResult.setRows(traceLogs);
        pageResult.setPage(traceLogCondition.getPageNo());
        pageResult.setPageSize(traceLogCondition.getPageSize());

        return pageResult;
    }
}
