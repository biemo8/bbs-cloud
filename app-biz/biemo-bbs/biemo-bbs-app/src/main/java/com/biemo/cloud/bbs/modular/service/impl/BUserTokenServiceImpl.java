package com.biemo.cloud.bbs.modular.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.biemo.cloud.bbs.modular.mapper.BUserTokenMapper;
import com.biemo.cloud.bbs.modular.domain.BUserToken;
import com.biemo.cloud.bbs.modular.service.IBUserTokenService;

/**
 * Service业务层处理
 *
 * @author makesoft
 * @date 2021-08-11
 */
@Service
public class BUserTokenServiceImpl extends ServiceImpl<BUserTokenMapper,BUserToken> implements IBUserTokenService
{

}
