package com.biemo.cloud.bbs.modular.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.biemo.cloud.bbs.modular.mapper.BMessageMapper;
import com.biemo.cloud.bbs.modular.domain.BMessage;
import com.biemo.cloud.bbs.modular.service.IBMessageService;

/**
 * Service业务层处理
 *
 * @author makesoft
 * @date 2021-08-11
 */
@Service
public class BMessageServiceImpl extends ServiceImpl<BMessageMapper,BMessage> implements IBMessageService
{

}
