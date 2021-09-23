package com.biemo.cloud.bbs.modular.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.biemo.cloud.bbs.api.vo.BTopicVo;
import com.biemo.cloud.bbs.modular.domain.BTopic;
import com.biemo.cloud.kernel.model.response.ResponseData;

/**
 * Service接口
 *
 * @author makesoft
 * @date 2021-08-11
 */
public interface IBTopicService extends IService<BTopic>
{

    ResponseData like(Long topicId);

    ResponseData favorite(Long topicId);

    ResponseData create(BTopicVo bTopicvo) throws Exception;
}
