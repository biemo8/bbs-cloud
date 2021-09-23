package com.biemo.cloud.auth.modular.ssomgr.consumer;

import com.biemo.cloud.kernel.model.api.ResourceService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 资源服务
 *
 *
 * @Date 2019/12/3 22:30
 */
@FeignClient(name = "biemo-system-app",contextId = "ResourceServiceConsumer")
public interface ResourceServiceConsumer extends ResourceService {
}
