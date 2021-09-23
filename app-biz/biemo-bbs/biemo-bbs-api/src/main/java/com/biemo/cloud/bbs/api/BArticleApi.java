package com.biemo.cloud.bbs.api;

import com.biemo.cloud.bbs.api.vo.BArticleVo;
import com.biemo.cloud.kernel.model.response.ResponseData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "biemo-bbs-app",contextId = "BArticleApi")
public interface BArticleApi {

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    ResponseData detail(@PathVariable("id") Long id);

    @GetMapping("/articles")
    ResponseData articles();

    @GetMapping("/nearly/{id}")
    ResponseData nearly(@PathVariable("id") Long id);

    @GetMapping("/related/{id}")
    ResponseData related(@PathVariable("id") Long id);

    @RequestMapping("/favorite/{id}")
    ResponseData userFavorite(@PathVariable("id") Long id);

    @RequestMapping("/tag/articles")
    ResponseData tagArticles(Long tagId);

    @GetMapping("/user/articles")
    ResponseData userArticles(BArticleVo bArticle);


    @PostMapping("/create")
    ResponseData articleCreate(@RequestParam(value = "id",required = false) Long id,String captchaId,String captchaCode ,String title,String content,String tags) throws Exception;

    @RequestMapping("/edit/{id}")
    public ResponseData editArticle(@PathVariable("id") Long id);
}
