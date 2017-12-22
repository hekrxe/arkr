package com.arkr.boot.config.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import redis.clients.jedis.JedisPoolConfig;

import java.nio.charset.Charset;

/**
 * 用于业务
 *
 * @author hztanhuayou
 * @date 2017/12/5
 */
@Configuration
@EnableCaching
@EnableAutoConfiguration(exclude = {RedisAutoConfiguration.class})
@PropertySource(value = "classpath:config/redis.properties")
class RedisTemplateConfig {
    @Value("${redis.maxIdle}")
    private Integer maxIdle;
    @Value("${redis.maxTotal}")
    private Integer maxTotal;
    @Value("${redis.minIdle}")
    private Integer minIdle;
    @Value("${redis.maxWaitMills}")
    private Long maxWaitMills;

    @Value("${redis.hostname}")
    private String hostname;
    @Value("${redis.port}")
    private Integer port;
    @Value("${redis.password}")
    private String password;
    @Value("${redis.timeout}")
    private Integer timeout;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(maxIdle);
        config.setMaxTotal(maxTotal);
        config.setMinIdle(minIdle);
        config.setMaxWaitMillis(maxWaitMills);

        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setHostName(hostname);
        factory.setPort(port);
        factory.setPassword(password);
        factory.setTimeout(timeout);
        factory.setPoolConfig(config);
        factory.setUsePool(true);

        factory.afterPropertiesSet();
        return factory;
    }


    @Bean("stringRedisTemplate")
    public StringRedisTemplate stringRedisTemplate() {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate(redisConnectionFactory());
        stringRedisTemplate.setDefaultSerializer(keyStringRedisSerializer());
        return stringRedisTemplate;
    }

    @Bean("numberRedisTemplate")
    public RedisTemplate<String, Long> numberRedisTemplate() {
        RedisTemplate<String, Long> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());

        template.setKeySerializer(keyStringRedisSerializer());
        template.setValueSerializer(new GenericToStringSerializer<>(Long.class));
        template.setHashValueSerializer(new GenericToStringSerializer<>(Long.class));

        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public RedisSerializer<Object> keyStringRedisSerializer() {
        return new RedisSerializer<Object>() {
            private Charset charset = Charset.forName("UTF-8");

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

}
