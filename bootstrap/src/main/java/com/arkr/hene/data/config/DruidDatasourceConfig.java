package com.arkr.hene.data.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Created by hztanhuayou on 2017/8/26
 */
@Configuration
@EnableTransactionManagement
@PropertySource(value = "classpath:config/${spring.profiles.active}/db.properties")
public class DruidDatasourceConfig implements TransactionManagementConfigurer {
    private Logger logger = LoggerFactory.getLogger(DruidDatasourceConfig.class);

    @Value("${druid.ds.url}")
    private String dbUrl;
    @Value("${druid.ds.username}")
    private String username;
    @Value("${druid.ds.password}")
    private String password;
    @Value("${druid.ds.driverClassName}")
    private String driverClassName;
    @Value("${druid.ds.initialSize}")
    private int initialSize;
    @Value("${druid.ds.minIdle}")
    private int minIdle;
    @Value("${druid.ds.maxActive}")
    private int maxActive;
    @Value("${druid.ds.maxWait}")
    private int maxWait;
    @Value("${druid.ds.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;
    @Value("${druid.ds.minEvictableIdleTimeMillis}")
    private int minEvictableIdleTimeMillis;
    @Value("${druid.ds.validationQuery}")
    private String validationQuery;
    @Value("${druid.ds.testWhileIdle}")
    private boolean testWhileIdle;
    @Value("${druid.ds.testOnBorrow}")
    private boolean testOnBorrow;
    @Value("${druid.ds.testOnReturn}")
    private boolean testOnReturn;
    @Value("${druid.ds.poolPreparedStatements}")
    private boolean poolPreparedStatements;
    @Value("${druid.ds.maxPoolPreparedStatementPerConnectionSize}")
    private int maxPoolPreparedStatementPerConnectionSize;
    @Value("${druid.ds.filters}")
    private String filters;
    @Value("{druid.ds.connectionProperties}")
    private String connectionProperties;

    @Bean
    @Primary
    public DataSource dataSource() {
        DruidDataSource datasource = new DruidDataSource();

        datasource.setUrl(this.dbUrl);
        datasource.setUsername(username);
        datasource.setPassword(password);
        datasource.setDriverClassName(driverClassName);

        //configuration
        datasource.setInitialSize(initialSize);
        datasource.setMinIdle(minIdle);
        datasource.setMaxActive(maxActive);
        datasource.setMaxWait(maxWait);
        datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        datasource.setValidationQuery(validationQuery);
        datasource.setTestWhileIdle(testWhileIdle);
        datasource.setTestOnBorrow(testOnBorrow);
        datasource.setTestOnReturn(testOnReturn);
        datasource.setPoolPreparedStatements(poolPreparedStatements);
        datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
        try {
            datasource.setFilters(filters);
        } catch (SQLException e) {
            logger.error("druid configuration initialization filter", e);
        }
        datasource.setConnectionProperties(connectionProperties);
        logger.info("Druid DataSource Config Succeed");
        return datasource;
    }

    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }
}
