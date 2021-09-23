package com.biemo.cloud.biz.log.modular.mapper;


import com.biemo.cloud.biz.log.api.entity.CommonLog;
import com.biemo.cloud.biz.log.modular.model.CommonLogCondition;
import com.biemo.cloud.biz.log.modular.model.CommonLogParams;
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
public interface CommonLogMapper extends BaseMapper<CommonLog> {

    /**
     * 获取常规日志总数量
     *
     *
     * @Date 2018/7/31 下午5:51
     */
    Long getCommonLogCount();

    /**
     * 获取常规日志列表
     *
     *
     * @Date 2018/7/31 下午5:51
     */
    List<CommonLog> getCommonLogList(CommonLogParams commonLogParams);

    /**
     * 获取常规日志列表(条件查询)
     *
     *
     * @Date 2018/7/31 下午5:51
     */
    List<CommonLog> getCommonLogListByCondition(CommonLogCondition commonLogCondition);

    /**
     * 获取常规日志列表(条件查询和分页)
     *
     *
     * @Date 2018/7/31 下午5:51
     */
    List<CommonLog> getCommonLogListPageByCondition(Page<CommonLog> page, CommonLogCondition commonLogCondition);

}
