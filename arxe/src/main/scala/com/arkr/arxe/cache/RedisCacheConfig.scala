package com.arkr.arxe.cache

import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.{CachingConfigurerSupport, EnableCaching}
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.{RedisTemplate, StringRedisTemplate}
import org.springframework.data.redis.serializer.{RedisSerializer, SerializationException}

/**
  * Created by hztanhuayou on 2017/8/19
  */
@Configuration
@EnableCaching
class RedisCacheConfig extends CachingConfigurerSupport {
  @Value("${redis.cache.cache.expiration}") private var redisCacheExpiration: Long = _

  @Bean(value = Array("redisTemplate"))
  def redisTemplate(redisConnectionFactory: RedisConnectionFactory): RedisTemplate[_, _] = {
    val template = new StringRedisTemplate(redisConnectionFactory)
    template.setValueSerializer(new MyGenericJackson2JsonRedisSerializer)
    template.setKeySerializer(new RedisSerializer[Object]() {
      @throws[SerializationException]
      override def serialize(o: Object): Array[Byte] = if (o == null) "nil".getBytes else o.toString.getBytes

      @throws[SerializationException]
      override def deserialize(bytes: Array[Byte]): Object = if (null == bytes) null else new String(bytes)
    })
    template.afterPropertiesSet()
    template
  }

  @Bean
  def cacheManager(redisTemplate: RedisTemplate[_, _]): CacheManager = {
    val manager = new RedisCacheManager(redisTemplate)
    manager.setDefaultExpiration(redisCacheExpiration)
    manager.setUsePrefix(true)
    manager
  }
}
