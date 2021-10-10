package com.biemo.cloud.bbs.modular.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biemo.cloud.bbs.api.vo.BArticleVo;
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
import com.biemo.cloud.bbs.modular.service.IBArticleService;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service业务层处理
 *
 * @author makesoft
 * @date 2021-08-11
 */
@Service
public class BArticleServiceImpl extends ServiceImpl<BArticleMapper,BArticle> implements IBArticleService
{

    @Autowired
    RedisCache redisCache;

    @Autowired
    BTagMapper bTagMapper;

    @Autowired
    BArticleTagMapper bArticleTagMapper;

    @Autowired
    BUserMapper bUserMapper;

    @Autowired
    BFavoriteMapper bFavoriteMapper;

    @Autowired
    BMessageMapper messageMapper;


    @Override
    @Transactional
    public ResponseData create(BArticleVo bArticleVo) throws Exception {
        if(bArticleVo==null){
            return ResponseData.error("参数不能为空！");
        }
        BUser bUser = BiemoLoginContext.me().getCurrentUser();
        if(bUser==null){
            return ResponseData.error("未登录！");
        }
        //验证码验证
        String captchaId = bArticleVo.getCaptchaId();
        String captchaCode = bArticleVo.getCaptchaCode();
        if(StringUtils.isNotEmpty(captchaId)){
            String code = redisCache.getCacheObject(Constants.CAPTCHA_CODE_KEY + captchaId);
            if(code==null||!code.equals(captchaCode)){
                return ResponseData.error("验证码错误！");
            }
        }
        //插入article
        bArticleVo.setUserId(bUser.getId());
        bArticleVo.setCreateTime(System.currentTimeMillis());
        BArticle article = new BArticle();
        BeanUtils.copyProperties(article,bArticleVo);
        article.setContentType("article");
        if(article!=null){
            if(article.getId()==null||article.getId()==0){
                article.setId(IdUtils.fastUUIDLong());
                this.baseMapper.insert(article);
            }else{
                this.baseMapper.updateById(article);
            }
        }
        if(bArticleVo.getId()==null){
            bArticleVo.setId(article.getId());
        }
        //处理标签
        if(StringUtils.isNotEmpty(bArticleVo.getTags())){
            String tags = bArticleVo.getTags();
            String []tagsArray = tags.split(",");
            for(int i=0;i<tagsArray.length;i++){
                String tagStr = tagsArray[i];
                BTag tag = new BTag();
                tag.setName(tagStr);
                BTag tagNew = bTagMapper.selectOne(new QueryWrapper<>(tag));
                if(tagNew==null){
                    tagNew = new BTag();
                    tagNew.setName(tagStr);
                    tagNew.setStatus(0L);
                    tagNew.setCreateTime(System.currentTimeMillis());
                    tagNew.setId(IdUtils.fastUUIDLong());
                    bTagMapper.insert(tagNew);
                }
                BArticleTag bArticleTag = new BArticleTag();
                bArticleTag.setId(IdUtils.fastUUIDLong());
                bArticleTag.setCreateTime(System.currentTimeMillis());
                bArticleTag.setTagId(tagNew.getId());
                bArticleTag.setStatus(0L);
                bArticleTag.setArticleId(bArticleVo.getId());
                bArticleTagMapper.insert(bArticleTag);
            }
        }
        return ResponseData.success(article);
    }

    @Override
    public JSONObject getDetailById(Long id,String type) {
        BArticle article = this.baseMapper.selectById(id);
        if(article==null){
            article = new BArticle();
            return  (JSONObject) JSONObject.toJSON(article);
        }
        if(StringUtils.isNotBlank(article.getContent())){
            article.setContent(MarkDown2HtmlUtils.markdownToHtml(article.getContent()));
        }
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(article);
        BUser bUser = bUserMapper.selectById(article.getUserId());
        jsonObject.put("user",bUser);
        List<BTag> tagList = new ArrayList<>();
        BArticleTag bArticleTag = new BArticleTag();
        bArticleTag.setArticleId(id);
        List<BArticleTag> bArticleTagList = bArticleTagMapper.selectList(new QueryWrapper<>(bArticleTag));
        List<Long> tagIds = bArticleTagList!=null&&bArticleTagList.size()>0?bArticleTagList.stream().map(x->x.getTagId()).collect(Collectors.toList()) : null;
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

    @Override
    public ResponseData favorite(Long id) {
        BUser bUser = BiemoLoginContext.me().getCurrentUser();
        if(bUser==null){
            return ResponseData.error("请先登录！");
        }
        BFavorite bFavorite = new BFavorite();
        bFavorite.setId(IdUtils.fastUUIDLong());
        bFavorite.setUserId(bUser.getId());
        bFavorite.setCreateTime(System.currentTimeMillis());
        bFavorite.setEntityId(id);
        bFavorite.setEntityType("article");
        bFavoriteMapper.insert(bFavorite);


        BArticle bArticle = this.baseMapper.selectById(id);
        //发送消息给对方
        BMessage message = new BMessage();
        message.setUserId(bArticle.getUserId());
        message.setFromId(bUser.getId());
        message.setTitle("收藏了你的文章");
        message.setContent(bUser.getNickname()+" 收藏了你的文章："+bArticle.getTitle());
        message.setType(3L);
        message.setStatus(0L);
        message.setCreateTime(System.currentTimeMillis());
        Map extraData = new HashMap<String,Object>(){{put("favoriteUserId",bUser.getId());put("articleId",bArticle.getId());}};
        message.setExtraData(JSONObject.toJSONString(extraData));
        message.setQuoteContent(bArticle.getTitle());
        messageMapper.insert(message);
        return ResponseData.success("收藏成功！");
    }

    @Override
    public ResponseData nearlyList(Long id) {
        BArticle bArticle = this.baseMapper.selectById(id);
        if(bArticle!=null){
            QueryWrapper<BArticle> queryWrapper = new QueryWrapper<>();
            queryWrapper.lt("create_time",bArticle.getCreateTime());
            queryWrapper.orderByDesc("create_time");
            queryWrapper.last("limit 10");
            List<BArticle> bArticles = this.baseMapper.selectList(queryWrapper);
            List<JSONObject> jsonObjectList = new ArrayList<>();
            if(bArticles!=null&&bArticles.size()>0){
                bArticles.forEach(article->{
                    JSONObject jsonObject = (JSONObject) JSONObject.toJSON(article);
                    jsonObject.put("user",bUserMapper.selectById(article.getUserId()));
                    //查找标签
                    BArticleTag bArticleTag = new BArticleTag();
                    bArticleTag.setArticleId(article.getId());
                    List<BArticleTag> bArticleTagList1 = bArticleTagMapper.selectList(new QueryWrapper<>(bArticleTag));
                    List<Long> tagIds = bArticleTagList1.stream().map(t->t.getTagId()).collect(Collectors.toList());
                    List<BTag> bTagList = tagIds!=null&&tagIds.size()>0?bTagMapper.selectBatchIds(tagIds):null;
                    jsonObject.put("tags",bTagList);
                    jsonObjectList.add(jsonObject);
                });
                return ResponseData.success(jsonObjectList);
            }
        }
        return ResponseData.success();
    }
}
