package com.arkr.hexe.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.arkr.hekr.model.User;
import com.arkr.hene.data.dao.UserDAO;
import com.arkr.xehe.service.hexe.MyEchoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by hztanhuayou on 2017/8/27
 */
@Service(version = "1.0.0")
public class MyEchoServiceImpl implements MyEchoService {
    private Logger logger = LoggerFactory.getLogger(MyEchoServiceImpl.class);

    @Autowired
    private UserDAO userDAO;

    @Override
    public String echo(String str) {
        logger.info("receive msg: " + str);
        return str;
    }

    @Override
    public User queryById(Long id) {
        User user = userDAO.queryById(id);
        logger.info("user info: " + JSON.toJSONString(user));
        return user;
    }
}
