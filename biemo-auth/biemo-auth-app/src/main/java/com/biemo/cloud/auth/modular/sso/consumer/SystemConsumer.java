package com.biemo.cloud.auth.modular.sso.consumer;

import com.biemo.cloud.system.api.BaseSystemApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 系统管理消费者
 *
 *
 * @Date 2019/9/25 18:32
 */
@FeignClient(name = "biemo-system-app",contextId = "SystemConsumer")
public interface SystemConsumer extends BaseSystemApi {
}
