package com.biemo.cloud.bbs.modular.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.biemo.cloud.bbs.modular.mapper.BOperateLogMapper;
import com.biemo.cloud.bbs.modular.domain.BOperateLog;
import com.biemo.cloud.bbs.modular.service.IBOperateLogService;

/**
 * Service业务层处理
 *
 * @author makesoft
 * @date 2021-08-11
 */
@Service
public class BOperateLogServiceImpl extends ServiceImpl<BOperateLogMapper,BOperateLog> implements IBOperateLogService
{

}
