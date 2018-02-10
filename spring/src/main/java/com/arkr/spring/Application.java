package com.arkr.spring;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * @author hztanhuayou
 * @date 2018/2/10
 */

@SpringBootApplication
public class Application implements InitializingBean {
    @Autowired
    private ApplicationContext context;


    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Application.class);
        application.run(args);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Object annoA = context.getBean("annoA");
        System.out.println(annoA);
        Object annoB = context.getBean("annoB");
        System.out.println(annoB);
    }
}
