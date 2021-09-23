package com.biemo.cloud.bbs.modular.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.biemo.cloud.bbs.modular.mapper.BEmailCodeMapper;
import com.biemo.cloud.bbs.modular.domain.BEmailCode;
import com.biemo.cloud.bbs.modular.service.IBEmailCodeService;

/**
 * Service业务层处理
 *
 * @author makesoft
 * @date 2021-08-11
 */
@Service
public class BEmailCodeServiceImpl extends ServiceImpl<BEmailCodeMapper,BEmailCode> implements IBEmailCodeService
{

}
