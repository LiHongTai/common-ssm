package com.roger.core.config;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.Data;

import javax.sql.DataSource;
import java.sql.SQLException;

@Data
public class DBConfig {
    protected String driverClassName;
    protected String url;
    protected String userName;
    protected String password;
    protected int initialSize;
    protected int minIdle;
    protected int maxActive;
    protected long maxWait;
    protected boolean poolPreparedStatements;
    protected String validationQuery;
    protected boolean testOnBorrow;
    protected boolean testOnReturn;
    protected boolean testWhileIdle;
    protected long timeBetweenEvictionRunsMillis;
    protected long minEvictableIdleTimeMillis;
    protected String filters;

    public DataSource initDruidDataSource() throws SQLException {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(driverClassName);
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
        return druidDataSource;
    }
}
