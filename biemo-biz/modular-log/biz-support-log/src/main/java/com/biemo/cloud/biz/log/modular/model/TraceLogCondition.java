package com.biemo.cloud.biz.log.modular.model;

import lombok.Data;

/**
 * 调用链日志列表查询条件
 *
 *
 * @date 2018-07-31-下午5:58
 */
@Data
public class TraceLogCondition {

    /**
     * 第几页
     */
    private Integer pageNo;

    /**
     * 每页大小
     */
    private Integer pageSize;

    /**
     * 分页的偏移量
     */
    private Long limitOffset;

    /**
     * 日志请求号
     */
    private String traceId;

    /**
     * 应用编码
     */
    private String appCode;

    /**
     * 远程调用类型
     */
    private String rpcPhase;

    /**
     * 查询开始日期
     */
    private Long beginTime;

    /**
     * 查询结束日期
     */
    private Long endTime;
}
