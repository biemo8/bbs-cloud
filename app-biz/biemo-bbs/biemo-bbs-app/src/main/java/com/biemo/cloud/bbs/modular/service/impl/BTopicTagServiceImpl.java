package com.biemo.cloud.bbs.modular.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biemo.cloud.bbs.modular.domain.response.BTopicResponse;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.biemo.cloud.bbs.modular.mapper.BTopicTagMapper;
import com.biemo.cloud.bbs.modular.domain.BTopicTag;
import com.biemo.cloud.bbs.modular.service.IBTopicTagService;

/**
 * Service业务层处理
 *
 * @author makesoft
 * @date 2021-08-11
 */
@Service
public class BTopicTagServiceImpl extends ServiceImpl<BTopicTagMapper,BTopicTag> implements IBTopicTagService
{
    @Autowired
    private BTopicTagMapper topicTagMapper;

    @Override
    public Page<BTopicResponse> customPageList(Page page, Long tagId) {
        return topicTagMapper.customPageList(page,tagId);
    }
}
