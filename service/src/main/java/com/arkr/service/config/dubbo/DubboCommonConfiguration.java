package com.arkr.service.config.dubbo;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.IOException;

/**
 * @author hztanhuayou
 * @date 2018/2/10
 */
@Configuration
@PropertySource(value = "classpath:config/dubbo.properties")
class DubboCommonConfiguration {
    /**
     * 注意! 使用 application.[properties,yml...]里定义的值
     */
    @Value("${dubbo.application.name}")
    private String applicationName;

    @Value("${dubbo.registry.address}")
    private String registerAddress;

    @Bean
    @ConditionalOnMissingBean(ApplicationConfig.class)
    public ApplicationConfig applicationConfig() {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName(applicationName);
        return applicationConfig;
    }

    @Bean
    @ConditionalOnMissingBean(RegistryConfig.class)
    public RegistryConfig registryConfig() {
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress(registerAddress);
        registryConfig.setClient("curator");
        registryConfig.setPort(30001);
        return registryConfig;
    }

    @Bean
    @ConditionalOnMissingBean(DubboVersionSupport.class)
    public DubboVersionSupport dubboVersionSupport() throws IOException {
        return new DubboVersionSupport("classpath:config/dubbo.version.properties");
    }
}
