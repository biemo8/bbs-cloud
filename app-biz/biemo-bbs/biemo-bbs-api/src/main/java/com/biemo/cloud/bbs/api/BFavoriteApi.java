package com.biemo.cloud.bbs.api;

import com.biemo.cloud.kernel.model.response.ResponseData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "biemo-bbs-app",contextId = "BFavoriteApi")
public interface BFavoriteApi {

    @GetMapping("/favorited")
    ResponseData liked(@RequestParam("entityType")String entityType, @RequestParam("entityId")Long entityId);

    @GetMapping("/delete")
    ResponseData delete(String entityType,Long entityId);
}
