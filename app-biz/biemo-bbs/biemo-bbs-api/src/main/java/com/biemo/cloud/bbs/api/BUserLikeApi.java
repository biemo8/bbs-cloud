package com.biemo.cloud.bbs.api;

import com.biemo.cloud.kernel.model.response.ResponseData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "biemo-bbs-app",contextId = "BUserLikeApi")
public interface BUserLikeApi {

    @GetMapping("/liked")
    ResponseData liked(@RequestParam("entityType")String entityType, @RequestParam("entityId")Long entityId);
}
