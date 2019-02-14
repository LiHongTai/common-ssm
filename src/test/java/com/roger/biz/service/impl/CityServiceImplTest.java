package com.roger.biz.service.impl;

import com.roger.SpringBaseTestSuit;
import com.roger.biz.entity.City;
import com.roger.biz.service.CityService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class CityServiceImplTest extends SpringBaseTestSuit {

    @Autowired(required = false)
    private CityService cityService;

    @Test
    public void testInsert(){
        City city = new City();
        city.setCityName("上海");
        city.setProvinceId(341002);
        city.setDescription("全世界第一城市");
        int count =  cityService.insert(city);
        Assert.assertTrue(count == 1);
    }
}