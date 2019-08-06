package com.roger.biz.service;

import com.github.pagehelper.PageInfo;
import com.roger.biz.entity.City;
import com.roger.core.service.CommonService;

import java.util.List;

public interface CityService extends CommonService<City> {

    List<City> findAll();

    PageInfo<City> findAll4Page();

}
