package com.arkr.hene.data.config;

import com.arkr.hene.data.dao.DAOInterfaceMaker;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by hztanhuayou on 2017/8/21
 */
@Configuration
@AutoConfigureAfter(MyBatisConfig.class)
public class MyBatisMapperScannerConfig {

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        mapperScannerConfigurer.setMarkerInterface(DAOInterfaceMaker.class);
        mapperScannerConfigurer.setBasePackage("com.arkr.hene.data.dao");
        return mapperScannerConfigurer;
    }
}
