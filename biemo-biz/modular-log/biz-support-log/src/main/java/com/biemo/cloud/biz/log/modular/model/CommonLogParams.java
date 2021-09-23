package com.biemo.cloud.biz.log.modular.model;

import lombok.Data;

/**
 * 常规日志列表查询条件
 *
 *
 * @date 2018-07-31-下午5:58
 */
@Data
public class CommonLogParams {

    /**
     * 第几页
     */
    private Integer pageNo;

    /**
     * 每页大小
     */
    private Integer pageSize;

    /**
     * 自增主键where的条件值
     */
    private Long gtValue;

}
