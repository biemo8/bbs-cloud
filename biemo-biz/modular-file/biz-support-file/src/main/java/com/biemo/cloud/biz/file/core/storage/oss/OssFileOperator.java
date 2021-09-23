package com.biemo.cloud.biz.file.core.storage.oss;

import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.IoUtil;
import com.biemo.cloud.biz.file.config.properties.OssProperteis;
import com.biemo.cloud.biz.file.core.exceptions.FileExceptionEnum;
import com.biemo.cloud.biz.file.core.storage.FileOperator;
import com.biemo.cloud.core.util.ToolUtil;
import com.biemo.cloud.kernel.model.exception.ServiceException;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.CopyObjectRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

/**
 * 阿里云oss操作封装
 *
 *
 * @date 2018-06-27-下午12:31
 */
@Slf4j
public class OssFileOperator implements FileOperator {

    private OssProperteis ossProperteis;

    private OSSClient ossClient;

    public OssFileOperator(OSSClient ossClient, OssProperteis ossProperteis) {
        this.ossClient = ossClient;
        this.ossProperteis = ossProperteis;
    }

    @Override
    public boolean isExistingFile(String fileName) {
        try {
            ossClient.getObject(this.ossProperteis.getBucketName(), fileName);
        } catch (OSSException | ClientException e) {
            log.info("阿里云上找不到该文件！文件名：" + fileName);
            return false;
        }
        return true;
    }

    @Override
    public byte[] getFileBytes(String fileName) {
        OSSObject object = null;
        try {
            object = ossClient.getObject(this.ossProperteis.getBucketName(), fileName);
        } catch (OSSException | ClientException e) {
            log.info("阿里云上找不到该文件！文件名：" + fileName);
            throw new ServiceException(FileExceptionEnum.FILE_NOT_FOUND);
        }

        InputStream objectContent = null;
        try {
            objectContent = object.getObjectContent();
            return IoUtil.readBytes(objectContent);
        } catch (IORuntimeException e) {
            log.error("流读取错误！", e);
            throw new ServiceException(FileExceptionEnum.IO_ERROR);
        } finally {
            IoUtil.close(objectContent);
        }
    }

    @Override
    public void storageFile(String fileName, InputStream inputStream) {
        ossClient.putObject(this.ossProperteis.getBucketName(), fileName, inputStream);
    }

    @Override
    public void storageFile(String fileName, byte[] bytes) {
        ossClient.putObject(this.ossProperteis.getBucketName(), fileName, new ByteArrayInputStream(bytes));
    }

    @Override
    public boolean copyFile(String sourceFileFinalName, String newFileFinalName) {
        CopyObjectRequest copyObjectFile =
                new CopyObjectRequest(this.ossProperteis.getBucketName(), sourceFileFinalName, this.ossProperteis.getBucketName(), newFileFinalName);
        ObjectMetadata meta = new ObjectMetadata();
        copyObjectFile.setNewObjectMetadata(meta);

        try {
            ossClient.copyObject(copyObjectFile);
        } catch (Exception e) {
            log.error("oss拷贝文件错误！", e);
            return false;
        }
        return true;
    }

    @Override
    public String getFileAuthUrl(String fileFinalName) {

        if (ToolUtil.isEmpty(fileFinalName)) {
            return null;
        }

        // 设置URL过期时间为1小时。
        Date expiration = new Date(System.currentTimeMillis() + ossProperteis.getExpiredMinutes() * 1000);

        // 生成以GET方法访问的签名URL，访客可以直接通过浏览器访问相关内容。
        URL url = ossClient.generatePresignedUrl(ossProperteis.getBucketName(), fileFinalName, expiration);

        return url.toString();
    }

}
