package com.arkr.hene.data.model;

import java.sql.Date;

/**
 * Created by hztanhuayou on 2017/8/21
 */
public class User extends ModelInterfaceMaker {
    private Long id;
    private String username;
    private Long age;
    private Date ctm;

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

    public Long getAge() {
        return age;
    }

    public User setAge(Long age) {
        this.age = age;
        return this;
    }

    public Date getCtm() {
        return ctm;
    }

    public User setCtm(Date ctm) {
        this.ctm = ctm;
        return this;
    }
}
