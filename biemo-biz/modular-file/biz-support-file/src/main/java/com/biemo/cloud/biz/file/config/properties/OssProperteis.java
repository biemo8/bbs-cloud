package com.biemo.cloud.biz.file.config.properties;

import lombok.Data;

/**
 * oss相关配置
 *
 *
 * @date 2018-06-27-下午1:20
 */
@Data
public class OssProperteis {

    /**
     * oss bucket 名称
     */
    private String bucketName;

    /**
     * accessKeyId
     */
    private String accessKeyId;

    /**
     * accessKeySecret
     */
    private String accessKeySecret;

    /**
     * oss sdk配置的链接地址(例如：http://oss-cn-beijing.aliyuncs.com)
     */
    private String endpoint;

    /**
     * 阿里云文件访问地址（例如：https://xxx-xxx.oss-cn-beijing.aliyuncs.com/）
     */
    private String internetFileUrl;

    /**
     * 文件默认过期时间（分钟）
     */
    private Integer expiredMinutes;
}
