package com.arkr.service.config.dubbo;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

/**
 * 服务提供方
 *
 * @author hztanhuayou
 * @date 2018/2/10
 */
@Configuration
@PropertySource(value = "classpath:config/dubbo.properties")
@Import(DubboCommonConfiguration.class)
class ProviderConfiguration {

    @Bean
    @ConditionalOnMissingBean(ServicePostProcessor.class)
    public ServicePostProcessor serviceConfigPostProcessor() {
        return new ServicePostProcessor();
    }
}
