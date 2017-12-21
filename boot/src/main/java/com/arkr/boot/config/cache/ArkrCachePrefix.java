package com.arkr.boot.config.cache;

import org.springframework.data.redis.cache.DefaultRedisCachePrefix;

/**
 * db cache前缀,用于做版本等
 *
 * @author hztanhuayou
 * @date 2017/12/7
 */
final class ArkrCachePrefix extends DefaultRedisCachePrefix {
    private static final String DELIMITER = ":";
    private static final String ARKR_ONLY = "arkr";
    private final String version;

    ArkrCachePrefix(int version) {
        this.version = String.valueOf(version);
    }

    @Override
    public byte[] prefix(String cacheName) {
        cacheName = ARKR_ONLY.concat(DELIMITER).concat(cacheName).concat(DELIMITER).concat(version);
        return super.prefix(cacheName);
    }
}
