package com.roger.core.config;

import com.roger.core.constant.SystemConstant;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Spring管理容器
 */
@Configuration
@ComponentScan(value = SystemConstant.COMPONENT_SCAN_PACKAGE)
public class SpringConfig {
}
