package com.biemo.cloud.bbs.modular.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biemo.cloud.bbs.core.search.impl.TopicIndexQueryContext;
import com.biemo.cloud.bbs.core.search.utils.IndexKeyUtils;
import com.biemo.cloud.bbs.modular.domain.BTopic;
import com.biemo.cloud.bbs.modular.service.IndexService;
import com.biemo.cloud.kernel.model.response.ResponseData;
import com.biemo.cloud.kernel.scanner.modular.annotation.GetResource;
import com.biemo.cloud.kernel.scanner.modular.stereotype.ApiResource;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
@Api(tags = "论坛首页")
@ApiResource(name = "论坛首页", path = "/index")
public class IndexController {

    @Autowired
    @Qualifier("topicIndexService")
    private IndexService topicIndexService;

    @ApiOperation("论坛首页")
    @GetResource(name = "论坛获取栏目分类", path = "/appCategoryList")
    public ResponseData appCategoryList() {
        return null;
    }


    @ApiOperation("构建info索引")
    @RequestMapping("/createInfoIndex")
    public ResponseData createInfoIndex(int shards,int replicas,boolean rebuild)
    {
        topicIndexService.createIndex(shards, replicas, rebuild);
        return ResponseData.success();
    }

    @ApiOperation("构建info索引")
    @RequestMapping("/buildInfoIndex")
    public ResponseData buildInfoIndex() throws IOException {
        topicIndexService.buildIndex();
        return ResponseData.success();
    }

    @RequestMapping("/search")
    public ResponseData search(String keyword,Integer page){
        TopicIndexQueryContext topicIndexQueryContext = new TopicIndexQueryContext();
        topicIndexQueryContext.setContent(keyword);
        topicIndexQueryContext.setTitle(keyword);
        topicIndexQueryContext.setPageNum(page);
        topicIndexQueryContext.setPageSize(10);
        Page<BTopic> pages = topicIndexService.search(topicIndexQueryContext);
        //关键字标红
        Set<String> exps = IndexKeyUtils.expAnalysis(IndexKeyUtils.clean(keyword));
        pages.getRecords().forEach(row->row.highlighterText(exps));
        Map<String,Object> result = new HashMap<>();
        result.put("results",pages.getRecords());
        return ResponseData.success(result);
    }
}
