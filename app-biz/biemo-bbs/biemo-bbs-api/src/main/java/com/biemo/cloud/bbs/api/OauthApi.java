package com.biemo.cloud.bbs.api;

import com.biemo.cloud.kernel.model.response.ResponseData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@FeignClient(value = "biemo-bbs-app",contextId = "OauthApi")
public interface OauthApi {


    @RequestMapping("/{oauthType}/login")
    ResponseData oauth(@PathVariable String oauthType, HttpSession session);

    @RequestMapping("/{oauthType}/login/callback")
    ResponseData callback(@PathVariable String oauthType, @RequestParam("code") String code,
                                 @RequestParam("state") String state,
                                 Model model, HttpSession session, HttpServletRequest request);

}
