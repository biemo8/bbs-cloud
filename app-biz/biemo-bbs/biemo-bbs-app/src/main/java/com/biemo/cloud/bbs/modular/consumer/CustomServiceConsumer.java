package com.biemo.cloud.bbs.modular.consumer;

import com.biemo.cloud.system.api.ResourceServiceApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 资源服务
 *
 *
 * @Date 2019/12/4 21:10
 */
@FeignClient(name = "biemo-system-app",contextId = "BbsCustomServiceConsumer")
public interface CustomServiceConsumer extends ResourceServiceApi {
}
