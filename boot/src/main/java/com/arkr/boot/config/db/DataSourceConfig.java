package com.arkr.boot.config.db;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

/**
 * @author hztanhuayou
 * @date 2017/12/4
 */
@Configuration
@PropertySource(value = "classpath:config/db.properties")
class DataSourceConfig {
    private Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);

    @Value("${jdbc.url}")
    private String dbUrl;
    @Value("${jdbc.username}")
    private String username;
    @Value("${jdbc.password}")
    private String password;
    @Value("${jdbc.driver}")
    private String driverClassName;
    @Value("${dbcp2.initial.size}")
    private int initialSize;
    @Value("${dbcp2.min.idle}")
    private int minIdle;
    @Value("${dbcp2.max.idle}")
    private int maxIdle;
    @Value("${dbcp2.max.total}")
    private int maxTotal;

    @Value("${dbcp2.default.auto.commit}")
    private boolean defaultAutoCommit;
    @Value("${dbcp2.time.between.eviction.runs.millis}")
    private long timeBetweenEvictionRunsMillis;
    @Value("${dbcp2.min.evictable.idle.time.millis}")
    private long minEvictableIdleTimeMillis;
    @Value("${dbcp2.validation.query}")
    private String validationQuery;
    @Value("${dbcp2.test.on.borrow}")
    private boolean testOnBorrow;
    @Value("${dbcp2.test.while.idle}")
    private boolean testWhileIdle;
    @Value("${dbcp2.test.while.return}")
    private boolean testWhileReturn;
    @Value("${dbcp2.pool.prepared.statements:true}")
    private boolean poolPreparedStatements;
    @Value("${dbcp2.max.open.prepared.statements:50}")
    int maxOpenPreparedStatements;
    @Value("${dbcp2.remove.abandoned.timeout:180}")
    int removeAbandonedTimeout;

    @Bean
    @Primary
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(dbUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setInitialSize(initialSize);
        dataSource.setMaxTotal(maxTotal);
        dataSource.setMaxIdle(maxIdle);
        dataSource.setMinIdle(minIdle);
        dataSource.setDefaultAutoCommit(defaultAutoCommit);
        dataSource.setPoolPreparedStatements(poolPreparedStatements);
        dataSource.setMaxOpenPreparedStatements(maxOpenPreparedStatements);
        dataSource.setRemoveAbandonedTimeout(removeAbandonedTimeout);
        dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        dataSource.setValidationQuery(validationQuery);
        dataSource.setTestOnBorrow(testOnBorrow);
        dataSource.setTestWhileIdle(testWhileIdle);
        dataSource.setTestOnReturn(testWhileReturn);
        logger.info("dataSource DataSource Config Succeed");
        return dataSource;
    }

}
