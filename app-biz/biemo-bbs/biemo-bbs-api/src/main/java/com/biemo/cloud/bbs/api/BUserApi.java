package com.biemo.cloud.bbs.api;

import com.biemo.cloud.bbs.api.vo.BUserVo;
import com.biemo.cloud.kernel.model.response.ResponseData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "biemo-bbs-app",contextId = "BUserApi")
public interface BUserApi {

    @GetMapping("/score/rank")
    ResponseData scoreRank();

    @GetMapping("/current")
    ResponseData current();

    @PostMapping("/signin")
    ResponseData signin(String captchaId,String captchaCode,String username,String password);

    @PostMapping("/signup")
    ResponseData signup(BUserVo bUserVo);

    @GetMapping("/signout")
    ResponseData signout(@RequestParam("userToken") String userToken);

    @GetMapping("/msgrecent")
    ResponseData msgrecent();

    @GetMapping("/{userId}")
    ResponseData user_id(@PathVariable("userId") String userId, @RequestParam(value = "pageNo",required = false) Integer pageNo);

    @GetMapping("/favorites")
    ResponseData favorites();

    @PostMapping("/set/email")
    ResponseData setEmail(String email);

    @PostMapping("/set/background/image")
    ResponseData setBackgroundImage(String backgroundImage);

    @PostMapping("/update/avatar")
    ResponseData updateAvatar(String avatar);

    @PostMapping("/edit/{userId}")
    ResponseData edit(@PathVariable("userId") String userId,String nickname,String avatar,String homePage,String description);

    @PostMapping("/set/password")
    ResponseData setPassword(String password,String rePassword);
}
