package com.biemo.cloud.bbs.api;

import com.biemo.cloud.kernel.model.response.ResponseData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "biemo-bbs-app",contextId = "BLinkApi")
public interface BLinkApi {

    @GetMapping("/toplinks")
    ResponseData toplinks();
}
