package com.biemo.cloud.bbs.api;

import com.biemo.cloud.kernel.model.response.ResponseData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "biemo-bbs-app",contextId = "BCommentApi")
public interface BCommentApi {

    @GetMapping("/list")
    ResponseData list(@RequestParam("entityType")String entityType, @RequestParam("entityId")Long entityId);

    @PostMapping("/create")
    ResponseData create(String contentType,String entityType,Long entityId,String content,String imageList,Long quoteId);
}
