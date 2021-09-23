package com.biemo.cloud.biz.file.modular.factory;

import com.biemo.cloud.biz.file.api.entity.Fileinfo;
import com.biemo.cloud.biz.file.config.properties.OssProperteis;
import com.biemo.cloud.biz.file.core.util.FileUtil;
import com.biemo.cloud.core.util.SpringContextHolder;
import com.biemo.cloud.core.util.ToolUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 文件信息组装工厂
 *
 *
 * @date 2018-07-27-下午3:12
 */
@Slf4j
public class FileFactory {

    public static Fileinfo getFileInfo(String appCode, String fileOriginName, Long fileSize, Long fileId, String fileStorageName) {
        if (ToolUtil.isEmpty(appCode)) {
            appCode = "noneAppCode";
        }

        Fileinfo fileinfo = new Fileinfo();
        fileinfo.setFileId(fileId);
        fileinfo.setAppCode(appCode);
        fileinfo.setFileOriginName(fileOriginName);
        fileinfo.setFileSuffix(FileUtil.getFileSuffix(fileOriginName));
        fileinfo.setFileSize(fileSize);
        fileinfo.setFileStorageName(fileStorageName);

        try {
            OssProperteis ossProperteis = SpringContextHolder.getBean(OssProperteis.class);
            fileinfo.setFileUrl(ossProperteis.getInternetFileUrl() + fileStorageName);
        } catch (Exception e) {
            log.error("获取ossProperties失败！存储文件地址为；" + fileStorageName);
            fileinfo.setFileUrl(fileStorageName);
        }

        return fileinfo;
    }
}
