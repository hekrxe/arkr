package com.arkr.near;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

/**
 * Created by hztanhuayou on 2017/8/16
 */
public interface DemoService {

    @CacheEvict(value = "demo", key = "#root.args[0].id")
    int save(Demo demo);

    @Cacheable(value = "demo", key = "#root.args[0]")
    Demo getById(Long id);

    @CacheEvict(value = "demoex", key = "#root.args[0].id")
    int saveEx(Demo demo);

    @Cacheable(value = "demoex", key = "#root.args[0]")
    Demo getByIdEx(Long id);
}
