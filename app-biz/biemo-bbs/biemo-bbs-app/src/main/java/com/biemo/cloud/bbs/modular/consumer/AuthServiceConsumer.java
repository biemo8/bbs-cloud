package com.biemo.cloud.bbs.modular.consumer;

import com.biemo.cloud.auth.api.AuthService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 鉴权消费者
 *
 *
 * @Date 2019-08-12 18:52
 */
@FeignClient(name = "biemo-auth-app",contextId = "BbsAuthServiceConsumer")
public interface AuthServiceConsumer extends AuthService {

}
