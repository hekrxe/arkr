package com.arkr.arxe.cache;

import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

/**
 * Created by hztanhuayou on 2017/8/19
 */
class MyGenericJackson2JsonRedisSerializer extends GenericJackson2JsonRedisSerializer {
    // 在scala 源文件里直接使用 GenericJackson2JsonRedisSerializer 会导致编译不通过
    // 不知道为啥...
}
