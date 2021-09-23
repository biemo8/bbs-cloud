package com.biemo.cloud.biz.file.modular.controller;

import com.biemo.cloud.biz.file.api.entity.Fileinfo;
import com.biemo.cloud.biz.file.modular.service.FileinfoService;
import com.biemo.cloud.core.page.PageFactory;
import com.biemo.cloud.core.request.RequestData;
import com.biemo.cloud.kernel.model.response.ResponseData;
import com.biemo.cloud.kernel.scanner.modular.annotation.PostResource;
import com.biemo.cloud.kernel.scanner.modular.stereotype.ApiResource;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 文件管理系统的接口
 *
 *
 * @Date 2018/4/17 23:14
 */
@RestController
@ApiResource(name = "文件后台管理", path = "/fileManager")
@Slf4j
public class FileManagerController {

    @Autowired
    private FileinfoService fileinfoService;

    /**
     * 获取文件列表
     *
     *
     * @Date 2018/7/27 下午4:10
     */
    @PostResource(name = "查询文件列表", path = "/getFileInfoList")
    public ResponseData getFileInfoList(RequestData requestData) {
        Fileinfo fileinfo = requestData.parse(Fileinfo.class);
        Page<Fileinfo> page = PageFactory.defaultPage();

        List<Fileinfo> results = fileinfoService.getFileInfoList(page, fileinfo);
        page.setRecords(results);
        return ResponseData.success(results);
    }

}
