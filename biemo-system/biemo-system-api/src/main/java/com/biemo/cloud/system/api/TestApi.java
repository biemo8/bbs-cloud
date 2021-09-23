package com.biemo.cloud.system.api;

import com.biemo.cloud.kernel.model.response.ResponseData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "biemo-system-app")
public interface TestApi {

    @RequestMapping(value = "/test")
    public ResponseData test(@RequestParam("a") String a);
}
