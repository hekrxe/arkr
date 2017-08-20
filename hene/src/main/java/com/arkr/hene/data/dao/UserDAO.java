package com.arkr.hene.data.dao;

import com.arkr.hene.data.model.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.annotation.Persistent;

/**
 * Created by hztanhuayou on 2017/8/21
 */
@Persistent
public interface UserDAO extends DAOInterfaceMaker{
    String CACHE_NAME = "c.usr.d";

    int insert(User user);

    @Cacheable(cacheNames = CACHE_NAME, key = "#root.args[0]")
    User queryById(Long id);
}
