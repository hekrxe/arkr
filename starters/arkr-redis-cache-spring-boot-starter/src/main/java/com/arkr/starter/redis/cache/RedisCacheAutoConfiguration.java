package com.arkr.starter.redis.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by hztanhuayou on 2017/8/20
 */
@Configuration
@EnableConfigurationProperties(RedisCacheProperties.class)
@ConditionalOnClass(value = {JedisPoolConfig.class, JedisConnectionFactory.class, RedisTemplate.class, CacheManager.class})
@ConditionalOnProperty(prefix = RedisCacheProperties.prefix, value = "enabled", matchIfMissing = true)
@EnableCaching
public class RedisCacheAutoConfiguration {

    private final RedisCacheProperties redisCacheProperties;

    @Autowired
    public RedisCacheAutoConfiguration(RedisCacheProperties redisCacheProperties) {
        this.redisCacheProperties = redisCacheProperties;
    }

    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(redisCacheProperties.getMaxIdle());
        config.setMaxTotal(redisCacheProperties.getMaxTotal());
        config.setMaxWaitMillis(redisCacheProperties.getMaxWaitMills());
        config.setMinIdle(redisCacheProperties.getMinIdle());
        return config;
    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setHostName(redisCacheProperties.getHostname());
        factory.setPort(redisCacheProperties.getPort());
        factory.setPassword(redisCacheProperties.getPassword());
        factory.setTimeout(redisCacheProperties.getTimeout());
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
        manager.setDefaultExpiration(redisCacheProperties.getExpiration());
        manager.setUsePrefix(true);
        return manager;
    }

}
