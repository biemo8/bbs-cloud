package com.biemo.cloud.bbs.api;

import com.biemo.cloud.kernel.model.response.ResponseData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@FeignClient(value = "biemo-bbs-app",contextId = "BUploadApi")
public interface BUploadApi {

    @RequestMapping("/upload")
    @ResponseBody
    ResponseData uploadFile(@RequestParam("image") MultipartFile multipartFile,
                                   HttpServletRequest request);

    @RequestMapping("/upload/wangEditorUpload")
    @ResponseBody
    String WangEditorUpload(@RequestParam("file") MultipartFile multipartFile,
                                   HttpServletRequest request);

    @RequestMapping("/upload/CKEditorUpload")
    @ResponseBody
    String CKEditorUpload(@RequestParam("upload") MultipartFile multipartFile,
                                 HttpServletRequest request);

    @RequestMapping(value = "/res/{key}.{resType}")
    void showAttr(@PathVariable(value = "key",required = false) String key,
                         @PathVariable(value = "resType",required = false) String resType,
                         HttpServletRequest request, HttpServletResponse response);


}
