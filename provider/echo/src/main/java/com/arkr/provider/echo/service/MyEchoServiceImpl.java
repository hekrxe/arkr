package com.arkr.provider.echo.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.arkr.service.echo.MyEchoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author hztanhuayou
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
