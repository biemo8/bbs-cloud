package com.biemo.cloud.bbs.modular.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biemo.cloud.bbs.api.BArticleApi;
import com.biemo.cloud.bbs.api.vo.BArticleVo;
import com.biemo.cloud.bbs.modular.annotation.RequestLimit;
import com.biemo.cloud.bbs.modular.context.BiemoLoginContext;
import com.biemo.cloud.bbs.modular.domain.*;
import com.biemo.cloud.bbs.modular.service.*;
import com.biemo.cloud.core.page.PageFactory;
import com.biemo.cloud.core.util.StringUtils;
import com.biemo.cloud.kernel.model.response.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Controller
 *
 * @author makesoft
 * @date 2021-08-11
 */
@RestController
@RequestMapping("/article")
public class BArticleController extends BaseController implements BArticleApi
{

    @Autowired
    private IBUserService ibUserService;

    @Autowired
    private IBArticleService ibArticleService;

    @Autowired
    private IBUserLikeService ibUserLikeService;

    @Autowired
    private IBArticleTagService ibArticleTagService;

    @Autowired
    private IBTagService ibTagService;


    @Override
    public ResponseData detail(Long id){

        return ResponseData.success(ibArticleService.getDetailById(id,null));
    }
    @Override
    public ResponseData articles(){
        BUser bUser = BiemoLoginContext.me().getCurrentUser();
        JSONObject result = new JSONObject();
        List<JSONObject> jsonObjectList = new ArrayList<>();
        Page page = ibArticleService.page(PageFactory.defaultPage(),new QueryWrapper<BArticle>().orderByDesc("create_time"));
        List<BArticle> bArticles = page.getRecords();
        if(bArticles!=null&&bArticles.size()>0){
            bArticles.forEach(article->{
                JSONObject jsonObject = (JSONObject) JSONObject.toJSON(article);
                BUser user = ibUserService.getById(article.getUserId());
                jsonObject.put("user",user);
                if(bUser!=null){
                    BUserLike bUserLike = new BUserLike();
                    bUserLike.setUserId(bUser.getId());
                    bUserLike.setEntityType("article");
                    bUserLike.setEntityId(article.getId());
                    BUserLike bUserLikeNew = ibUserLikeService.getOne(new QueryWrapper<>(bUserLike));
                    if(bUserLikeNew!=null){
                        jsonObject.put("liked",true);
                    }
                }
                //查找标签
                BArticleTag bArticleTag = new BArticleTag();
                bArticleTag.setArticleId(article.getId());
                List<BArticleTag> bArticleTagList1 = ibArticleTagService.list(new QueryWrapper<>(bArticleTag));
                List<Long> tagIds = bArticleTagList1.stream().map(t->t.getTagId()).collect(Collectors.toList());
                List<BTag> bTagList = tagIds!=null&&tagIds.size()>0?ibTagService.listByIds(tagIds):null;
                jsonObject.put("tags",bTagList);
                jsonObjectList.add(jsonObject);
            });
        }
        boolean hasMore = page.getCurrent()<page.getPages()?true:false;
        result.put("hasMore",hasMore);
        result.put("pageNo",hasMore?page.getCurrent()+1:page.getCurrent());
        result.put("results",jsonObjectList);
        return ResponseData.success(result);
    }

    @Override
    public ResponseData nearly(Long id){
        return ibArticleService.nearlyList(id);
    }

    @Override
    public ResponseData related(Long id){
        return ibArticleService.nearlyList(id);
    }

    //收藏
    @Override
    public ResponseData userFavorite(Long id){

        return ibArticleService.favorite(id);
    }

    @Override
    public ResponseData tagArticles(Long tagId){
        BArticleTag articleTag = new BArticleTag();
        articleTag.setTagId(tagId);
        Page<BArticleTag> bArticleTagPage = ibArticleTagService.page(PageFactory.defaultPage(),new QueryWrapper<>(articleTag).orderByDesc("create_time"));
        List<BArticleTag> bArticleTagList = bArticleTagPage.getRecords();
        JSONObject result = new JSONObject();
        List<JSONObject> jsonObjectList = new ArrayList<>();
        if(bArticleTagList!=null&&bArticleTagList.size()>0){
            List<Long> articleIds = bArticleTagList.stream().map(a->a.getArticleId()).collect(Collectors.toList());
            List<BArticle> articles = ibArticleService.listByIds(articleIds);
            if(articles!=null&&articles.size()>0){
                articles.forEach(article->{
                    JSONObject jsonObject = (JSONObject) JSONObject.toJSON(article);
                    BUser bUser = ibUserService.getById(article.getUserId());
                    jsonObject.put("user",bUser);
                    //查找标签
                    BArticleTag bArticleTag = new BArticleTag();
                    bArticleTag.setArticleId(article.getId());
                    List<BArticleTag> bArticleTagList1 = ibArticleTagService.list(new QueryWrapper<>(bArticleTag));
                    List<Long> tagIds = bArticleTagList1.stream().map(t->t.getTagId()).collect(Collectors.toList());
                    List<BTag> bTagList = tagIds!=null&&tagIds.size()>0?ibTagService.listByIds(tagIds):null;
                    jsonObject.put("tags",bTagList);
                    jsonObjectList.add(jsonObject);
                });
            }
        }
        boolean hasMore = bArticleTagPage.getCurrent()<bArticleTagPage.getPages()?true:false;
        result.put("hasMore",hasMore);
        result.put("pageNo",hasMore?bArticleTagPage.getCurrent()+1:bArticleTagPage.getCurrent());
        result.put("results",jsonObjectList);
        return ResponseData.success(result);
    }

    @Override
    public ResponseData userArticles(BArticleVo bArticle)
    {
        BUser bUser = BiemoLoginContext.me().getCurrentUser();
        JSONObject result = new JSONObject();
        List<JSONObject> jsonObjectList = new ArrayList<>();
        QueryWrapper queryWrapper = new QueryWrapper(bArticle);
        queryWrapper.orderByDesc("create_time");
        Page page = ibArticleService.page(PageFactory.defaultPage(),queryWrapper);
        List<BArticle> bArticles = page.getRecords();
        if(bArticles!=null&&bArticles.size()>0){
            bArticles.forEach(article->{
                JSONObject jsonObject = (JSONObject) JSONObject.toJSON(article);
                BUser user = ibUserService.getById(article.getUserId());
                jsonObject.put("user",user);
                if(bUser!=null){
                    BUserLike bUserLike = new BUserLike();
                    bUserLike.setUserId(bUser.getId());
                    bUserLike.setEntityType("article");
                    bUserLike.setEntityId(article.getId());
                    BUserLike bUserLikeNew = ibUserLikeService.getOne(new QueryWrapper<>(bUserLike));
                    if(bUserLikeNew!=null){
                        jsonObject.put("liked",true);
                    }
                }
                jsonObjectList.add(jsonObject);
            });
        }
        boolean hasMore = page.getCurrent()<page.getPages()?true:false;
        result.put("hasMore",hasMore);
        result.put("pageNo",hasMore?page.getCurrent()+1:page.getCurrent());
        result.put("results",jsonObjectList);
        return ResponseData.success(result);
    }

    @Override
    @RequestLimit(count = 1,time = 60)
    @Transactional
    public ResponseData articleCreate(Long id,String captchaId,String captchaCode ,String title,String content,String tags) throws Exception {
        BArticleVo bAritleVo = new BArticleVo();
        bAritleVo.setCaptchaId(captchaId);
        bAritleVo.setCaptchaCode(captchaCode);
        bAritleVo.setTitle(title);
        bAritleVo.setContent(content);
        bAritleVo.setTags(tags);
        bAritleVo.setId(id);
        if(id!=null&&StringUtils.isNotEmpty(tags)){
            BArticleTag bArticleTag = new BArticleTag();
            bArticleTag.setArticleId(id);
            List<BArticleTag> bArticleTags =  ibArticleTagService.list(new QueryWrapper(bArticleTag));
            if(bArticleTags!=null&&bArticleTags.size()>0){
                List<Long> bArticleTagsIds = bArticleTags.stream().map(t->t.getId()).collect(Collectors.toList());
                ibArticleTagService.removeByIds(bArticleTagsIds);
            }
        }
        return ibArticleService.create(bAritleVo);
    }

    @Override
    public ResponseData editArticle(Long id){
        BUser bUser = BiemoLoginContext.me().getCurrentUser();
        if(bUser==null){
            return ResponseData.error("请先登录！");
        }
        return ResponseData.success(ibArticleService.getDetailById(id,"edit"));
    }
}
