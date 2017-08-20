package com.arkr.spring.boot.datasource.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Created by hztanhuayou on 2017/8/20
 */
@Configuration
@ConditionalOnClass({DataSource.class})
@ConditionalOnMissingBean(value = DataSource.class)
@EnableConfigurationProperties(DataSourceProperties.class)
@ConditionalOnProperty(prefix = DataSourceProperties.prefix, value = "enabled", matchIfMissing = true)
public class DataSourceAutoConfiguration {

    private final DataSourceProperties properties;

    @Autowired
    public DataSourceAutoConfiguration(DataSourceProperties properties) {
        this.properties = properties;
    }

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(properties.getDriverClassName());
        dataSource.setUrl(properties.getUrl());
        dataSource.setUsername(properties.getUsername());
        dataSource.setPassword(properties.getPassword());
        dataSource.setMaxActive(properties.getMaxActive());
        dataSource.setMaxIdle(properties.getMaxIdle());
        dataSource.setMaxWait(properties.getMaxWait());
        dataSource.setValidationQuery("SELECT 1");
        dataSource.setTestOnBorrow(true);
        return dataSource;
    }
}
