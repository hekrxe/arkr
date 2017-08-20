package com.arkr.hene.data.dao;

import com.arkr.hene.data.model.User;

/**
 * Created by hztanhuayou on 2017/8/21
 */
public interface UserDAO {

    int insert(User user);

    User queryById(Long id);
}
