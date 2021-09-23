package com.biemo.cloud.bbs.modular.consumer;

import com.biemo.cloud.system.api.SystemAppApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 系统应用app消费者
 *
 *
 * @Date 2019/12/4 20:30
 */
@FeignClient(name = "biemo-system-app",contextId ="SystemAppConsumer")
public interface SystemAppConsumer extends SystemAppApi {
}
