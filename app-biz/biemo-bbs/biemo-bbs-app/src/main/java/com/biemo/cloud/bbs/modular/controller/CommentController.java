package com.biemo.cloud.bbs.modular.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biemo.cloud.bbs.api.BCommentApi;
import com.biemo.cloud.bbs.modular.context.BiemoLoginContext;
import com.biemo.cloud.bbs.modular.domain.BComment;
import com.biemo.cloud.bbs.modular.domain.BUser;
import com.biemo.cloud.bbs.modular.service.IBCommentService;
import com.biemo.cloud.bbs.modular.service.IBUserService;
import com.biemo.cloud.bbs.utils.MarkDown2HtmlUtils;
import com.biemo.cloud.core.page.PageFactory;
import com.biemo.cloud.core.util.StringUtils;
import com.biemo.cloud.core.util.uuid.IdUtils;
import com.biemo.cloud.kernel.model.response.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/comment")
public class CommentController extends  BaseController implements BCommentApi {

    @Autowired
    IBCommentService commentService;
    @Autowired
    IBUserService ibUserService;

    @Override
    public ResponseData list(@RequestParam("entityType")String entityType,@RequestParam("entityId")Long entityId){
        Map<String,Object> result = new HashMap<>();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("entity_type",entityType);
        queryWrapper.eq("entity_id",entityId);
        queryWrapper.orderByDesc("create_time");
        Page<BComment> page = commentService.page(PageFactory.defaultPage(),queryWrapper);
        List<BComment> bCommentList = page.getRecords();
        if(bCommentList!=null&&bCommentList.size()>0){
            List<JSONObject> jsonObjectList = new ArrayList<>();
            bCommentList.forEach(bComment -> {
                bComment.setContent(MarkDown2HtmlUtils.markdownToHtml(bComment.getContent()));
                JSONObject jsonObject = (JSONObject) JSONObject.toJSON(bComment);
                BUser bUser = ibUserService.getById(bComment.getUserId());
                jsonObject.put("user",bUser);

                BComment bComment1 = commentService.getById(bComment.getQuoteId());
                if(bComment1!=null){
                    JSONObject quoteJsonObj = (JSONObject)JSONObject.toJSON(bComment1);
                    quoteJsonObj.put("user",ibUserService.getById(bComment1.getUserId()));
                    jsonObject.put("quote",quoteJsonObj);
                }
                jsonObjectList.add(jsonObject);
            });

            boolean hasMore = page.getCurrent()<page.getPages()?true:false;
            result.put("hasMore",hasMore);
            result.put("pageNo",hasMore?page.getCurrent()+1:page.getCurrent());
            result.put("results",jsonObjectList);
        }
        return ResponseData.success(result);
    }
    @Override
    public ResponseData create(String contentType,String entityType,Long entityId,String content,String imageList,Long quoteId){
        BUser bUser = BiemoLoginContext.me().getCurrentUser();
        if(bUser==null){
            return ResponseData.error("请登录！");
        }
        BComment bComment = new BComment();
        bComment.setCreateTime(System.currentTimeMillis());
        bComment.setContent(content);
        bComment.setContentType(contentType);
        bComment.setEntityId(entityId);
        bComment.setImageList(imageList);
        bComment.setQuoteId(quoteId);
        bComment.setStatus(0L);
        bComment.setEntityType(entityType);
        bComment.setUserId(bUser.getId());
        bComment.setId(IdUtils.fastUUIDLong());
        return commentService.create(bComment);
    }

}
