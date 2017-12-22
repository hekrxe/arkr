package com.arkr.boot.config.cache;

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
import org.springframework.data.redis.cache.RedisCachePrefix;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import redis.clients.jedis.JedisPoolConfig;

import java.nio.charset.Charset;

/**
 * @author hztanhuayou
 * @date 2017/12/5
 */
@Configuration
@EnableCaching
@PropertySource(value = "classpath:config/db-cache.properties")
class RedisCacheConfig implements InitializingBean {
    private Logger logger = LoggerFactory.getLogger(RedisCacheConfig.class);

    @Value("${redis.cache.maxIdle}")
    private Integer maxIdle;
    @Value("${redis.cache.maxTotal}")
    private Integer maxTotal;
    @Value("${redis.cache.minIdle}")
    private Integer minIdle;
    @Value("${redis.cache.maxWaitMills}")
    private Long maxWaitMills;

    @Value("${redis.cache.hostname}")
    private String hostname;
    @Value("${redis.cache.port}")
    private Integer port;
    @Value("${redis.cache.password}")
    private String password;
    @Value("${redis.cache.timeout}")
    private Integer timeout;

    @Value("${redis.cache.expiration}")
    private Long expiration;

    @Value("${redis.cache.version}")
    private Integer cacheVersion;

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
        factory.setDatabase(0);
        factory.setHostName(hostname);
        factory.setPort(port);
        factory.setPassword(password);
        factory.setTimeout(timeout);
        factory.setPoolConfig(jedisPoolConfig());
        factory.setUsePool(true);
        factory.afterPropertiesSet();

        return factory;
    }

    @Bean("dbRedisTemplate")
    public RedisTemplate dbRedisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        GenericJackson2JsonRedisSerializer defaultSerializer = new GenericJackson2JsonRedisSerializer();

        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        redisTemplate.setDefaultSerializer(dbCacheKeySerializer());
        redisTemplate.setKeySerializer(dbCacheKeySerializer());
        redisTemplate.setHashKeySerializer(dbCacheKeySerializer());
        redisTemplate.setValueSerializer(defaultSerializer);
        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }

    @Bean
    public RedisCachePrefix redisCachePrefix() {
        return new ArkrCachePrefix(cacheVersion);
    }

    @Bean("cacheManager")
    public CacheManager cacheManager() {
        RedisCacheManager manager = new RedisCacheManager(dbRedisTemplate());
        manager.setDefaultExpiration(expiration);
        manager.setUsePrefix(true);
        manager.setCachePrefix(redisCachePrefix());
        manager.setTransactionAware(true);
        return manager;
    }

    @Bean
    public RedisSerializer dbCacheKeySerializer() {
        return new RedisSerializer<Object>() {
            private final Charset charset = Charset.forName("UTF-8");

            @Override
            public byte[] serialize(Object o) throws SerializationException {
                return o == null ? "nil".getBytes() : o.toString().getBytes(charset);
            }

            @Override
            public Object deserialize(byte[] bytes) throws SerializationException {
                return bytes == null ? null : new String(bytes, charset);
            }
        };
    }

    @Override
    public void afterPropertiesSet() {
        logger.info("RedisCacheConfig Config Succeed");
    }
}
