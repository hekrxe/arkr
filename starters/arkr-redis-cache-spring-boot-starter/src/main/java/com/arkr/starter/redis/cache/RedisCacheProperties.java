package com.arkr.starter.redis.cache;

import org.springframework.boot.context.properties.ConfigurationProperties;

import static com.arkr.starter.redis.cache.RedisCacheProperties.prefix;

/**
 * Created by hztanhuayou on 2017/8/20
 */
@ConfigurationProperties(prefix = prefix)
public class RedisCacheProperties {
    public static final String prefix = "starter.redis.cache";

    // config
    private Integer maxIdle;
    private Integer maxTotal;
    private Integer minIdle;
    private Long maxWaitMills;

    // connection
    private String hostname;
    private Integer port;
    private String password;
    private Integer timeout;

    // cache
    private Long expiration;

    public RedisCacheProperties() {

    }

    public Integer getMaxIdle() {
        return maxIdle;
    }

    public RedisCacheProperties setMaxIdle(Integer maxIdle) {
        this.maxIdle = maxIdle;
        return this;
    }

    public Integer getMaxTotal() {
        return maxTotal;
    }

    public RedisCacheProperties setMaxTotal(Integer maxTotal) {
        this.maxTotal = maxTotal;
        return this;
    }

    public Long getMaxWaitMills() {
        return maxWaitMills;
    }

    public RedisCacheProperties setMaxWaitMills(Long maxWaitMills) {
        this.maxWaitMills = maxWaitMills;
        return this;
    }

    public String getHostname() {
        return hostname;
    }

    public RedisCacheProperties setHostname(String hostname) {
        this.hostname = hostname;
        return this;
    }

    public Integer getPort() {
        return port;
    }

    public RedisCacheProperties setPort(Integer port) {
        this.port = port;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public RedisCacheProperties setPassword(String password) {
        this.password = password;
        return this;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public RedisCacheProperties setTimeout(Integer timeout) {
        this.timeout = timeout;
        return this;
    }

    public Long getExpiration() {
        return expiration;
    }

    public RedisCacheProperties setExpiration(Long expiration) {
        this.expiration = expiration;
        return this;
    }

    public Integer getMinIdle() {
        return minIdle;
    }

    public RedisCacheProperties setMinIdle(Integer minIdle) {
        this.minIdle = minIdle;
        return this;
    }
}
