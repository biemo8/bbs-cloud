package com.biemo.cloud.bbs.api;

import com.biemo.cloud.kernel.model.response.ResponseData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "biemo-bbs-app",contextId = "BSysConfigApi")
public interface BSysConfigApi {

    @GetMapping("/configs")
    ResponseData configs();
}
