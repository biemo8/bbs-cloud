package com.biemo.cloud.biz.file.api.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 文件下载内容
 *
 *
 * @date 2018-07-27-下午3:54
 */
@Getter
@Setter
public class FileByteInfo {

    /**
     * 文件字节
     */
    private byte[] fileBytes;

}
