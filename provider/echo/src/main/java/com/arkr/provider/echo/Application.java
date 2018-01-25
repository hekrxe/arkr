package com.arkr.provider.echo;

import com.arkr.boot.config.cache.EnableDataCaching;
import com.arkr.boot.config.db.EnableDataSource;
import com.arkr.boot.config.redis.EnableRedisTemplate;
import com.arkr.boot.config.sys.SysConfig;
import com.arkr.boot.config.sys.SysConfigLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author hztanhuayou
 */
@EnableDataSource
@EnableDataCaching
@EnableRedisTemplate
@SpringBootApplication
public class Application {
    private static Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SysConfigLoader.apply(new String[]{"classpath:config/res.properties"}, "echo");

        SpringApplication application = new SpringApplication(Application.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.setWebEnvironment(false);
        application.run(args);
        logger.info("Service echo Started. " + SysConfig.getOrElse("service.env", "") + " " + SysConfig.getString("abc"));
    }
}
