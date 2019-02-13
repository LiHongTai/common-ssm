package com.roger.biz.entity;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class User {

    private Long id;
    private String userName;
    private Integer age;
    private String phone;
    private Date createdTime;
    private Date updatedTime;
}
