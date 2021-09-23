package com.biemo.cloud.bbs.modular.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biemo.cloud.bbs.api.BUserLikeApi;
import com.biemo.cloud.bbs.modular.context.BiemoLoginContext;
import com.biemo.cloud.bbs.modular.domain.BUser;
import com.biemo.cloud.bbs.modular.domain.BUserLike;
import com.biemo.cloud.bbs.modular.service.IBUserLikeService;
import com.biemo.cloud.kernel.model.response.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/like")
public class UserLikeController extends  BaseController implements BUserLikeApi {

    @Autowired
    private IBUserLikeService ibUserLikeService;

    @Override
    public ResponseData liked(@RequestParam("entityType")String entityType,@RequestParam("entityId")Long entityId){
        BUser bUser = BiemoLoginContext.me().getCurrentUser();
        if(bUser==null){
            return ResponseData.success(new HashMap(){{put("liked",false);}});
        }
        BUserLike userLike = new BUserLike();
        userLike.setUserId(bUser.getId());
        userLike.setEntityType(entityType);
        userLike.setEntityId(entityId);
        BUserLike bUserLike = ibUserLikeService.getOne(new QueryWrapper<>(userLike));
        if(bUserLike!=null){
            return ResponseData.success(new HashMap(){{put("liked",true);}});
        }
        return ResponseData.success(new HashMap(){{put("liked",false);}});
    }


}
