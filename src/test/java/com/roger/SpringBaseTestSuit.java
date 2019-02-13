package com.roger;

import com.roger.core.config.CommonDataSource;
import com.roger.core.config.MyBatis;
import com.roger.core.config.SpringConfig;
import com.roger.core.config.SpringTX;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {CommonDataSource.class, MyBatis.class, SpringTX.class, SpringConfig.class})
public class SpringBaseTestSuit {
}
