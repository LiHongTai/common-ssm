package com.roger.biz.service.impl;

import com.roger.biz.entity.City;
import com.roger.biz.service.CityService;
import com.roger.core.annotation.TargetDataSource;
import com.roger.core.enumeration.DataSourceType;
import com.roger.core.service.impl.CommonServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@TargetDataSource(DataSourceType.SLAVE)
@Slf4j
public class CityServiceImpl extends CommonServiceImpl<City> implements CityService {

}
