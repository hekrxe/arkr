package com.arkr.hene.data.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by hztanhuayou on 2017/8/21
 */

@Configuration
@EnableCaching
@PropertySource(value = "classpath:config/${spring.profiles.active}/db-cache.properties")
public class DataCachingConfig implements InitializingBean {
    private Logger logger = LoggerFactory.getLogger(DataCachingConfig.class);

    // config
    @Value("${redis.cache.maxIdle}")
    private Integer maxIdle;
    @Value("${redis.cache.maxTotal}")
    private Integer maxTotal;
    @Value("${redis.cache.minIdle}")
    private Integer minIdle;
    @Value("${redis.cache.maxWaitMills}")
    private Long maxWaitMills;

    // connection
    @Value("${redis.cache.hostname}")
    private String hostname;
    @Value("${redis.cache.port}")
    private Integer port;
    @Value("${redis.cache.password}")
    private String password;
    @Value("${redis.cache.timeout}")
    private Integer timeout;

    // cache
    @Value("${redis.cache.expiration}")
    private Long expiration;

    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(maxIdle);
        config.setMaxTotal(maxTotal);
        config.setMaxWaitMillis(maxWaitMills);
        config.setMinIdle(minIdle);
        return config;
    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setHostName(hostname);
        factory.setPort(port);
        factory.setPassword(password);
        factory.setTimeout(timeout);
        factory.setPoolConfig(jedisPoolConfig()); // 这样安全点
        return factory;
    }

    @Bean("redisTemplate")
    public RedisTemplate redisTemplate() {
        StringRedisTemplate template = new StringRedisTemplate(jedisConnectionFactory());
        GenericJackson2JsonRedisSerializer redisValueSerializer = new GenericJackson2JsonRedisSerializer();
        template.setValueSerializer(redisValueSerializer);
        template.setKeySerializer(new RedisSerializer<Object>() {
            @Override
            public byte[] serialize(Object o) throws SerializationException {
                return o == null ? "nil".getBytes() : o.toString().getBytes();
            }

            @Override
            public Object deserialize(byte[] bytes) throws SerializationException {
                return bytes == null ? null : new String(bytes);
            }
        });
        template.afterPropertiesSet();
        return template;
    }

    @Bean("cacheManager")
    public CacheManager cacheManager() {
        RedisCacheManager manager = new RedisCacheManager(redisTemplate());
        manager.setDefaultExpiration(expiration);
        manager.setUsePrefix(true);
        return manager;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("DataCaching Config Succeed");
    }
}
