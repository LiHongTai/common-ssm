package com.roger;

import com.roger.core.config.datasource.MasterDBConfig;
import com.roger.core.config.CustomSqlSessionFactoryBean;
import com.roger.core.config.SpringConfig;
import com.roger.core.config.SpringTX;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MasterDBConfig.class, CustomSqlSessionFactoryBean.class, SpringTX.class, SpringConfig.class})
public class SpringBaseTestSuit {
}
