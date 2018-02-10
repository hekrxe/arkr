package com.arkr.service.config.dubbo;

import com.alibaba.dubbo.config.ConsumerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

/**
 * @author hztanhuayou
 * @date 2018/2/10
 */

@Configuration
@PropertySource(value = "classpath:config/dubbo.properties")
@Import(DubboCommonConfiguration.class)
class ConsumerConfiguration {
    @Value("${dubbo.application.name}")
    private String applicationName;
    @Value("${dubbo.registry.address}")
    private String registerAddress;
    @Value("${dubbo.consumer.timeout}")
    private Integer timeout;

    @Bean
    public ConsumerConfig consumerConfig() {
        ConsumerConfig consumerConfig = new ConsumerConfig();
        consumerConfig.setTimeout(timeout);
        return consumerConfig;
    }

    @Bean
    @ConditionalOnMissingBean(ReferenceBeforeProcessor.class)
    public ReferenceBeforeProcessor referenceBeforeProcessor() {
        return new ReferenceBeforeProcessor();
    }
}
