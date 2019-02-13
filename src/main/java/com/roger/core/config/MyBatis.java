package com.roger.core.config;

import com.roger.core.constant.SystemConstant;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@Import(CommonDataSource.class)
@MapperScan(value = SystemConstant.MAPPER_SCAN_BASE_PACKAGE ,sqlSessionFactoryRef = "baseSqlSessionFactory")
public class MyBatis {


    @Bean(name = "baseSqlSessionFactory")
    public SqlSessionFactoryBean sqlSessionFactoryBean(@Qualifier("baseDataSource") DataSource dataSource) throws IOException {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();

        sqlSessionFactoryBean.setDataSource(dataSource);

        //打印SQL语句到控制台
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setLogImpl(StdOutImpl.class);
        sqlSessionFactoryBean.setConfiguration(configuration);

        //把相关的Mapper配置文件加入导入到SqlSessionFactory中
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resourcePatternResolver.getResources(SystemConstant.MAPPER_XML_PATH));

        sqlSessionFactoryBean.setTypeAliasesPackage(SystemConstant.TYPE_ALIASES_PACKAGE);

        return sqlSessionFactoryBean;
    }

}
