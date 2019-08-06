package com.roger.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.roger.biz.entity.City;
import com.roger.biz.service.CityService;
import com.roger.core.annotation.TargetDataSource;
import com.roger.core.enumeration.DataSourceType;
import com.roger.core.mapper.CityMapper;
import com.roger.core.service.impl.CommonServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@TargetDataSource(DataSourceType.SLAVE)
@Slf4j
public class CityServiceImpl extends CommonServiceImpl<City> implements CityService {

    @Autowired
    private CityMapper cityMapper;

    @Override
    public List<City> findAll() {
        return cityMapper.findAll();
    }

    @Override
    public PageInfo<City> findAll4Page() {
        //启动分页查询
        PageHelper.startPage(0,5);
        List<City> cityList = cityMapper.findAll();
        PageInfo<City> cityPageInfo = new PageInfo<>(cityList);
        return cityPageInfo;
    }
}
