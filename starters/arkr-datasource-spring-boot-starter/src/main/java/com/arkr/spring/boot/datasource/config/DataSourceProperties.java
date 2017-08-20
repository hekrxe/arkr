package com.arkr.spring.boot.datasource.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import static com.arkr.spring.boot.datasource.config.DataSourceProperties.prefix;

/**
 * Created by hztanhuayou on 2017/8/20
 */
@ConfigurationProperties(prefix = prefix)
@Configuration
@PropertySource("classpath:config/db.properties")
public class DataSourceProperties implements EnvironmentAware {
    public static final String prefix = "spring.datasource";

    @Value("${spring.datasource.url}")
    private String url;
    private String username;
    private String password;
    private String driverClassName;
    private Integer maxActive;
    private Integer maxIdle;
    private Long maxWait;
    private String minIdle;
    private String initialSize;


    @Override
    public void setEnvironment(Environment environment) {
        System.out.println("aaaaaaaaaaaa");
    }

    public String getUrl() {
        return url;
    }

    public DataSourceProperties setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public DataSourceProperties setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public DataSourceProperties setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public DataSourceProperties setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
        return this;
    }

    public Integer getMaxActive() {
        return maxActive;
    }

    public DataSourceProperties setMaxActive(Integer maxActive) {
        this.maxActive = maxActive;
        return this;
    }

    public Integer getMaxIdle() {
        return maxIdle;
    }

    public DataSourceProperties setMaxIdle(Integer maxIdle) {
        this.maxIdle = maxIdle;
        return this;
    }

    public Long getMaxWait() {
        return maxWait;
    }

    public DataSourceProperties setMaxWait(Long maxWait) {
        this.maxWait = maxWait;
        return this;
    }

    public String getMinIdle() {
        return minIdle;
    }

    public DataSourceProperties setMinIdle(String minIdle) {
        this.minIdle = minIdle;
        return this;
    }

    public String getInitialSize() {
        return initialSize;
    }

    public DataSourceProperties setInitialSize(String initialSize) {
        this.initialSize = initialSize;
        return this;
    }
}