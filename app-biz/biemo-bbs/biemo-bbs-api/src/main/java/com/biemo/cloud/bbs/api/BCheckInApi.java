package com.biemo.cloud.bbs.api;

import com.biemo.cloud.kernel.model.response.ResponseData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@FeignClient(value = "biemo-bbs-app",contextId = "BCheckInApi")
public interface BCheckInApi {

    @GetMapping("/getCheckin")
    ResponseData getCheckin();

    @PostMapping("/doCheckin")
    ResponseData doCheckin();

    @GetMapping("/rank")
    ResponseData rank();


}
