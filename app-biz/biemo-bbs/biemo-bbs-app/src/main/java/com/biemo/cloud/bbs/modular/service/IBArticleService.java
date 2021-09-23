package com.biemo.cloud.bbs.modular.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.biemo.cloud.bbs.api.vo.BArticleVo;
import com.biemo.cloud.bbs.modular.domain.BArticle;
import com.biemo.cloud.kernel.model.response.ResponseData;

import java.lang.reflect.InvocationTargetException;

/**
 * Service接口
 *
 * @author makesoft
 * @date 2021-08-11
 */
public interface IBArticleService extends IService<BArticle>
{

    ResponseData create(BArticleVo bAritleVo) throws InvocationTargetException, IllegalAccessException, Exception;

    JSONObject getDetailById(Long id,String type);

    ResponseData favorite(Long id);

    ResponseData nearlyList(Long id);
}
