package com.biemo.cloud.bbs.api;

import com.biemo.cloud.kernel.model.response.ResponseData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "biemo-bbs-app",contextId = "BTagApi")
public interface BTagApi {

    @PostMapping("/autocomplete")
    ResponseData autocomplete(@RequestParam("input")String input);

    @GetMapping("/{tagId}")
    ResponseData getTag(@PathVariable("tagId") Long tagId);
}
