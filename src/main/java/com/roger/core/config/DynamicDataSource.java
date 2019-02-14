package com.roger.core.config;

import com.roger.core.config.datasource.MasterDBConfig;
import com.roger.core.config.datasource.SlaveDBConfig;
import com.roger.core.enumeration.DataSourceType;
import com.roger.core.multiple.DataSourceContextHolder;
import com.roger.core.multiple.MultipleDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Configuration
@Import({MasterDBConfig.class, SlaveDBConfig.class})
@Slf4j
public class DynamicDataSource {


    @Autowired
    private MasterDBConfig masterDBConfig;

    @Autowired
    private SlaveDBConfig slaveDBConfig;

    @Bean(name = "multipleDataSource")
    @Primary
    public DataSource multipleDataSource() throws SQLException {
        MultipleDataSource multipleDataSource = new MultipleDataSource();
        DataSource masterDataSource = masterDBConfig.initDruidDataSource();
        log.info("设置默认数据源{}", masterDataSource);
        multipleDataSource.setDefaultTargetDataSource(masterDataSource);
        log.info("开始配置多数据源");
        Map<Object, Object> targetDataSources = new HashMap<>();
        addTargetDataSources(targetDataSources, DataSourceType.MASTER, masterDataSource);
        log.info("主数据源添加成功...");
        addTargetDataSources(targetDataSources, DataSourceType.SLAVE, slaveDBConfig.initDruidDataSource());
        log.info("从数据源添加成功...");
        multipleDataSource.setTargetDataSources(targetDataSources);
        log.info("配置多数据源完成");
        return multipleDataSource;
    }

    private void addTargetDataSources(Map<Object, Object> targetDataSources, DataSourceType dataSourceType, DataSource dataSource) {
        targetDataSources.put(dataSourceType.name(), dataSource);
        DataSourceContextHolder.dataSourceIds.add(dataSourceType.name());
    }
}