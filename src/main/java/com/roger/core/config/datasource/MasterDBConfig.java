package com.roger.core.config.datasource;

import com.roger.core.config.DBConfig;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@ConfigurationProperties(prefix = "master.datasource.druid")
@PropertySource(value = "classpath:masterdb.properties")
public class MasterDBConfig extends DBConfig {

}
