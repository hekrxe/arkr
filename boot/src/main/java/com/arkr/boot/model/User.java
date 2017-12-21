package com.arkr.boot.model;

import java.sql.Timestamp;

/**
 * @author hztanhuayou
 * @date 2017/12/21
 */
public class User {
    private Long id;
    private String username;
    private String password;
    private Timestamp dbCreateTime;
    private Timestamp dbUpdateTime;

    public Long getId() {
        return id;
    }

    public User setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public Timestamp getDbCreateTime() {
        return dbCreateTime;
    }

    public User setDbCreateTime(Timestamp dbCreateTime) {
        this.dbCreateTime = dbCreateTime;
        return this;
    }

    public Timestamp getDbUpdateTime() {
        return dbUpdateTime;
    }

    public User setDbUpdateTime(Timestamp dbUpdateTime) {
        this.dbUpdateTime = dbUpdateTime;
        return this;
    }
}
