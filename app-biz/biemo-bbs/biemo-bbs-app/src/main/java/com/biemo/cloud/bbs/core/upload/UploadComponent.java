package com.biemo.cloud.bbs.core.upload;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.biemo.cloud.bbs.core.upload.bean.UploadBean;
import com.biemo.cloud.bbs.modular.context.BiemoLoginContext;
import com.biemo.cloud.bbs.modular.domain.BAttachment;
import com.biemo.cloud.bbs.modular.domain.BUser;
import com.biemo.cloud.bbs.modular.service.IBAttachmentService;
import com.biemo.cloud.bbs.utils.ControllerUtil;
import com.biemo.cloud.bbs.utils.PathUtil;
import com.biemo.cloud.bbs.utils.QiniuUtil;
import com.biemo.cloud.core.util.DateUtils;
import com.biemo.cloud.core.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.UUID;

import static com.sun.org.apache.xerces.internal.impl.dv.util.Base64.decode;

/**
 * Description:
 *
 * @author BIEMO
 * @create 2017-07-19
 **/
@Component
@Scope("prototype")
public class UploadComponent {


    @Value("${virtual.path.enable}")
    String enableVirtualPath;

    @Value("${virtual.path.windows}")
    String windowsUploadPath;

    @Value("${virtual.path.linux}")
    String linuxUploadPath;

    @Value("${qiniu.accessKey}")
    private String accessKey;

    @Value("${qiniu.secretKey}")
    private String secretKey;

    @Value("${qiniu.bucketname}")
    private String bucketname;

    @Value("${qiniu.cdnDomain}")
    private String domain;

    @Value("${qiniu.upload.enable}")
    private String qiniuUpload;

    @Value("virtual.path.enableFullPath")
    private String enableFullPath = "false";

    @Autowired
    IBAttachmentService attachmentService;


    public UploadBean uploadFile(MultipartFile multipartFile, HttpServletRequest request){
        if (!multipartFile.isEmpty()) {
            BAttachment attachment = new BAttachment();
            /** 获取用户会话 **/
            BUser userVo  = BiemoLoginContext.me().getCurrentUser();
            if(userVo!=null) {
                attachment.setUserId(userVo.getId().toString());
                attachment.setUserName(userVo.getUsername());
            }
            attachment.setUploadDate(new Date());
            attachment.setUploadIp(ControllerUtil.getRemoteAddress(request));
            attachment.setFileSize(Float.valueOf(multipartFile.getSize())/1024);
            attachment.setFileExtname(multipartFile.getContentType());
            attachment.setFileKey(UUID.randomUUID().toString().replace("-",""));
            attachment.setOriginalFileName(multipartFile.getOriginalFilename());
            /*创建uploadBean*/
            UploadBean result = new UploadBean();
            String fileType = this.getFileType(attachment.getOriginalFileName());
            String fileName = this.getFileName(fileType) ;
            String newName =this.getNewFileName(fileName);
            if (!Boolean.parseBoolean(qiniuUpload)) {
                File file = new File(this.getUploadPath() + newName);
                /*如果不存在就创建*/
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                try {
                    this.writeFile(multipartFile.getBytes(), file);
                    attachment.setFilePath(newName);
                    attachment.setFileName(fileName);
                    if(Boolean.parseBoolean(enableFullPath)) {
                        result.setUrl(request.getScheme() + "://" + ControllerUtil.getDomain() + "/res/" + attachment.getFileKey() + "." + fileType);
                    }else {
                        result.setUrl("/res/" + attachment.getFileKey() + "." + fileType);

                    }
                    attachmentService.save(attachment);
                } catch (Exception e) {
                    throw new RuntimeException(e.getMessage());
                }
            }else {
                String qiniuFileResult = QiniuUtil.upload(accessKey, secretKey, bucketname, multipartFile);
                if (!StrUtil.isBlank(qiniuFileResult)) {
                    String fileKey = JSON.parseObject(qiniuFileResult).getString("key");
                    String fileUrl = domain + "/" + fileKey;
                    //result.setPreview(fileUrl+"?imageView2/0/w/180/h/180/q/75");
                    result.setPreview(fileUrl+"?imageMogr2/thumbnail/180x180/format/webp/blur/1x0/quality/75");
                    if (com.biemo.cloud.core.util.StringUtils.getExtensionName(fileName).equals("jpg") || StringUtils.getExtensionName(fileName).equals("JPG") || StringUtils.getExtensionName(fileName).equals("png") || StringUtils.getExtensionName(fileName).equals("PNG") || StringUtils.getExtensionName(fileName).equals("jpeg") ||StringUtils.getExtensionName(fileName).equals("JPEG")) {
                        fileUrl += "?imageslim";
                    }
                    result.setUrl(fileUrl);
                }
            }
            return result;
        }else{
            throw new RuntimeException("上传文件不能为空！");
        }
    }

    public String getUploadPath(){
        String os = System.getProperty("os.name");
        String uploadPath = enableVirtualPath.equals("true")
                ?(os.toLowerCase().startsWith("win")
                ?windowsUploadPath
                :linuxUploadPath)
                : PathUtil.getRootClassPath()+ File.separator + "static";
        return uploadPath;
    }

    public Charset getCharset(){
        String os = System.getProperty("os.name");
        Charset charset = os.toLowerCase().startsWith("win")
                ? Charset.forName("gbk")
                :Charset.forName("UTF-8");
        return charset;
    }
    public  UploadBean uploadBase64File(String data, HttpServletRequest request){

        /*创建uploadBean*/
        UploadBean result = new UploadBean();
        if (!StringUtils.isBlank(data)) {
            BAttachment attachment = new BAttachment();
            BUser userVo  = BiemoLoginContext.me().getCurrentUser();
            if(userVo!=null) {
                attachment.setUserId(userVo.getId().toString());
                attachment.setUserName(userVo.getUsername());
            }
            attachment.setUploadDate(new Date());
            attachment.setUploadIp(ControllerUtil.getRemoteAddress(request));
            attachment.setFileSize(this.getBase64FileSize(data).floatValue()/1024);
            if(getBase64FileType(data).equals("png")){
               attachment.setFileExtname("image/png");
            }else if(getBase64FileType(data).equals("gif")){
                attachment.setFileExtname("image/gif");
            }else if(getBase64FileType(data).equals("jpg")){
                attachment.setFileExtname("image/jpeg");
            }
            attachment.setFileKey(UUID.randomUUID().toString().replace("-",""));
           String fileType = this.getBase64FileType(data);
           String fileName = this.getFileName(fileType);
           String newName = this.getNewFileName(fileName);
            attachment.setOriginalFileName(newName);
            File file = new File(this.getUploadPath() + newName);
            attachment.setFilePath(file.getPath());
            attachment.setFileName(fileName);
            /*如果不存在就创建*/
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            try {
                this.writeFile(this.base64StrToByte(data),file);
            } catch (Exception e) {
                throw  new RuntimeException(e.getMessage());
            }
            if(Boolean.parseBoolean(enableFullPath)) {
                result.setUrl(request.getScheme() + "://" + ControllerUtil.getDomain() + "/res/" + attachment.getFileKey() + "." + fileType);
            }else {
                result.setUrl("/res/" + attachment.getFileKey() + "." + fileType);

            }
            return result;
        }else{
                throw new RuntimeException("上传文件不能为空！");
        }
    }

    public  String getFileName(String fileType){
      return UUID.randomUUID().toString().replace("-","") + "." + fileType;
    }

    public  String getZipFileName(String fileName){
        return File.separator + "upload" + File.separator +"ebook"+File.separator + DateUtils.getYears()
                + File.separator + DateUtils.getMonth() + File.separator + DateUtils.getDay() + File.separator + fileName;
    }

    public  String getNewFileName(String fileName){
        return File.separator + "upload" + File.separator + DateUtils.getYears()
                + File.separator + DateUtils.getMonth() + File.separator + DateUtils.getDay() + File.separator + fileName;
    }

    public  String getFileType(String fileName) {
        String type = fileName.substring(fileName.lastIndexOf(".") + 1);
        return type;
    }

    /***
     * data:,文本数据
     data:text/plain,文本数据
     data:text/html,HTML代码
     data:text/html;base64,base64编码的HTML代码
     data:text/css,CSS代码
     data:text/css;base64,base64编码的CSS代码
     data:text/JavaScript,Javascript代码
     data:text/javascript;base64,base64编码的Javascript代码
     data:image/gif;base64,base64编码的gif图片数据
     data:image/png;base64,base64编码的png图片数据
     data:image/jpeg;base64,base64编码的jpeg图片数据
     data:image/x-icon;base64,base64编码的icon图片数据
     */
    public  String getBase64FileType(String data) {
       String fileType = data.split(",")[0];
       System.out.println(fileType);
       if("data:image/png;base64".equals(fileType)){
           return "png";
       }
       if("data:image/gif;base64".equals(fileType)){
            return "gif";
       }
        if("data:image/jpeg;base64".equals(fileType)){
            return "jpeg";
        }
       throw new RuntimeException("对不起，请上传正确的文件格式[.png,.gif,.jpg]");
    }

    private Integer getBase64FileSize(String data){
        String str = data.split(",")[1];
        Integer  equalIndex= str.indexOf("=");
        if(str.indexOf("=")>0) {
            str=str.substring(0, equalIndex);
        }
        Integer strLength=str.length();
       return strLength-(strLength/8)*2;
    }

    private byte[] base64StrToByte(String data){
        byte[] b ;
        b = decode(data.split(",")[1]);
        for (int i = 0; i < b.length; ++i) {
            if (b[i] < 0) {// 调整异常数据
                b[i] += 256;
            }
        }
        return b;
    }


    public  String getEbookFileFolder(){
        return File.separator + "upload" + File.separator +"ebook" + File.separator + DateUtils.getYears()
                + File.separator + DateUtils.getMonth() + File.separator + DateUtils.getDay() ;
    }

    private synchronized void writeFile(byte[] bytes,File file) throws Exception{
        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file));
        stream.write(bytes);
        stream.close();
    }


    public static void  main(String[] args){
        UploadComponent uploadComponent = new UploadComponent();
        String s  = "1502176167115.png";
        System.out.println(s.contains("pngs"));
        uploadComponent.getBase64FileSize("data:image/jpeg;base64,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");
    }
}
