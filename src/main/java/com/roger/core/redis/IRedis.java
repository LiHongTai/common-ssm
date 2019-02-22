package com.roger.core.redis;

import com.roger.core.enumeration.ExistEnum;
import com.roger.core.enumeration.ExpireTimeEnum;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IRedis {
    Object hget(String business, String key, String field);
    Map<String, String> hgetAll(String business, String key);
    Boolean expire(String business, String key, int seconds);
    Boolean expireAt(String business, String key, Date unixTime);
    Long ttl(String business, String key);
    Long decrBy(String business, String key, long step);
    Long decr(String business, String key);
    Long incrBy(String business, String key, long step);
    Long incr(String business, String key);
    void hset(String business, String key, String field, String value);
    void set(String business, String key, String value);
    void set(String business, byte[] key, byte[] value);
    String get(String business, String key);
    byte[] get(String business, byte[] key);
    Boolean exists(String business, String key);
    String type(String business, String key);
    Integer append(String business, String key, String value);
    String substr(String business, String key, int start, int end);
    Boolean hsetnx(String business, String key, String field, String value);
    void hmset(String business, String key, Map<String, String> fieldValueColl);
    List<String> hmget(String business, String key, String... fields);
    Long hincrBy(String business, String key, String field, long value);
    Boolean hexists(String business, String key, String field);
    Long hdel(String business, String key, String... fields);
    Long hlen(String business, String key);
    Set<String> hkeys(String business, String key);

    long del(String business, String... keys);
    Long llen(String business, String key);
    String lpop(String business, String key);
    Long lpush(String business, String key, String... values);
    Long lrem(String business, String key, Long count, String value);
    List<String> lrange(String business, String key, Long start, Long end);
    String lindex(String business, String key, Long index);
    String rpop(String business, String key);
    Long rpush(String business, String key, String... values);
    Long sadd(String business, String key, String... values);
    Long scard(String business, String key);
    void setex(String business, String key, Integer seconds, String value);
    Set<String> smembers(String business, String key);
    Long srem(String business, String key, String... values);
    void ltrim(String business, String key, Long start, Long end);
    Boolean sismember(String business, String key, String member);

    String set(String business, String key, String value, ExistEnum existEnum, ExpireTimeEnum expireTimeEnum, long time);
    String getSet(String business, String key, String value);

    Long hset(String business, byte[] key, byte[] field, byte[] value);
    byte[] hget(String business, byte[] key, byte[] field);

}
