package com.roger.core.config;

import com.roger.core.config.datasource.MasterDBConfig;
import com.roger.core.config.datasource.SlaveDBConfig;
import com.roger.core.constant.SystemConstant;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Spring管理容器
 */
@Configuration
@EnableConfigurationProperties({MasterDBConfig.class,SlaveDBConfig.class})
@ComponentScan(value = SystemConstant.COMPONENT_SCAN_PACKAGE)
@EnableAspectJAutoProxy
public class SpringConfig {
}
