package com.roger.core.redis.impl;

import com.roger.SpringBaseTestSuit;
import com.roger.core.redis.IRedis;
import org.junit.Test;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

public class RedisClusterImplTest extends SpringBaseTestSuit {

    @Resource(name = "redisClusterImpl")
    private IRedis iRedis;

    @Test
    public void testHget() {
        Object value = iRedis.hget("member", "user:1001", "name");
        assertTrue("Mary".equals(value.toString()));
    }

    @Test
    public void testHset() {
        iRedis.hset("member", "user:1001", "name", "Mary");
    }


    @Test
    public void testHmset() {
        Map<String, String> fieldValueColl = new HashMap<>();
        fieldValueColl.put("name", "Mary");
        fieldValueColl.put("address", "SH");
        iRedis.hmset("member", "user:1001", fieldValueColl);
    }

    @Test
    public void testHmget() {
        List<String> stringList = iRedis.hmget("member", "user:1001", "name", "address");
        assertTrue(stringList.size() == 2);
    }

    @Test
    public void testHsetnx() {
        Boolean aBoolean = iRedis.hsetnx("member", "user:1001", "name", "Roger");
        assertFalse(aBoolean);
    }

    @Test
    public void testhkeys() {
        Set<String> hkeys = iRedis.hkeys("member", "user:1001");
        System.out.println(hkeys);
    }

    @Test
    public void testlpush() {
        Long lpush = iRedis.lpush("order", "apple", "IPhone6", "IPhone7");
        System.out.println(lpush);
    }

    @Test
    public void testsadd(){
        Long aLong = iRedis.sadd("goods", "phone", "HW");
        System.out.println("sadd：" + aLong);
    }

    @Test
    public void testsmembers(){
        Set<String> smembers = iRedis.smembers("goods", "phone");
        System.out.println(smembers);
    }

    @Test
    public void testSrem(){
        Long srem = iRedis.srem("goods", "phone", "IPhone", "ds");
        System.out.println(srem);
    }

    @Test
    public void testsismember(){
        Boolean aBoolean = iRedis.sismember("goods", "phone", "ViVo");
        assertTrue(aBoolean);
    }

    @Test
    public void testHsetByteArr() {
        Long aLong = iRedis.hset("member", "user:1002".getBytes(), "name".getBytes(), "Roger6632".getBytes());
        System.out.println("hset:"+aLong);
    }

    @Test
    public void testHgetByteArr() {
        byte[] bytes = iRedis.hget("member", "user:1002".getBytes(), "name".getBytes());
        System.out.println(new String(bytes));
    }
}