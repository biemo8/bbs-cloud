package com.biemo.cloud.biz.file.config;

import com.biemo.cloud.biz.file.config.properties.OssProperteis;
import com.biemo.cloud.biz.file.core.db.FileInitializer;
import com.biemo.cloud.biz.file.core.storage.FileOperator;
import com.biemo.cloud.biz.file.core.storage.oss.OssFileOperator;
import com.biemo.cloud.biz.file.modular.controller.FileController;
import com.biemo.cloud.biz.file.modular.controller.FileManagerController;
import com.biemo.cloud.biz.file.modular.provider.FileinfoServiceProvider;
import com.biemo.cloud.biz.file.modular.service.FileinfoService;
import com.biemo.cloud.kernel.model.constants.ConfigPrefixConstants;
import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 字典自动配置
 *
 *
 * @date 2018-07-27-下午1:57
 */
@Configuration
@ComponentScan("com.biemo.cloud.biz.file")
public class FileAutoConfiguration {

    /**
     * 阿里云配置
     *
     *
     * @Date 2018/6/27 下午2:21
     */
    @Bean
    @ConfigurationProperties(prefix = ConfigPrefixConstants.ALIYUN_OSS)
    public OssProperteis ossProperteis() {
        return new OssProperteis();
    }

    /**
     * oss客户端sdk
     *
     *
     * @Date 2018/6/27 下午6:10
     */
    @Bean
    public OSSClient ossClient(OssProperteis ossProperteis) {
        DefaultCredentialProvider defaultCredentialProvider =
                new DefaultCredentialProvider(ossProperteis.getAccessKeyId(), ossProperteis.getAccessKeySecret());
        ClientConfiguration clientConfiguration = new ClientConfiguration();
        return new OSSClient(ossProperteis.getEndpoint(), defaultCredentialProvider, clientConfiguration);
    }

    /**
     * 文件操作工具
     *
     *
     * @Date 2018/6/27 下午2:21
     */
    @Bean
    public FileOperator fileOperator(OSSClient ossClient, OssProperteis ossProperteis) {
        return new OssFileOperator(ossClient, ossProperteis);
    }

    /**
     * 数据库初始化
     */
    @Bean
    public FileInitializer fileInitializer() {
        return new FileInitializer();
    }

    /**
     * 控制器
     */
    @Bean
    public FileController fileController() {
        return new FileController();
    }

    @Bean
    public FileManagerController fileManagerController() {
        return new FileManagerController();
    }

    /**
     * 服务
     */
    @Bean
    public FileinfoService fileinfoService() {
        return new FileinfoService();
    }

    /**
     * 服务提供者
     */
    @Bean
    public FileinfoServiceProvider fileinfoServiceProvider() {
        return new FileinfoServiceProvider();
    }
}
