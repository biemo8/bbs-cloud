package com.biemo.cloud.bbs.modular.controller;

import java.util.*;
import java.util.stream.Collectors;

import cn.hutool.http.HtmlUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biemo.cloud.bbs.api.BTopicApi;
import com.biemo.cloud.bbs.api.vo.BTopicVo;
import com.biemo.cloud.bbs.modular.annotation.RequestLimit;
import com.biemo.cloud.bbs.modular.context.BiemoLoginContext;
import com.biemo.cloud.bbs.modular.domain.*;
import com.biemo.cloud.bbs.modular.domain.response.BTopicResponse;
import com.biemo.cloud.bbs.modular.service.*;
import com.biemo.cloud.bbs.utils.MarkDown2HtmlUtils;
import com.biemo.cloud.core.page.PageFactory;
import com.biemo.cloud.core.util.StringUtils;
import com.biemo.cloud.core.util.uuid.IdUtils;
import com.biemo.cloud.kernel.model.response.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * Controller
 *
 * @author makesoft
 * @date 2021-08-11
 */
@RestController
@RequestMapping("/topic")
public class BTopicController extends BaseController implements BTopicApi
{
    @Autowired
    private IBTopicService ibTopicService;
    @Autowired
    private IBTopicNodeService ibTopicNodeService;
    @Autowired
    private IBUserService ibUserService;
    @Autowired
    private IBUserLikeService ibUserLikeService;
    @Autowired
    private IBTopicTagService ibTopicTagService;

    /**
     * 查询列表
     */
    @Override
    public ResponseData topics(BTopicVo bTopic)
    {
        BUser bUser = BiemoLoginContext.me().getCurrentUser();
        JSONObject result = new JSONObject();
        List<JSONObject> jsonObjectList = new ArrayList<>();
        QueryWrapper queryWrapper = new QueryWrapper(bTopic);
        queryWrapper.orderByDesc("create_time");
        Page<BTopic> page = ibTopicService.page(PageFactory.defaultPage(),queryWrapper);
        List<BTopic> bTopics = page.getRecords();
        if(bTopics!=null&&bTopics.size()>0){
            bTopics.forEach(topic->{
                BTopicNode node = ibTopicNodeService.getById(topic.getNodeId());
                JSONObject jsonObject = (JSONObject) JSONObject.toJSON(topic);
                if(!StringUtils.isEmpty(topic.getContent())){
                    String content = MarkDown2HtmlUtils.markdownToHtml(topic.getContent());
                    content = HtmlUtil.cleanHtmlTag(content);
                    content = content.length()>=100?content.substring(100):content;
                    jsonObject.put("summary", content);
                }
                if(topic.getImageList()!=null){
                    jsonObject.put("imageList",JSONObject.parseArray(topic.getImageList()));
                }
                jsonObject.put("node",node);
                BUser user = ibUserService.getById(topic.getUserId());
                jsonObject.put("user",user);
                if(bUser!=null){
                    BUserLike bUserLike = new BUserLike();
                    bUserLike.setUserId(bUser.getId());
                    bUserLike.setEntityType("topic");
                    bUserLike.setEntityId(topic.getId());
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
    public ResponseData nodes(){
        List<BTopicNode> nodes = ibTopicNodeService.list();
        return ResponseData.success(nodes);
    }
    @Override
    public ResponseData node(Long id){
        return ResponseData.success(ibTopicNodeService.getById(id));
    }

    @Override
    public ResponseData topicDetail(Long topicId){
        return ResponseData.success(ibTopicService.getDetailById(topicId,null));
    }

    @Override
    public ResponseData topicEditDetail(Long topicId) {
        return ResponseData.success(ibTopicService.getDetailById(topicId,"edit"));
    }

    @Override
    public ResponseData userTopics(BTopicVo bTopic)
    {
        BUser bUser = BiemoLoginContext.me().getCurrentUser();
        JSONObject result = new JSONObject();
        List<JSONObject> jsonObjectList = new ArrayList<>();
        QueryWrapper<BTopic> queryWrapper = new QueryWrapper(bTopic);
        queryWrapper.orderByDesc("create_time");
        Page<BTopic> page = ibTopicService.page(PageFactory.defaultPage(),queryWrapper);
        List<BTopic> bTopics = page.getRecords();
        if(bTopics!=null&&bTopics.size()>0){
            bTopics.forEach(topic->{
                BTopicNode node = ibTopicNodeService.getById(topic.getNodeId());
                JSONObject jsonObject = (JSONObject) JSONObject.toJSON(topic);
                if(topic.getImageList()!=null){
                    jsonObject.put("imageList",JSONObject.parseArray(topic.getImageList()));
                }
                jsonObject.put("node",node);
                BUser user = ibUserService.getById(topic.getUserId());
                jsonObject.put("user",user);
                if(bUser!=null){
                    BUserLike bUserLike = new BUserLike();
                    bUserLike.setUserId(bUser.getId());
                    bUserLike.setEntityType("topic");
                    bUserLike.setEntityId(topic.getId());
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
    public ResponseData tagTopics(Long tagId) {
        BUser bUser = BiemoLoginContext.me().getCurrentUser();
        JSONObject result = new JSONObject();
        Page<BTopicResponse> page = ibTopicTagService.customPageList(PageFactory.defaultPage(),tagId);
        List<BTopicResponse> topicResponses = page.getRecords();
        List<JSONObject> jsonObjectList = new ArrayList<>();
        if(topicResponses!=null&&topicResponses.size()>0){
            topicResponses.forEach(topic->{
                JSONObject jsonObject = (JSONObject) JSONObject.toJSON(topic);
                if(topic.getImageList()!=null){
                    jsonObject.put("imageList",JSONObject.parseArray(topic.getImageList()));
                }
                if(!StringUtils.isEmpty(topic.getContent())){
                    String content = MarkDown2HtmlUtils.markdownToHtml(topic.getContent());
                    content = HtmlUtil.cleanHtmlTag(content);
                    content = content.length()>=100?content.substring(100):content;
                    jsonObject.put("summary", content);
                }
                if(bUser!=null){
                    BUserLike bUserLike = new BUserLike();
                    bUserLike.setUserId(bUser.getId());
                    bUserLike.setEntityType("topic");
                    bUserLike.setEntityId(topic.getId());
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

    //最近喜欢的
    @Override
    public ResponseData recentlikes(@PathVariable Long topicId){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("entity_id",topicId);
        queryWrapper.orderByDesc("create_time");
        queryWrapper.last("limit 10");
        List<BUserLike> bUserLikes = ibUserLikeService.list(queryWrapper);
        if(bUserLikes!=null&&bUserLikes.size()>0){
            List<Long> userIds = bUserLikes.stream().map(x->x.getUserId()).collect(Collectors.toList());
            List<BUser> users = ibUserService.listByIds(userIds);
            return ResponseData.success(users);
        }
        return ResponseData.success();
    }
    //点赞
    @Override
    public ResponseData userLike(@PathVariable("topicId") Long topicId){

        return ibTopicService.like(topicId);
    }

    //收藏
    @Override
    public ResponseData userFavorite(@PathVariable("topicId") Long topicId){

        return ibTopicService.favorite(topicId);
    }

    @Override
    @RequestLimit(count = 1,time = 60)
    public ResponseData create(String captchaId,String captchaCode ,Long type,Long nodeId,String title,String content,String imageList,String tags) throws Exception {
        BTopicVo bTopicVo = new BTopicVo();
        bTopicVo.setCaptchaId(captchaId);
        bTopicVo.setCaptchaCode(captchaCode);
        bTopicVo.setType(type);
        bTopicVo.setNodeId(nodeId);
        bTopicVo.setTitle(title);
        bTopicVo.setContent(content);
        bTopicVo.setImageList(imageList);
        bTopicVo.setTags(tags);
        return ibTopicService.create(bTopicVo);
    }


    @Override
    public ResponseData editSave(Long topicId,Long nodeId, String title, String content, String imageList, String tags) throws Exception {
        BTopicVo bTopicVo = new BTopicVo();
        bTopicVo.setNodeId(nodeId);
        bTopicVo.setTitle(title);
        bTopicVo.setContent(content);
        bTopicVo.setImageList(imageList);
        bTopicVo.setTags(tags);
        bTopicVo.setId(topicId);
        return ibTopicService.create(bTopicVo);
    }

    public static void main(String[] args){
        Set<Long> sets = new HashSet<>();
        for(int i=0;i<1000;i++){
            sets.add(IdUtils.fastUUIDLong()/1000);
        }
        System.out.println(sets.size());
    }

}
