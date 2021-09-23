package com.biemo.cloud.bbs.modular.controller;

import com.biemo.cloud.kernel.model.response.ResponseData;
import com.biemo.cloud.kernel.scanner.modular.annotation.GetResource;
import com.biemo.cloud.kernel.scanner.modular.stereotype.ApiResource;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "论坛首页")
@ApiResource(name = "论坛首页", path = "/bbs")
public class IndexController {

    @ApiOperation("论坛首页")
    @GetResource(name = "论坛获取栏目分类", path = "/appCategoryList")
    public ResponseData appCategoryList() {
        return null;
    }
}
