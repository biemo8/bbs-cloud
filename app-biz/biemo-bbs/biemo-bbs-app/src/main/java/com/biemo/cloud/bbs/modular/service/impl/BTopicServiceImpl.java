package com.biemo.cloud.bbs.modular.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biemo.cloud.bbs.api.vo.BTopicVo;
import com.biemo.cloud.bbs.modular.context.BiemoLoginContext;
import com.biemo.cloud.bbs.modular.domain.*;
import com.biemo.cloud.bbs.modular.mapper.*;
import com.biemo.cloud.bbs.utils.MarkDown2HtmlUtils;
import com.biemo.cloud.core.constant.Constants;
import com.biemo.cloud.core.util.StringUtils;
import com.biemo.cloud.core.util.redis.RedisCache;
import com.biemo.cloud.core.util.uuid.IdUtils;
import com.biemo.cloud.kernel.model.response.ResponseData;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.biemo.cloud.bbs.modular.service.IBTopicService;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service业务层处理
 *
 * @author makesoft
 * @date 2021-08-11
 */
@Service
public class BTopicServiceImpl extends ServiceImpl<BTopicMapper, BTopic> implements IBTopicService
{
    @Autowired
    BUserLikeMapper bUserLikeMapper;

    @Autowired
    BFavoriteMapper bFavoriteMapper;

    @Autowired
    BTagMapper bTagMapper;

    @Autowired
    BTopicTagMapper bTopicTagMapper;

    @Autowired
    RedisCache redisCache;

    @Autowired
    BMessageMapper messageMapper;

    @Autowired
    BTopicMapper topicMapper;

    @Autowired
    BTopicNodeMapper topicNodeMapper;

    @Autowired
    BUserMapper userMapper;

    @Override
    @Transactional
    public ResponseData like(Long topicId) {
        BUser bUser = BiemoLoginContext.me().getCurrentUser();
        if(bUser==null){
            return ResponseData.error("请先登录！");
        }
        BTopic bTopic = this.baseMapper.selectById(topicId);
        if(bTopic!=null){
            bTopic.setLikeCount(bTopic.getLikeCount()+1);
            this.baseMapper.updateById(bTopic);
        }
        BUserLike bUserLike = new BUserLike();
        bUserLike.setId(IdUtils.fastUUIDLong());
        bUserLike.setUserId(bUser.getId());
        bUserLike.setCreateTime(System.currentTimeMillis());
        bUserLike.setEntityId(topicId);
        bUserLike.setEntityType("topic");
        bUserLikeMapper.insert(bUserLike);

        //发送消息给对方
        BMessage message = new BMessage();
        message.setUserId(bTopic.getUserId());
        message.setFromId(bUser.getId());
        message.setTitle("点赞了你的话题");
        message.setContent(bUser.getNickname()+" 点赞了你的话题："+bTopic.getTitle());
        message.setType(2L);
        message.setStatus(0L);
        message.setCreateTime(System.currentTimeMillis());
        Map extraData = new HashMap<String,Object>(){{put("likeUserId",bUser.getId());put("topicId",topicId);}};
        message.setExtraData(JSONObject.toJSONString(extraData));
        message.setQuoteContent(bTopic.getTitle());
        messageMapper.insert(message);
        return ResponseData.success("点赞成功！");
    }

    @Override
    @Transactional
    public ResponseData favorite(Long topicId) {
        BUser bUser = BiemoLoginContext.me().getCurrentUser();
        if(bUser==null){
            return ResponseData.error("请先登录！");
        }
        BFavorite bFavorite = new BFavorite();
        bFavorite.setId(IdUtils.fastUUIDLong());
        bFavorite.setUserId(bUser.getId());
        bFavorite.setCreateTime(System.currentTimeMillis());
        bFavorite.setEntityId(topicId);
        bFavorite.setEntityType("topic");
        bFavoriteMapper.insert(bFavorite);

        BTopic bTopic = this.baseMapper.selectById(topicId);
        //发送消息给对方
        BMessage message = new BMessage();
        message.setUserId(bTopic.getUserId());
        message.setFromId(bUser.getId());
        message.setTitle("收藏了你的话题");
        message.setContent(bUser.getNickname()+" 收藏了你的话题："+bTopic.getTitle());
        message.setType(3L);
        message.setStatus(0L);
        message.setCreateTime(System.currentTimeMillis());
        Map extraData = new HashMap<String,Object>(){{put("favoriteUserId",bUser.getId());put("topicId",topicId);}};
        message.setExtraData(JSONObject.toJSONString(extraData));
        message.setQuoteContent(bTopic.getTitle());
        messageMapper.insert(message);
        return ResponseData.success("收藏成功！");
    }

    @Override
    @Transactional
    public ResponseData create(BTopicVo bTopicvo) throws Exception {
        if(bTopicvo==null){
            return ResponseData.error("参数不能为空！");
        }
        BUser bUser = BiemoLoginContext.me().getCurrentUser();
        if(bUser==null){
            return ResponseData.error("未登录！");
        }
        //验证码验证
        String captchaId = bTopicvo.getCaptchaId();
        String captchaCode = bTopicvo.getCaptchaCode();
        if(StringUtils.isNotEmpty(captchaId)){
            String code = redisCache.getCacheObject(Constants.CAPTCHA_CODE_KEY + captchaId);
            if(code==null||!code.equals(captchaCode)){
                return ResponseData.error("验证码错误！");
            }
        }
        //插入topic
        bTopicvo.setUserId(bUser.getId());
        bTopicvo.setCreateTime(System.currentTimeMillis());
        BTopic bTopic = new BTopic();
        BeanUtils.copyProperties(bTopic,bTopicvo);
        if(bTopic!=null&&bTopic.getId()!=null&&bTopic.getId()!=0){
            this.baseMapper.updateById(bTopic);
        }else if(bTopic!=null&&(bTopic.getId()==null||bTopic.getId()==0)){
            bTopic.setId(IdUtils.fastUUIDLong());
            bTopicvo.setId(bTopic.getId());
            this.baseMapper.insert(bTopic);
        }
        //处理标签
        if(StringUtils.isNotEmpty(bTopicvo.getTags())){
            String tags = bTopicvo.getTags();
            String []tagsArray = tags.split(",");
            for(int i=0;i<tagsArray.length;i++){
                String tagStr = tagsArray[i];
                BTag tag = new BTag();
                tag.setName(tagStr);
                BTag tagNew = bTagMapper.selectOne(new QueryWrapper<>(tag));
                if(tagNew==null){
                    tagNew = new BTag();
                    tagNew.setName(tagStr);
                    tagNew.setId(IdUtils.fastUUIDLong());
                    tagNew.setStatus(0L);
                    tagNew.setCreateTime(System.currentTimeMillis());
                    bTagMapper.insert(tagNew);
                }
                BTopicTag topicTag = new BTopicTag();
                topicTag.setId(IdUtils.fastUUIDLong());
                topicTag.setCreateTime(System.currentTimeMillis());
                topicTag.setTopicId(bTopicvo.getId());
                topicTag.setTagId(tagNew.getId());
                topicTag.setStatus(0L);
                topicTag.setLastCommentTime(0L);
                topicTag.setLastCommentUserId(0L);
                bTopicTagMapper.insert(topicTag);
            }
        }
        return ResponseData.success(bTopic);
    }

    @Override
    public JSONObject getDetailById(Long topicId, String type) {
        BTopic bTopic = topicMapper.selectById(topicId);
        bTopic.setViewCount(bTopic.getViewCount()+1);
        topicMapper.updateById(bTopic);
        BTopicNode node = topicNodeMapper.selectById(bTopic.getNodeId());
        if(!"edit".equals(type)&&bTopic.getType()==0){
            bTopic.setContent(MarkDown2HtmlUtils.markdownToHtml(bTopic.getContent()));
        }
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(bTopic);
        jsonObject.put("node",node);
        if(bTopic.getImageList()!=null){
            jsonObject.put("imageList",JSONObject.parseArray(bTopic.getImageList()));
        }
        BUser user = userMapper.selectById(bTopic.getUserId());
        jsonObject.put("user",user);
        List<BTag> tagList = new ArrayList<>();
        BTopicTag bTopicTag = new BTopicTag();
        bTopicTag.setTopicId(topicId);
        List<BTopicTag> bTopicTagList = bTopicTagMapper.selectList(new QueryWrapper<>(bTopicTag));
        List<Long> tagIds = bTopicTagList!=null&&bTopicTagList.size()>0?bTopicTagList.stream().map(x->x.getTagId()).collect(Collectors.toList()) : null;
        if(tagIds!=null&&tagIds.size()>0){
            tagList = bTagMapper.selectBatchIds(tagIds);
            if("edit".equals(type)){
                List<String> tags= tagList.stream().map(t->t.getName()).collect(Collectors.toList());
                jsonObject.put("tags",tags);
            }else{
                jsonObject.put("tags",tagList);
            }
        }
        return jsonObject;
    }
}
