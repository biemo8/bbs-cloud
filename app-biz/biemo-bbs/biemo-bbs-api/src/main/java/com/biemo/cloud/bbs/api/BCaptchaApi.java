package com.biemo.cloud.bbs.api;

import com.biemo.cloud.kernel.model.response.ResponseData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@FeignClient(value = "biemo-bbs-app",contextId = "BCaptchaApi")
public interface BCaptchaApi {

    @GetMapping("/captchaImage")
    ResponseData getCode(HttpServletResponse response) throws IOException;
}
