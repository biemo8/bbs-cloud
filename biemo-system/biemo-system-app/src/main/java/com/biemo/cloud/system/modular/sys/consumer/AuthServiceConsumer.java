package com.biemo.cloud.system.modular.sys.consumer;

import com.biemo.cloud.auth.api.AuthService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RestController;

/**
 * 鉴权消费者
 *
 *
 * @Date 2019-08-12 18:52
 */
@FeignClient(name = "biemo-auth-app",contextId = "AuthServiceConsumer")
public interface AuthServiceConsumer extends AuthService {

}
