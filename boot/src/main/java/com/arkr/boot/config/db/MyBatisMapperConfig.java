package com.arkr.boot.config.db;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author hztanhuayou
 * @date 2017/12/4
 */
@Configuration
@AutoConfigureAfter(MyBatisConfig.class)
class MyBatisMapperConfig {
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        mapperScannerConfigurer.setBasePackage("com.arkr.boot.dao");
        return mapperScannerConfigurer;
    }
}
