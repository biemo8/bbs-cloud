package com.biemo.cloud.system.modular.ent.consumer;

import com.biemo.cloud.biz.file.api.FileApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 鉴权消费者
 *
 *
 * @Date 2019-08-12 18:52
 */
@FeignClient(name = "biemo-cloud-file",contextId = "FileConsumer")
public interface FileConsumer extends FileApi {

}
