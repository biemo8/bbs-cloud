package com.biemo.cloud.bbs.modular.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biemo.cloud.bbs.api.BUploadApi;
import com.biemo.cloud.bbs.core.upload.UploadComponent;
import com.biemo.cloud.bbs.core.upload.bean.UploadBean;
import com.biemo.cloud.bbs.modular.domain.BAttachment;
import com.biemo.cloud.bbs.modular.service.IBAttachmentService;
import com.biemo.cloud.core.util.StringUtils;
import com.biemo.cloud.kernel.model.response.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.net.URLEncoder;


@Controller
public class UploadController implements BUploadApi {

    @Autowired
    private UploadComponent uploadComponent;

    @Autowired
    private IBAttachmentService attachmentService;

    @ResponseBody
    @Override
    public ResponseData uploadFile(@RequestParam("image") MultipartFile multipartFile,
                               HttpServletRequest request){
        UploadBean result = uploadComponent.uploadFile(multipartFile,request);

        return ResponseData.success(result);
    }


    @ResponseBody
    @Override
    public String WangEditorUpload(@RequestParam("file") MultipartFile multipartFile,
                         HttpServletRequest request) {
        UploadBean result = uploadComponent.uploadFile(multipartFile,request);
        return result.getUrl();

    }


    @ResponseBody
    @Override
    public String CKEditorUpload(@RequestParam("upload") MultipartFile multipartFile,
                                 HttpServletRequest request) {
        StringBuffer sb=new StringBuffer();
        UploadBean result = uploadComponent.uploadFile(multipartFile,request);
        sb.append("<script type=\"text/javascript\">");
        sb.append("window.parent.CKEDITOR.tools.callFunction("+ request.getParameter("CKEditorFuncNum") + ",'" +result.getUrl()+"','')");
        sb.append("</script>");
        return sb.toString();

    }

    @Override
    public void showAttr(@PathVariable(value = "key",required = false) String key,
                         @PathVariable(value = "resType",required = false) String resType,
                         HttpServletRequest request,HttpServletResponse response) {
        if(StringUtils.isEmpty(key))return;
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("file_key",key);
        BAttachment attachment = attachmentService.getOne(queryWrapper);
        if(StringUtils.isNullOrEmpty(attachment)||!attachment.getFileName().contains(resType))
            throw new RuntimeException("文件不存在！");
        try {
            response.reset();
            /* 判断浏览器类型，设置文件下载名 */
            String userAgent = request.getHeader("user-agent").toLowerCase();
            if (userAgent.contains("msie") || userAgent.contains("like gecko")||userAgent.contains("trident")||userAgent.contains("edge")) {
                attachment.setOriginalFileName(URLEncoder.encode(attachment.getOriginalFileName(), "UTF-8"));
            } else {
                attachment.setOriginalFileName(new String(attachment.getOriginalFileName().getBytes("utf-8"), "ISO8859-1"));
            }
            response.setHeader("Content-disposition", "attachment;filename="+attachment.getOriginalFileName());
            response.setContentType(attachment.getFileExtname());
            FileCopyUtils.copy(new FileInputStream(uploadComponent.getUploadPath()+attachment.getFilePath()), response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
//            throw new RuntimeException(e.getMessage());
        }
    }

}
