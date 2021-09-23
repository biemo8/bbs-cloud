package com.biemo.cloud.bbs.modular.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biemo.cloud.bbs.api.BTagApi;
import com.biemo.cloud.bbs.modular.domain.BTag;
import com.biemo.cloud.bbs.modular.service.IBTagService;
import com.biemo.cloud.kernel.model.response.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/tag")
public class TagController extends  BaseController implements BTagApi {

    @Autowired
    private IBTagService ibTagService;

    @Override
    public ResponseData autocomplete(@RequestParam("input")String input){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.like("name",input);
        List<BTag> tagList = ibTagService.list(queryWrapper);
        return ResponseData.success(tagList);
    }
    @Override
    public ResponseData getTag(@PathVariable("tagId") Long tagId){
        return ResponseData.success(ibTagService.getById(tagId));
    }

}
