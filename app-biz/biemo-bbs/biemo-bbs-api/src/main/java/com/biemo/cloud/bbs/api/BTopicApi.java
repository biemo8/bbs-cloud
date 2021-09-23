package com.biemo.cloud.bbs.api;

import com.biemo.cloud.bbs.api.vo.BTopicVo;
import com.biemo.cloud.kernel.model.response.ResponseData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "biemo-bbs-app",contextId = "BTopicApi")
public interface BTopicApi {
    @GetMapping("/topics")
    ResponseData topics(BTopicVo bTopic);

    @GetMapping("/nodes")
    ResponseData nodes();

    @GetMapping("/node")
    ResponseData node(Long id);

    @GetMapping("/{topicId}")
    ResponseData topicDetail(@PathVariable("topicId") Long topicId);

    @GetMapping("/user/topics")
    ResponseData userTopics(BTopicVo bTopic);

    @GetMapping("/recentlikes/{topicId}")
    ResponseData recentlikes(@PathVariable Long topicId);

    @PostMapping("/like/{topicId}")
    ResponseData userLike(@PathVariable("topicId") Long topicId);

    @GetMapping("/favorite/{topicId}")
    ResponseData userFavorite(@PathVariable("topicId") Long topicId);

    @PostMapping("/create")
    ResponseData create(String captchaId,String captchaCode ,Long type,Long nodeId,String title,String content,String imageList,String tags) throws Exception;
}
