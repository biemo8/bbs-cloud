package com.biemo.cloud.biz.log.modular.factory;

import cn.hutool.core.date.DateUtil;
import com.biemo.cloud.biz.log.api.entity.CommonLog;
import com.biemo.cloud.biz.log.modular.model.CommonLogCondition;
import com.biemo.cloud.biz.log.modular.model.CommonLogParams;
import com.biemo.cloud.core.request.RequestData;
import com.biemo.cloud.core.util.ToolUtil;
import com.biemo.cloud.kernel.model.page.PageResult;

import java.util.List;

/**
 * 查询普通日志的参数构造器
 *
 *
 * @date 2018-08-01-下午2:47
 */
public class CommonLogFactory {

    /**
     * 判断是否有条件查询，如果有就构造条件查询实体，没有就是单纯分页查询
     */
    public static Object getRequest(RequestData requestData) {

        String requestNo = requestData.getString("requestNo");
        String appCode = requestData.getString("appCode");
        String logLevel = requestData.getString("logLevel");
        String beginTime = requestData.getString("beginTime");
        String endTime = requestData.getString("endTime");

        if (ToolUtil.isAllEmpty(requestNo, appCode, logLevel, beginTime, endTime)) {

            CommonLogParams commonLogParams = new CommonLogParams();

            Integer pageNo = requestData.getInteger("pageNo");
            Integer pageSize = requestData.getInteger("pageSize");
            Long gtValue = requestData.getLong("gtValue");

            if (pageNo != null) {
                commonLogParams.setPageNo(pageNo);
            } else {
                commonLogParams.setPageNo(1);
            }
            if (pageSize != null) {
                commonLogParams.setPageSize(pageSize);
            } else {
                commonLogParams.setPageSize(100);
            }
            if (gtValue != null) {
                commonLogParams.setGtValue(gtValue);
            }
            return commonLogParams;

        } else {
            CommonLogCondition commonLogCondition = requestData.parse(CommonLogCondition.class);

            if (commonLogCondition.getPageNo() == null) {
                commonLogCondition.setPageNo(1);
            }
            if (commonLogCondition.getPageSize() == null) {
                commonLogCondition.setPageSize(100);
            }

            if (beginTime != null) {
                commonLogCondition.setBeginTime(DateUtil.parse(beginTime).getTime());
            }
            if (endTime != null) {
                commonLogCondition.setEndTime(DateUtil.parse(endTime).getTime());
            }

            commonLogCondition.setLimitOffset((long) ((commonLogCondition.getPageNo() - 1) * commonLogCondition.getPageSize()));

            return commonLogCondition;
        }
    }

    /**
     * 创建分页的响应结果
     */
    public static PageResult<CommonLog> getResponse(List<CommonLog> result, Long commonLogCount, CommonLogParams commonLogParams) {
        PageResult<CommonLog> pageResult = new PageResult<>();
        pageResult.setRows(result);
        pageResult.setTotalRows(commonLogCount);
        pageResult.setPage(commonLogParams.getPageNo());
        pageResult.setPageSize(commonLogParams.getPageSize());

        long a = commonLogCount % commonLogParams.getPageSize() == 0 ? 0 : 1;
        pageResult.setTotalPage((int) (a + commonLogCount / commonLogParams.getPageSize()));

        return pageResult;
    }

    /**
     * 创建分页的响应结果(条件查询的)
     */
    public static PageResult<CommonLog> getResponseCondition(List<CommonLog> result, CommonLogCondition commonLogCondition) {
        PageResult<CommonLog> pageResult = new PageResult<>();
        pageResult.setRows(result);
        pageResult.setPage(commonLogCondition.getPageNo());
        pageResult.setPageSize(commonLogCondition.getPageSize());

        return pageResult;
    }
}
