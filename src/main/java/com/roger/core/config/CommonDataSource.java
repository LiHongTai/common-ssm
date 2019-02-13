package com.roger.core.config;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.sql.SQLException;

@Configuration
@PropertySource("classpath:druid.properties")
@Slf4j
public class CommonDataSource {

    @Value("${datasource.druid.driverClassName}")
    private String driveClassName;
    @Value("${datasource.druid.url}")
    private String url;
    @Value("${datasource.druid.userName}")
    private String userName;
    @Value("${datasource.druid.password}")
    private String password;
    @Value("${datasource.druid.initialSize}")
    private int initialSize;
    @Value("${datasource.druid.minIdle}")
    private int minIdle;
    @Value("${datasource.druid.maxActive}")
    private int maxActive;
    @Value("${datasource.druid.maxWait}")
    private long maxWait;
    @Value("${datasource.druid.poolPreparedStatements}")
    private boolean poolPreparedStatements;
    @Value("${datasource.druid.validationQuery}")
    private String validationQuery;
    @Value("${datasource.druid.testOnBorrow}")
    private boolean testOnBorrow;
    @Value("${datasource.druid.testOnReturn}")
    private boolean testOnReturn;
    @Value("${datasource.druid.testWhileIdle}")
    private boolean testWhileIdle;
    @Value("${datasource.druid.timeBetweenEvictionRunsMillis}")
    private long timeBetweenEvictionRunsMillis;
    @Value("${datasource.druid.minEvictableIdleTimeMillis}")
    private long minEvictableIdleTimeMillis;
    @Value("${datasource.druid.filters}")
    private String filters;

    @Bean(name = "baseDataSource")
    public DruidDataSource dataSource() throws SQLException {
        DruidDataSource druidDataSource = new com.alibaba.druid.pool.DruidDataSource();
        druidDataSource.setDriverClassName(driveClassName);
        druidDataSource.setUrl(url);
        druidDataSource.setUsername(userName);
        druidDataSource.setPassword(password);

        druidDataSource.setInitialSize(initialSize);

        druidDataSource.setMinIdle(minIdle);
        druidDataSource.setMaxActive(maxActive);
        druidDataSource.setMaxWait(maxWait);

        druidDataSource.setPoolPreparedStatements(poolPreparedStatements);

        druidDataSource.setValidationQuery(validationQuery);
        druidDataSource.setTestOnBorrow(testOnBorrow);
        druidDataSource.setTestOnReturn(testOnReturn);
        druidDataSource.setTestWhileIdle(testWhileIdle);
        druidDataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);

        druidDataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);

        druidDataSource.setFilters(filters);
        log.info("druid数据源配置完成");
        return druidDataSource;
    }

}
