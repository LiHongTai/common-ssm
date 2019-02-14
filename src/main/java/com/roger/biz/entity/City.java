package com.roger.biz.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class City {
    private Long id;
    private Integer provinceId;
    private String cityName;
    private String description;
}
