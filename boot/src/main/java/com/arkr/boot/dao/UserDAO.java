package com.arkr.boot.dao;

import com.arkr.boot.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.cache.annotation.Cacheable;

/**
 * @author hztanhuayou
 * @date 2017/12/21
 */
public interface UserDAO extends DAO {
    String CACHE_NAME = "usr";
    String TABLE_NAME = " `user` ";
    String COLUMN = "`id`,`username`,`password`,`db_update_time`,`db_create_time`";
    String COLUMN_WITHOUT_ID = "(`username`,`password`,`db_update_time`,`db_create_time`)";
    String COLUMN_INSERT_VAL = "(#{username},#{password},#{dbUpdateTime},#{dbCreateTime})";

    /**
     * 插入一条心记录
     *
     * @param user 心记录
     * @return 影响行数
     */
    @Insert(INSERT + INTO + TABLE_NAME + COLUMN_WITHOUT_ID + VALUES + COLUMN_INSERT_VAL)
    int insert(User user);

    /**
     * 根据id获取一个user
     *
     * @param id id
     * @return user
     */
    @Cacheable(value = CACHE_NAME, key = "#root.args[0]")
    User queryById(@Param("id") Long id);

    /**
     * 根据用户名获取一个user
     *
     * @param username username
     * @return user
     */
    @Select(SELECT + COLUMN + FROM + TABLE_NAME + WHERE + "`username`=#{username}")
    User queryByUsername(@Param("username") String username);
}
