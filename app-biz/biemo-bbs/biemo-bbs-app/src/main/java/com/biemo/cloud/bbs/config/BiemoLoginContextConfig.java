package com.biemo.cloud.bbs.config;

import com.biemo.cloud.bbs.modular.context.BiemoLoginContext;
import com.biemo.cloud.bbs.modular.service.IBUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BiemoLoginContextConfig {

    @Bean
    public BiemoLoginContext biemoLoginContext(IBUserService ibUserService){
        return new BiemoLoginContext(ibUserService);
    }
}
