package com.biemo.cloud.biz.log.modular.model;

import lombok.Data;

/**
 * 常规日志列表查询条件
 *
 *
 * @date 2018-07-31-下午5:58
 */
@Data
public class CommonLogCondition {

    /**
     * 第几页
     */
    private Integer pageNo;

    /**
     * 每页大小
     */
    private Integer pageSize;

    /**
     * limit的偏移量
     */
    private Long limitOffset;

    /**
     * 日志请求号
     */
    private String requestNo;

    /**
     * 应用编码
     */
    private String appCode;

    /**
     * 日志级别
     */
    private String logLevel;

    /**
     * 查询开始日期
     */
    private Long beginTime;

    /**
     * 查询结束日期
     */
    private Long endTime;
}
