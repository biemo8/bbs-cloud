package com.biemo.cloud.bbs.modular.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biemo.cloud.bbs.api.BUserApi;
import com.biemo.cloud.bbs.api.vo.BUserVo;
import com.biemo.cloud.bbs.modular.context.BiemoLoginContext;
import com.biemo.cloud.bbs.modular.domain.*;
import com.biemo.cloud.bbs.modular.service.*;
import com.biemo.cloud.core.page.PageFactory;
import com.biemo.cloud.core.util.MD5Util;
import com.biemo.cloud.core.util.StringUtils;
import com.biemo.cloud.kernel.model.response.ResponseData;
import org.apache.kafka.common.internals.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.management.BufferPoolMXBean;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class BUserController extends BaseController implements BUserApi {

    @Autowired
    private IBUserService ibUserService;

    @Autowired
    private IBTopicService ibTopicService;

    @Autowired
    private IBTopicNodeService ibTopicNodeService;

    @Autowired
    private IBFavoriteService ibFavoriteService;

    @Autowired
    private IBArticleService ibArticleService;

    @Autowired
    private IBMessageService ibMessageService;

    @Autowired
    private IBUserScoreLogService ibUserScoreLogService;

    @Override
    public ResponseData scoreRank(){
        QueryWrapper<BUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("score");
        queryWrapper.last("limit 10");
        return ResponseData.success(ibUserService.list(queryWrapper));
    }

    @Override
    public ResponseData current(){
        return ResponseData.success(ibUserService.getcurrent());
    }

    //登录
    @Override
    public ResponseData signin(String captchaId,String captchaCode,String username,String password){

        return ibUserService.signin(captchaId,captchaCode,username,password);
    }

    @Override
    public ResponseData signup(BUserVo bUserVo) {

        return ibUserService.signup(bUserVo);
    }

    //登出
    @Override
    public ResponseData signout(@RequestParam("userToken") String userToken){
        return ResponseData.success(ibUserService.signout(userToken));
    }


    @Override
    public ResponseData msgrecent(){
        //{"count":0,"messages":null}
        return ibUserService.msgrecent();
    }
    @Override
    public ResponseData user_id(@PathVariable("userId") String userId,@RequestParam(value = "pageNo",required = false) Integer pageNo){
        if("messages".equals(userId)){
            BUser bUser = BiemoLoginContext.me().getCurrentUser();
            if(bUser==null){
                return ResponseData.error("请先登录！");
            }
            BMessage bMessage = new BMessage();
            bMessage.setUserId(bUser.getId());
            Page<BMessage> page= ibMessageService.page(PageFactory.defaultPage(),new QueryWrapper<>(bMessage).orderByDesc("create_time"));
            JSONObject result = new JSONObject();
            List<JSONObject> jsonObjectList = new ArrayList<>();
            if(page.getRecords()!=null&&page.getRecords().size()>0){
                List<BMessage> bMessageUpdateList = new ArrayList<>();
                page.getRecords().forEach(message->{
                    JSONObject tmpMessage = (JSONObject)JSONObject.toJSON(message);
                    tmpMessage.put("from",ibUserService.getById(message.getFromId()));
                    jsonObjectList.add(tmpMessage);
                    message.setStatus(1L);
                    bMessageUpdateList.add(message);
                });
                ibMessageService.updateBatchById(bMessageUpdateList);
            }
            result.put("results",jsonObjectList);
            Page<BMessage> finalPage = page;
            result.put("page",new JSONObject(){{put("page",pageNo);put("limit",PageFactory.PAGE_SIZE);put("total", finalPage.getTotal());}});
            return ResponseData.success(result);
        }else if("scorelogs".equals(userId)){
            BUser bUser = BiemoLoginContext.me().getCurrentUser();
            if(bUser==null){
                return ResponseData.error("请先登录！");
            }
            BUserScoreLog bUserScoreLog = new BUserScoreLog();
            bUserScoreLog.setUserId(bUser.getId());
            Page<BUserScoreLog> page= ibUserScoreLogService.page(PageFactory.defaultPage(),new QueryWrapper<>(bUserScoreLog).orderByDesc("create_time"));
            JSONObject result = new JSONObject();
            List<JSONObject> jsonObjectList = new ArrayList<>();
            if(page.getRecords()!=null&&page.getRecords().size()>0){
                page.getRecords().forEach(userScoreLog->{
                    JSONObject tmpMessage = (JSONObject)JSONObject.toJSON(userScoreLog);
                    tmpMessage.put("user",ibUserService.getById(userScoreLog.getUserId()));
                    jsonObjectList.add(tmpMessage);
                });
            }
            result.put("results",jsonObjectList);
            Page<BUserScoreLog> finalPage = page;
            result.put("page",new JSONObject(){{put("page",pageNo);put("limit",PageFactory.PAGE_SIZE);put("total", finalPage.getTotal());}});
            return ResponseData.success(result);
        }
        return ResponseData.success(ibUserService.getById(userId));
    }

    //用户收藏列表
    @Override
    public ResponseData favorites(){
        BUser bUser = BiemoLoginContext.me().getCurrentUser();
        if(bUser==null){
            return ResponseData.error("没登录！");
        }
        JSONObject result = new JSONObject();
        List<JSONObject> jsonObjectList = new ArrayList<>();
        QueryWrapper<BFavorite> queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id",bUser.getId());
        queryWrapper.orderByDesc("create_time");
        Page<BFavorite> page = ibFavoriteService.page(PageFactory.defaultPage(),queryWrapper);
        List<BFavorite> bFavorites = page.getRecords();
        if(bFavorites!=null&&bFavorites.size()>0){
            bFavorites.forEach(favorite->{
                Map<String,Object> map = new HashMap<>();
                JSONObject jsonObject = new JSONObject();
                if("topic".equals(favorite.getEntityType())){
                    BTopic topic = ibTopicService.getById(favorite.getEntityId());
                    if(topic!=null){
                        BTopicNode node = ibTopicNodeService.getById(topic.getNodeId());
                        BUser user = ibUserService.getById(topic.getUserId());
                        jsonObject = (JSONObject) JSONObject.toJSON(topic);
                        jsonObject.put("node",node);
                        jsonObject.put("user",user);
                        jsonObject.put("url","/topic/"+favorite.getEntityId());
                    }

                }else if("article".equals(favorite.getEntityType())){
                    BArticle article = ibArticleService.getById(favorite.getEntityId());
                    if(article!=null){
                        jsonObject = (JSONObject) JSONObject.toJSON(article);
                        BUser user = ibUserService.getById(article.getUserId());
                        jsonObject.put("user",user);
                        jsonObject.put("url","/article/"+favorite.getEntityId());
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
    public ResponseData setEmail(String email){
        if(StringUtils.isEmpty(email)){
            return ResponseData.error("参数不能为空！");
        }
        BUser user = BiemoLoginContext.me().getCurrentUser();
        if(user==null){
            return ResponseData.error("请先登录！");
        }
        user.setEmail(email);
        ibUserService.updateById(user);
        //更新缓存
        ibUserService.updateCache(user);
        return ResponseData.success();
    }
    @Override
    public ResponseData setBackgroundImage(String backgroundImage){
        if(StringUtils.isEmpty(backgroundImage)){
            return ResponseData.error("参数不能为空！");
        }
        BUser user = BiemoLoginContext.me().getCurrentUser();
        if(user==null){
            return ResponseData.error("请先登录！");
        }
        user.setBackgroundImage(backgroundImage);
        ibUserService.updateById(user);
        //更新缓存
        ibUserService.updateCache(user);
        return ResponseData.success();
    }
    @Override
    public ResponseData updateAvatar(String avatar){
        if(StringUtils.isEmpty(avatar)){
            return ResponseData.error("参数不能为空！");
        }
        BUser user = BiemoLoginContext.me().getCurrentUser();
        if(user==null){
            return ResponseData.error("请先登录！");
        }
        user.setAvatar(avatar);
        ibUserService.updateById(user);
        ibUserService.updateCache(user);
        return ResponseData.success();
    }
    @Override
    public ResponseData edit(@PathVariable("userId") String userId,String nickname,String avatar,String homePage,String description){
        if(StringUtils.isEmpty(userId)){
            return ResponseData.error("参数不能为空！");
        }
        BUser user = BiemoLoginContext.me().getCurrentUser();
        if(user==null){
            return ResponseData.error("请先登录！");
        }
        user.setNickname(nickname);
        user.setAvatar(avatar);
        user.setHomePage(homePage);
        user.setDescription(description);
        ibUserService.updateById(user);
        ibUserService.updateCache(user);
        return ResponseData.success();
    }
    @Override
    public ResponseData setPassword(String password,String rePassword){
        if(StringUtils.isEmpty(password)||StringUtils.isEmpty(rePassword)){
            return ResponseData.error("参数不能为空！");
        }
        if(!password.equals(rePassword)){
            return ResponseData.error("输入的密码不一致！");
        }
        BUser user = BiemoLoginContext.me().getCurrentUser();
        if(user==null){
            return ResponseData.error("请先登录！");
        }
        user.setPassword(MD5Util.encrypt(password));
        ibUserService.updateById(user);
        ibUserService.updateCache(user);
        return ResponseData.success();
    }
}
