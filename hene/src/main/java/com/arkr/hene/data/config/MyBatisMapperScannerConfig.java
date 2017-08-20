package com.arkr.hene.data.config;

import com.arkr.hene.data.dao.DAOInterfaceMaker;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by hztanhuayou on 2017/8/21
 */
@Configuration
@AutoConfigureAfter(MyBatisConfig.class)
public class MyBatisMapperScannerConfig implements InitializingBean {
    private Logger logger = LoggerFactory.getLogger(MyBatisMapperScannerConfig.class);

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        mapperScannerConfigurer.setMarkerInterface(DAOInterfaceMaker.class);
        mapperScannerConfigurer.setBasePackage("com.arkr.hene.data.dao");
        return mapperScannerConfigurer;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info(this.getClass().getSimpleName() + " Succeed");
    }
}
