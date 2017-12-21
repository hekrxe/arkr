package com.arkr.hexe.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.arkr.xehe.service.hexe.MyEchoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by hztanhuayou on 2017/8/27
 */
@Service(version = "1.0.0")
public class MyEchoServiceImpl implements MyEchoService {
    private Logger logger = LoggerFactory.getLogger(MyEchoServiceImpl.class);


    @Override
    public String echo(String str) {
        logger.info("receive msg: " + str);
        return str;
    }
}
