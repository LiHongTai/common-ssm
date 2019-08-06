package com.roger.biz.controller;

import com.roger.biz.entity.City;
import com.roger.biz.export.CityExportUtil;
import com.roger.biz.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping(value = "/city")
public class CityController {

    @Autowired
    private CityService cityService;

    @RequestMapping(value = "/exportCityByParam")
    public void exportCityByParam(HttpServletRequest request, HttpServletResponse response){
        List<City> cityList = cityService.findAll();
        CityExportUtil.export(response,cityList);
    }

}
