package com.biemo.cloud.biz.file.modular.controller;

import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.core.io.IoUtil;
import com.biemo.cloud.biz.file.api.entity.Fileinfo;
import com.biemo.cloud.biz.file.core.exceptions.FileExceptionEnum;
import com.biemo.cloud.biz.file.core.storage.FileOperator;
import com.biemo.cloud.biz.file.core.util.FileUtil;
import com.biemo.cloud.biz.file.modular.service.FileinfoService;
import com.biemo.cloud.core.util.ToolUtil;
import com.biemo.cloud.kernel.model.exception.RequestEmptyException;
import com.biemo.cloud.kernel.model.exception.ServiceException;
import com.biemo.cloud.kernel.model.response.ResponseData;
import com.biemo.cloud.kernel.scanner.modular.annotation.GetResource;
import com.biemo.cloud.kernel.scanner.modular.annotation.PostResource;
import com.biemo.cloud.kernel.scanner.modular.stereotype.ApiResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 文件上传下载管理
 *
 *
 * @Date 2018/4/17 23:14
 */
@RestController
@ApiResource(name = "文件管理", path = "/file")
@Slf4j
public class FileController {

    @Autowired
    private FileinfoService fileinfoService;

    @Autowired
    private FileOperator fileOperator;

    /**
     * 上传文件
     *
     *
     * @Date 2018/4/18 13:52
     */
    @PostResource(name = "文件上传", path = "/upload")
    public ResponseData upload(MultipartHttpServletRequest multiReq) {

        MultipartFile file = multiReq.getFile("file");
        if (file == null) {
            throw new RequestEmptyException("传递file为空！");
        }

        //获取文件流
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            log.error("上传文件时，读取文件流错误！", e);
            throw new ServiceException(FileExceptionEnum.IO_ERROR);
        }

        //获取文件名称
        String fileName = file.getOriginalFilename();
        if (ToolUtil.isEmpty(fileName)) {
            throw new ServiceException(FileExceptionEnum.FILE_NOT_FOUND);
        }
        log.info("文件系统中的上传文件名称：------------------" + fileName);

        //获取文件大小
        long size = file.getSize();

        //保存文件
        String fileId = fileinfoService.uploadFileAndSaveFileInfo(inputStream, fileName, size);

        //响应文件唯一标识
        if (fileId == null) {
            return ResponseData.success();
        } else {
            return ResponseData.success(fileId);
        }
    }

    /**
     * 文件下载
     *
     *
     * @Date 2018/4/18 13:52
     */
    @GetResource(name = "文件下载", path = "/download")
    public void downloadFile(HttpServletRequest request, HttpServletResponse response) {

        //获取文件唯一标识号
        String fileId = request.getParameter("fileId");
        if (ToolUtil.isEmpty(fileId)) {
            throw new RequestEmptyException("文件id为空！");
        }

        //判断文件id是否是long类型
        Long fileIdLong;
        try {
            fileIdLong = Long.valueOf(fileId);
        } catch (NumberFormatException e) {
            throw new ServiceException(FileExceptionEnum.FILE_NAME_ERROR);
        }

        //读取文件存的目录
        Fileinfo fileinfo = fileinfoService.getFileInfo(fileIdLong);

        String fileOriginName = FileUtil.getFileName(fileinfo.getFileOriginName(), fileinfo.getFileSuffix());
        try {
            fileOriginName = new String(fileOriginName.getBytes(), "ISO-8859-1");
        } catch (IOException e) {
            log.error("文件中文名称转换异常", e);
            throw new ServiceException(FileExceptionEnum.FILE_NAME_FORMAT_ERROR);
        }

        //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
        response.setContentType("application/octet-stream; charset=utf-8");
        //2.设置文件头：最后一个参数是设置下载文件名(假如我们叫a.pdf)
        response.setHeader("Content-Disposition", "attachment; filename=" + fileOriginName);

        byte[] fileBytes = fileOperator.getFileBytes(fileinfo.getFileStorageName());

        OutputStream out = null;
        try {
            out = response.getOutputStream();
            out.write(fileBytes);
        } catch (IOException e) {
            log.error("获取文件异常", e);
            throw new ServiceException(FileExceptionEnum.IO_ERROR);
        } finally {
            IoUtil.close(out);
        }
    }

    /**
     * 预览文件
     *
     *
     * @Date 2018/4/18 13:52
     */
    @GetResource(name = "预览文件", path = "/preview")
    public void preview(HttpServletRequest request, HttpServletResponse response) {
        String fileId = request.getParameter("fileId");
        if (ToolUtil.isEmpty(fileId)) {
            throw new RequestEmptyException("文件id为空！");
        }

        String fileUrl = fileinfoService.getFileUrlById(Long.valueOf(fileId));
        try {
            response.sendRedirect(fileUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 预览文件(返回base64编码)
     *
     *
     * @Date 2018/4/18 13:52
     */
    @GetResource(name = "预览文件base64", path = "/previewBase64")
    public String previewBase64(HttpServletRequest request, HttpServletResponse response) {
        String fileId = request.getParameter("fileId");
        if (ToolUtil.isEmpty(fileId)) {
            throw new RequestEmptyException("文件id为空！");
        }

        //从阿里云读取文件
        Fileinfo fileinfo = fileinfoService.getFileInfo(Long.valueOf(fileId));
        String filePath = fileinfo.getFileStorageName();
        if (ToolUtil.isEmpty(filePath)) {
            throw new ServiceException(FileExceptionEnum.FILE_NOT_FOUND);
        }

        //读取文件流
        try {
            byte[] fileBytes = fileOperator.getFileBytes(filePath);
            return Base64Encoder.encode(fileBytes);
        } catch (Exception e) {
            log.error("读取文件错误！", e);
            throw new ServiceException(FileExceptionEnum.IO_ERROR);
        }
    }

}
