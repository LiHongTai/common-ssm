package com.roger.core.multiple;


import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

@Slf4j
public class MultipleDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
      String currentDataSource =  DataSourceContextHolder.getDataSource();
      if(currentDataSource == null){
          log.info("未设置数据源，使用默认的数据源");
      }else{
          log.info("当前使用的数据源为{}",currentDataSource);
      }
      return currentDataSource;
    }
}
