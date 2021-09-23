package com.biemo.cloud.bbs.modular.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.biemo.cloud.bbs.modular.mapper.BCheckInMapper;
import com.biemo.cloud.bbs.modular.domain.BCheckIn;
import com.biemo.cloud.bbs.modular.service.IBCheckInService;

/**
 * Service业务层处理
 *
 * @author makesoft
 * @date 2021-08-11
 */
@Service
public class BCheckInServiceImpl extends ServiceImpl<BCheckInMapper,BCheckIn> implements IBCheckInService
{

}
