package com.biemo.cloud.biz.log.modular.service;

import com.biemo.cloud.biz.log.api.entity.CommonLog;
import com.biemo.cloud.biz.log.modular.factory.CommonLogFactory;
import com.biemo.cloud.biz.log.modular.mapper.CommonLogMapper;
import com.biemo.cloud.biz.log.modular.model.CommonLogCondition;
import com.biemo.cloud.biz.log.modular.model.CommonLogParams;
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
public class CommonLogService extends ServiceImpl<CommonLogMapper, CommonLog> {

    /**
     * 获取普通日志列表（没有查询条件的）
     *
     *
     * @Date 2018/8/1 下午4:10
     */
    public PageResult<CommonLog> getCommonLogList(CommonLogParams commonLogParams) {
        Long commonLogCount = this.baseMapper.getCommonLogCount();

        if (commonLogParams.getGtValue() == null) {
            commonLogParams.setGtValue(commonLogCount);
        }

        List<CommonLog> commonLogList = this.baseMapper.getCommonLogList(commonLogParams);
        return CommonLogFactory.getResponse(commonLogList, commonLogCount, commonLogParams);
    }

    /**
     * 获取普通日志列表（带查询条件的）
     *
     *
     * @Date 2018/8/1 下午4:10
     */
    public PageResult<CommonLog> getCommonLogListByCondition(CommonLogCondition commonLogCondition) {
        List<CommonLog> commonLogList = this.baseMapper.getCommonLogListByCondition(commonLogCondition);
        return CommonLogFactory.getResponseCondition(commonLogList, commonLogCondition);
    }

}
