package com.biemo.cloud.libs.config;

import com.biemo.cloud.kernel.logger.chain.aop.ChainOnConsumerAop;
import com.biemo.cloud.kernel.logger.chain.aop.ChainOnControllerAop;
import com.biemo.cloud.kernel.logger.chain.aop.ChainOnProviderAop;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * 调用链日志记录的aop
 *
 *
 * @date 2018-08-04-下午5:47
 */
@Configuration
public class ChainConfig {

    /**
     * 调用链治理
     */
    @Bean
    public ChainOnConsumerAop chainOnConsumerAop() {
        return new ChainOnConsumerAop();
    }

    /**
     * 调用链治理
     */
    @Bean
    public ChainOnControllerAop chainOnControllerAop() {
        return new ChainOnControllerAop();
    }

    /**
     * 调用链治理
     */
    @Bean
    public ChainOnProviderAop chainOnProviderAop() {
        return new ChainOnProviderAop();
    }

}
