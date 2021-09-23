package com.biemo.cloud.bbs.modular.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biemo.cloud.bbs.api.BFavoriteApi;
import com.biemo.cloud.bbs.modular.context.BiemoLoginContext;
import com.biemo.cloud.bbs.modular.domain.BFavorite;
import com.biemo.cloud.bbs.modular.domain.BUser;
import com.biemo.cloud.bbs.modular.domain.BUserLike;
import com.biemo.cloud.bbs.modular.service.IBFavoriteService;
import com.biemo.cloud.kernel.model.response.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/favorite")
public class FavoriteController extends  BaseController implements BFavoriteApi {

    @Autowired
    private IBFavoriteService ibFavoriteService;

    @Override
    public ResponseData liked(@RequestParam("entityType")String entityType,@RequestParam("entityId")Long entityId){
        BUser bUser = BiemoLoginContext.me().getCurrentUser();
        if(bUser==null){
            return ResponseData.success(new HashMap(){{put("favorited",false);}});
        }
        BFavorite bFavorite = new BFavorite();
        bFavorite.setUserId(bUser.getId());
        bFavorite.setEntityType(entityType);
        bFavorite.setEntityId(entityId);
        BFavorite bFavoriteNew = ibFavoriteService.getOne(new QueryWrapper<>(bFavorite));
        if(bFavoriteNew!=null){
            return ResponseData.success(new HashMap(){{put("favorited",true);}});
        }
        return ResponseData.success(new HashMap(){{put("favorited",false);}});
    }

    @Override
    public ResponseData delete(String entityType,Long entityId){
        BUser bUser = BiemoLoginContext.me().getCurrentUser();
        if(bUser==null){
            return ResponseData.error("请登录！");
        }
        BFavorite bFavorite = new BFavorite();
        bFavorite.setUserId(bUser.getId());
        bFavorite.setEntityType(entityType);
        bFavorite.setEntityId(entityId);
        ibFavoriteService.remove(new QueryWrapper<>(bFavorite));
        return ResponseData.success();
    }




}
