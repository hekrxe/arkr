package com.arkr.near;

import java.io.Serializable;

/**
 * Created by hztanhuayou on 2017/8/16
 */
public class Demo implements Serializable, Cloneable {
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public Demo setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Demo setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    protected Demo clone() throws CloneNotSupportedException {
        return new Demo().setId(id).setName(name);
    }
}
