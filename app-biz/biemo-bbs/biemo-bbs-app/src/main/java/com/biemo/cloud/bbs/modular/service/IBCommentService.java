package com.biemo.cloud.bbs.modular.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.biemo.cloud.bbs.modular.domain.BComment;
import com.biemo.cloud.kernel.model.response.ResponseData;

/**
 * Service接口
 *
 * @author makesoft
 * @date 2021-08-11
 */
public interface IBCommentService extends IService<BComment>
{

    ResponseData create(BComment bComment);
}
