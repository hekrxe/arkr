package com.arkr.xehe.service.hexe;

import com.arkr.hekr.model.User;

/**
 * Created by hztanhuayou on 2017/8/27
 */
public interface MyEchoService {
    String echo(String str);

    User queryById(Long id);
}
