package com.roger.core.dao.impl;

import com.roger.SpringBaseTestSuit;
import com.roger.biz.entity.User;
import com.roger.core.constant.OgnlParamConstant;
import com.roger.core.dao.GeneralDao;
import com.roger.core.db.GeneralOgnlParam;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class TestGeneralDaoImpl extends SpringBaseTestSuit {

    @Autowired(required = false)
    private GeneralDao crudDao;

    @Test
    public void testSelectByPrimaryKey() {
        Map<String, Object> paramMap = new HashMap<>();
        //主键列
        paramMap.put(OgnlParamConstant.PRIMARY_KEY, "id");
        //主键值
        paramMap.put(OgnlParamConstant.PRIMARY_VALUE, 79);
        //查询列名
        List<String> columnNameList = new ArrayList<>();
        columnNameList.add("id");
        columnNameList.add("user_name userName");//起别名
        columnNameList.add("age");
        columnNameList.add("phone");
        columnNameList.add("created_time createdTime");
        columnNameList.add("updated_time updatedTime");
        paramMap.put(OgnlParamConstant.DML_COLUMN_NAME_LIST, columnNameList);

        User user = crudDao.selectByPrimaryKey(User.class,paramMap);
        System.out.println(user);
    }

    @Test
    public void testSelectAdvanced() {
        GeneralOgnlParam generalOgnlParam = new GeneralOgnlParam();

        //排序字段 表的列名
        String orderExp = "updated_time desc";
        generalOgnlParam.setOrderExp(orderExp);
        //where条件表达式
        String conditionExp = "user_name = #{conditionParam.userName} and age = #{conditionParam.age}";
        generalOgnlParam.setConditionExp(conditionExp);
        Map<String,Object> conditionParam = new HashMap<>();
        conditionParam.put("userName","Roger");
        conditionParam.put("age",21);
        generalOgnlParam.setConditionParam(conditionParam);
        //启动分页
        generalOgnlParam.setEnablePage(true);
        generalOgnlParam.setPageSize(2);
        generalOgnlParam.setPageNo(1);

        List<User> users = crudDao.selectAdvanced(User.class, generalOgnlParam);
        System.out.println(users);

        generalOgnlParam.setPageNo(2);
        users = crudDao.selectAdvanced(User.class, generalOgnlParam);
        System.out.println(users);
    }

    @Test
    public void testInsert() {
        User user = new User();
        user.setUserName("Jackson");
        user.setAge(38);
        user.setPhone("15498756489");
        int count = crudDao.insert(user);
        Assert.assertTrue(count == 1);
    }

    @Test
    public void testInsertBatch(){
        List<User> userList = new ArrayList<>();
        User user = new User();
        user.setUserName("Jacson");
        user.setAge(38);
        user.setPhone("15498756489");
        userList.add(user);

        user = new User();
        user.setUserName("Biliy");
        user.setAge(38);
        user.setPhone("15498756489");
        userList.add(user);

        user = new User();
        user.setUserName("James");
        user.setAge(38);
        user.setPhone("15498756489");
        userList.add(user);

        int count = crudDao.batchInsert(userList);
        Assert.assertTrue(count == 3);
    }

    @Test
    public void testUpdate(){
        User user = new User();
        user.setAge(56);
        user.setUpdatedTime(new Date());
        GeneralOgnlParam generalOgnlParam = new GeneralOgnlParam();
        String conditionExp = "id=#{conditionParam.id}";
        generalOgnlParam.setConditionExp(conditionExp);
        Map<String,Object> conditionParam = new HashMap<>();
        conditionParam.put("id",81);
        generalOgnlParam.setConditionParam(conditionParam);

        int count = crudDao.update(user,generalOgnlParam);
        Assert.assertTrue(count == 1);
    }

    @Test
    public void testDeletePrimaryKey(){
        Map<String, Object> paramMap = new HashMap<>();
        //主键列
        paramMap.put(OgnlParamConstant.PRIMARY_KEY, "id");
        //主键值
        paramMap.put(OgnlParamConstant.PRIMARY_VALUE, 79);

        User user = crudDao.selectByPrimaryKey(User.class, paramMap);
        int expectedValue = 1;
        if(user == null){
            expectedValue = 0;
        }
        int realValue = crudDao.deleteByPrimaryKey(User.class,paramMap);
        Assert.assertTrue(expectedValue == realValue);
    }

    @Test
    public void testDeleteAdvanced(){
        GeneralOgnlParam generalOgnlParam = new GeneralOgnlParam();
        String conditionExp = " age = #{conditionParam.age} and user_name = #{conditionParam.userName}";
        generalOgnlParam.setConditionExp(conditionExp);

        Map<String,Object> conditionParam = new HashMap<>();
        conditionParam.put("age",38);
        conditionParam.put("userName","Jacson");
        generalOgnlParam.setConditionParam(conditionParam);

        List<User> userList = crudDao.selectAdvanced(User.class, generalOgnlParam);
        int expectedValue = userList.size();
        int realValue = crudDao.deleteAdvanced(User.class,generalOgnlParam);
        Assert.assertTrue(expectedValue == realValue);
    }
}
