package com.roger.core.config;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;
import java.sql.SQLException;

@Data
@Configuration
@ConfigurationProperties(prefix = "master.datasource.druid")
@PropertySource(value = "classpath:masterdb.properties")
@Slf4j
public class CommonDataSource {

    private String driveClassName;
    private String url;
    private String userName;
    private String password;
    private int initialSize;
    private int minIdle;
    private int maxActive;
    private long maxWait;
    private boolean poolPreparedStatements;
    private String validationQuery;
    private boolean testOnBorrow;
    private boolean testOnReturn;
    private boolean testWhileIdle;
    private long timeBetweenEvictionRunsMillis;
    private long minEvictableIdleTimeMillis;
    private String filters;

    @Bean(name = "baseDataSource")
    public DataSource dataSource() throws SQLException {
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
        log.info("baseDataSource 数据源配置完成");
        return druidDataSource;
    }
}
