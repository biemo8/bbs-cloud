package com.biemo.cloud.bbs.modular.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biemo.cloud.bbs.modular.annotation.RequestLimit;
import com.biemo.cloud.bbs.modular.domain.BTopic;
import com.biemo.cloud.bbs.modular.mapper.BTopicMapper;
import com.biemo.cloud.bbs.modular.mapper.BUserMapper;
import com.biemo.cloud.kernel.model.response.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.biemo.cloud.bbs.modular.mapper.BCommentMapper;
import com.biemo.cloud.bbs.modular.domain.BComment;
import com.biemo.cloud.bbs.modular.service.IBCommentService;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service业务层处理
 *
 * @author makesoft
 * @date 2021-08-11
 */
@Service
public class BCommentServiceImpl extends ServiceImpl<BCommentMapper,BComment> implements IBCommentService
{
    @Autowired
    BCommentMapper bCommentMapper;

    @Autowired
    BTopicMapper  bTopicMapper;

    @Autowired
    BUserMapper bUserMapper;



    @Override
    @Transactional
    @RequestLimit(count = 1,time = 60)
    public ResponseData create(BComment bComment) {
        if(bComment.getQuoteId()==null){
            bComment.setQuoteId(0L);
        }
        bCommentMapper.insert(bComment);
        //更新topic状态
        BTopic topic = bTopicMapper.selectById(bComment.getEntityId());
        if(topic!=null){
            topic.setCommentCount(topic.getCommentCount()!=null?topic.getCommentCount()+1:0l);
            topic.setLastCommentTime(System.currentTimeMillis());
            topic.setLastCommentUserId(bComment.getUserId());
            bTopicMapper.updateById(topic);
        }
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(bComment);
        jsonObject.put("user",bUserMapper.selectById(bComment.getUserId()));
        BComment bComment1 = bCommentMapper.selectById(bComment.getQuoteId());
        if(bComment1!=null){
            JSONObject jsonObject1 = (JSONObject) JSONObject.toJSON(bComment1);
            jsonObject1.put("user",bUserMapper.selectById(bComment1.getUserId()));
            jsonObject.put("quote",jsonObject1);
        }
        return ResponseData.success(jsonObject);
    }
}
