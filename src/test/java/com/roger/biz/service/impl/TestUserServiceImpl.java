package com.roger.biz.service.impl;

import com.roger.SpringBaseTestSuit;
import com.roger.biz.entity.User;
import com.roger.biz.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TestUserServiceImpl extends SpringBaseTestSuit {

    @Autowired
    private UserService userService;

    @Test
    public void testInsert() {
        User user = new User();
        user.setUserName("Jackson");
        user.setAge(38);
        user.setPhone("15498756489");
        int count = userService.insert(user);
        Assert.assertTrue(count == 1);
    }

}
