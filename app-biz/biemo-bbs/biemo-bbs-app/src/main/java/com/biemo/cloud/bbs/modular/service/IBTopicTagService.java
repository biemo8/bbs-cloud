package com.biemo.cloud.bbs.modular.service;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.biemo.cloud.bbs.modular.domain.BTopicTag;
import com.biemo.cloud.bbs.modular.domain.response.BTopicResponse;

/**
 * Service接口
 *
 * @author makesoft
 * @date 2021-08-11
 */
public interface IBTopicTagService extends IService<BTopicTag>
{

    Page<BTopicResponse> customPageList(Page page, Long tagId);
}
