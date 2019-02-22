package com.roger.core.redis.impl;

import com.roger.core.enumeration.ExistEnum;
import com.roger.core.enumeration.ExpireTimeEnum;
import com.roger.core.redis.IRedis;
import com.roger.core.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisClusterConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service(value = "redisClusterImpl")
public class RedisClusterImpl implements IRedis {

    @Resource(name = "redisClusterTemplate")
    private RedisTemplate redisClusterTemplate;

    /**
     * 获取Jedis原始客户端操作对象
     *
     * @return
     */
    private JedisCluster getJedisCluster() {
        RedisConnectionFactory redisConnectionFactory = redisClusterTemplate.getRequiredConnectionFactory();
        RedisClusterConnection redisClusterConnection = redisConnectionFactory.getClusterConnection();
        JedisCluster jedisCluster = (JedisCluster) redisClusterConnection.getNativeConnection();
        return jedisCluster;
    }

    /**
     * 获取哪个业务下哪个节点下的某个key的结果
     *
     * @param business 类型
     * @param key      在redis数据库中存储节点
     * @param field    类似Java的Map类型中key
     * @return 类似Java的Map类型中value
     */
    @Override
    public Object hget(String business, String key, String field) {
        HashOperations opsForHash = redisClusterTemplate.opsForHash();
        String nodeKey = RedisUtil.buildKey(business, key);
        return opsForHash.get(nodeKey, field);
    }

    /**
     * 获取哪个业务下哪个节点下所有信息
     *
     * @param business 类型
     * @param key      在redis数据库中存储节点
     * @return
     */
    @Override
    public Map<String, String> hgetAll(String business, String key) {
        HashOperations opsForHash = redisClusterTemplate.opsForHash();
        String nodeKey = RedisUtil.buildKey(business, key);
        return opsForHash.entries(nodeKey);
    }

    /**
     * 设置从节点写入redis之后的有效时间，单位秒
     *
     * @param business
     * @param key      在redis数据库中存储节点
     * @param seconds  时间数，单位秒
     * @return
     */
    @Override
    public Boolean expire(String business, String key, int seconds) {
        ValueOperations opsForValue = redisClusterTemplate.opsForValue();
        String nodeKey = RedisUtil.buildKey(business, key);
        return opsForValue.getOperations().expire(nodeKey, seconds, TimeUnit.SECONDS);
    }

    /**
     * 设置在节点写入redis之后，在哪个时间点失效，之后不可用
     *
     * @param business 业务类型
     * @param key      在redis数据库中存储节点
     * @param unixTime 日期时间
     * @return
     */
    @Override
    public Boolean expireAt(String business, String key, Date unixTime) {
        ValueOperations opsForValue = redisClusterTemplate.opsForValue();
        String nodeKey = RedisUtil.buildKey(business, key);
        return opsForValue.getOperations().expireAt(nodeKey, unixTime);
    }

    /**
     * 获取节点还有多少有效时间数，单位秒
     *
     * @param business 业务类型
     * @param key      在redis数据库中存储节点
     * @return
     */
    @Override
    public Long ttl(String business, String key) {
        ValueOperations opsForValue = redisClusterTemplate.opsForValue();
        String nodeKey = RedisUtil.buildKey(business, key);
        return opsForValue.getOperations().getExpire(nodeKey);
    }

    /**
     * 按步长step递减
     *
     * @param business 业务类型
     * @param key      在redis数据库中存储节点
     * @param step     步长
     * @return
     */
    @Override
    public Long decrBy(String business, String key, long step) {
        return incrBy(business, key, -step);
    }

    /**
     * 按步长 1 递减
     *
     * @param business 业务类型
     * @param key      在redis数据库中存储节点
     * @return
     */
    @Override
    public Long decr(String business, String key) {
        return incrBy(business, key, -1);
    }

    /**
     * 按步长step递增
     *
     * @param business 业务类型
     * @param key      在redis数据库中存储节点
     * @param step     步长
     * @return
     */
    @Override
    public Long incrBy(String business, String key, long step) {
        ValueOperations opsForValue = redisClusterTemplate.opsForValue();
        String nodeKey = RedisUtil.buildKey(business, key);
        return opsForValue.increment(nodeKey, step);
    }

    /**
     * 按步长 1 递增
     *
     * @param business 业务类型
     * @param key      在redis数据库中存储节点
     * @return
     */
    @Override
    public Long incr(String business, String key) {
        ValueOperations opsForValue = redisClusterTemplate.opsForValue();
        String nodeKey = RedisUtil.buildKey(business, key);
        return opsForValue.increment(nodeKey, 1);
    }

    /**
     * @param business 业务类型
     * @param key      在redis数据库中存储节点
     * @param field    类似Java的Map类型中key
     * @param value    类似Java的Map类型中value
     */
    @Override
    public void hset(String business, String key, String field, String value) {
        HashOperations opsForHash = redisClusterTemplate.opsForHash();
        String nodeKey = RedisUtil.buildKey(business, key);
        opsForHash.put(nodeKey, field, value);
    }

    @Override
    public void set(String business, String key, String value) {
        ValueOperations opsForValue = redisClusterTemplate.opsForValue();
        String nodeKey = RedisUtil.buildKey(business, key);
        opsForValue.set(nodeKey, value);
    }

    @Override
    public void set(String business, byte[] key, byte[] value) {
        ValueOperations opsForValue = redisClusterTemplate.opsForValue();
        byte[] nodeKey = RedisUtil.buildKey(business, key);
        opsForValue.set(nodeKey, value);
    }

    @Override
    public String get(String business, String key) {
        ValueOperations opsForValue = redisClusterTemplate.opsForValue();
        String nodeKey = RedisUtil.buildKey(business, key);
        return (String) opsForValue.get(nodeKey);
    }

    @Override
    public byte[] get(String business, byte[] key) {
        ValueOperations opsForValue = redisClusterTemplate.opsForValue();
        byte[] nodeKey = RedisUtil.buildKey(business, key);
        return (byte[]) opsForValue.get(nodeKey);
    }

    @Override
    public Boolean exists(String business, String key) {
        ValueOperations opsForValue = redisClusterTemplate.opsForValue();
        String nodeKey = RedisUtil.buildKey(business, key);
        return opsForValue.getOperations().hasKey(nodeKey);
    }

    /**
     * @param business 业务类型
     * @param key      在redis数据库中存储节点
     * @return redis节点存储的数据类型
     */
    @Override
    public String type(String business, String key) {
        ValueOperations opsForValue = redisClusterTemplate.opsForValue();
        String nodeKey = RedisUtil.buildKey(business, key);
        return opsForValue.getOperations().type(nodeKey).name();
    }

    @Override
    public Integer append(String business, String key, String value) {
        ValueOperations opsForValue = redisClusterTemplate.opsForValue();
        String nodeKey = RedisUtil.buildKey(business, key);
        return opsForValue.append(nodeKey, value);
    }

    @Override
    public String substr(String business, String key, int start, int end) {
        ValueOperations opsForValue = redisClusterTemplate.opsForValue();
        String nodeKey = RedisUtil.buildKey(business, key);
        return opsForValue.get(nodeKey, start, end);
    }

    /**
     * 如果redis中设置的节点下的field已经存在，则不能设置成功，反之成功
     *
     * @param business 业务类型
     * @param key      在redis数据库中存储节点
     * @param field    类似Java的Map类型中key
     * @param value    类似Java的Map类型中value
     * @return
     */
    @Override
    public Boolean hsetnx(String business, String key, String field, String value) {
        HashOperations opsForHash = redisClusterTemplate.opsForHash();
        String nodeKey = RedisUtil.buildKey(business, key);
        return opsForHash.putIfAbsent(nodeKey, field, value);
    }

    @Override
    public void hmset(String business, String key, Map<String, String> fieldValueColl) {
        HashOperations opsForHash = redisClusterTemplate.opsForHash();
        String nodeKey = RedisUtil.buildKey(business, key);
        opsForHash.putAll(nodeKey, fieldValueColl);
    }

    @Override
    public List<String> hmget(String business, String key, String... fields) {
        HashOperations opsForHash = redisClusterTemplate.opsForHash();
        String nodeKey = RedisUtil.buildKey(business, key);
        return opsForHash.multiGet(nodeKey, Arrays.asList(fields));
    }

    @Override
    public Long hincrBy(String business, String key, String field, long value) {
        HashOperations opsForHash = redisClusterTemplate.opsForHash();
        String nodeKey = RedisUtil.buildKey(business, key);
        return opsForHash.increment(nodeKey, field, value);
    }

    @Override
    public Boolean hexists(String business, String key, String field) {
        HashOperations opsForHash = redisClusterTemplate.opsForHash();
        String nodeKey = RedisUtil.buildKey(business, key);
        return opsForHash.hasKey(nodeKey, field);
    }

    @Override
    public Long hdel(String business, String key, String... fields) {
        HashOperations opsForHash = redisClusterTemplate.opsForHash();
        String nodeKey = RedisUtil.buildKey(business, key);
        return opsForHash.delete(nodeKey, fields);
    }

    @Override
    public Long hlen(String business, String key) {
        HashOperations opsForHash = redisClusterTemplate.opsForHash();
        String nodeKey = RedisUtil.buildKey(business, key);
        return opsForHash.size(nodeKey);
    }

    @Override
    public Set<String> hkeys(String business, String key) {
        HashOperations opsForHash = redisClusterTemplate.opsForHash();
        String nodeKey = RedisUtil.buildKey(business, key);
        return opsForHash.keys(nodeKey);
    }

    @Override
    public long del(String business, String... keys) {
        ValueOperations opsForValue = redisClusterTemplate.opsForValue();
        List<String> nodeKeys = RedisUtil.buildKeys(business, keys);
        return opsForValue.getOperations().delete(nodeKeys);
    }

    /**
     * 返回列表list的长度
     *
     * @param business 业务类型
     * @param key      在redis数据库中存储节点
     * @return
     */
    @Override
    public Long llen(String business, String key) {
        ListOperations opsForList = redisClusterTemplate.opsForList();
        return opsForList.size(RedisUtil.buildKey(business, key));
    }

    /**
     * lpop命令，从左边弹出一条，并从列表中删除
     * 左..................右
     *
     * @param business 业务类型
     * @param key      在redis数据库中存储节点
     * @return
     */
    @Override
    public String lpop(String business, String key) {
        ListOperations opsForList = redisClusterTemplate.opsForList();
        String nodeKey = RedisUtil.buildKey(business, key);
        return (String) opsForList.leftPop(nodeKey);
    }

    /**
     * lpush 在列表的左边添加N条数据
     * 左..................右
     *
     * @param business 业务类型
     * @param key      在redis数据库中存储节点
     * @param values
     * @return
     */
    @Override
    public Long lpush(String business, String key, String... values) {
        ListOperations opsForList = redisClusterTemplate.opsForList();
        String nodeKey = RedisUtil.buildKey(business, key);
        return opsForList.leftPushAll(nodeKey, Arrays.asList(values));
    }

    /**
     * lrem 从列表中删除一定数量的特定值
     *
     * @param business 业务类型
     * @param key      在redis数据库中存储节点
     * @param count    取值有 >0 =0 <0
     *                 表头....................表尾
     *                 >0 从表头向表尾扫描列表，移除列表中所有与value值相等的 count 条数据，此命令终止
     *                 =0 移除列表中所有与value值相等的元素
     *                 <0 从表尾向表头扫描列表，移除列表中所有与value值相等的 -count 条数据，此命令终止
     * @param value
     * @return
     */
    @Override
    public Long lrem(String business, String key, Long count, String value) {
        ListOperations opsForList = redisClusterTemplate.opsForList();
        String nodeKey = RedisUtil.buildKey(business, key);
        return opsForList.remove(nodeKey, count, value);
    }

    /**
     * 0:表示列表的第一个元素,1:表示列表的第二个元素,2表示列表的第三个元素,依次类推
     * -1:表示列表的最后一个元素,-2:表示列表的倒数第二个元素,-3表示列表的倒数第三个元素
     * 下标超出列表的最大值即size() -1,则不会报错
     * 如果start > size() -1 ,则返回一个空列表
     * 如果end > size() -1 ,则按照 end = size() -1来获取列表的集合
     *
     * @param business 业务类型
     * @param key      在redis数据库中存储节点
     * @param start    下标取值范围 ...-2,-1,0,1,2...
     * @param end      下标取值范围 ...-2,-1,0,1,2...
     * @return
     */
    @Override
    public List<String> lrange(String business, String key, Long start, Long end) {
        ListOperations opsForList = redisClusterTemplate.opsForList();
        String nodeKey = RedisUtil.buildKey(business, key);
        return opsForList.range(nodeKey, start, end);
    }

    /**
     * 0:表示列表的第一个元素,1:表示列表的第二个元素,2表示列表的第三个元素,依次类推
     * -1:表示列表的最后一个元素,-2:表示列表的倒数第二个元素,-3表示列表的倒数第三个元素
     *
     * @param business 业务类型
     * @param key      在redis数据库中存储节点
     * @param index    取值范围 ...-2,-1,0,1,2...
     * @return
     */
    @Override
    public String lindex(String business, String key, Long index) {
        ListOperations opsForList = redisClusterTemplate.opsForList();
        String nodeKey = RedisUtil.buildKey(business, key);
        return (String) opsForList.index(nodeKey, index);
    }

    /**
     * rpop命令，从右边弹出一条，并从列表中删除
     * 左..................右
     *
     * @param business 业务类型
     * @param key      在redis数据库中存储节点
     * @return
     */
    @Override
    public String rpop(String business, String key) {
        ListOperations opsForList = redisClusterTemplate.opsForList();
        String nodeKey = RedisUtil.buildKey(business, key);
        return (String) opsForList.rightPop(nodeKey);
    }

    /**
     * rpush 在列表的右边添加N条数据
     * 左..................右
     *
     * @param business 业务类型
     * @param key      在redis数据库中存储节点
     * @param values
     * @return
     */
    @Override
    public Long rpush(String business, String key, String... values) {
        ListOperations opsForList = redisClusterTemplate.opsForList();
        String nodeKey = RedisUtil.buildKey(business, key);
        return opsForList.rightPushAll(nodeKey, Arrays.asList(values));
    }

    @Override
    public Long sadd(String business, String key, String... values) {
        SetOperations opsForSet = redisClusterTemplate.opsForSet();
        String nodeKey = RedisUtil.buildKey(business, key);
        return opsForSet.add(nodeKey, values);
    }

    /**
     * 返回集合Set中元素的个数
     *
     * @param business 业务类型
     * @param key      在redis数据库中存储节点
     * @return
     */
    @Override
    public Long scard(String business, String key) {
        SetOperations opsForSet = redisClusterTemplate.opsForSet();
        String nodeKey = RedisUtil.buildKey(business, key);
        return opsForSet.size(nodeKey);
    }

    /**
     * @param business 业务类型
     * @param key      在redis数据库中存储节点
     * @param seconds  过期时间，单位秒
     * @param value
     * @return
     */
    @Override
    public void setex(String business, String key, Integer seconds, String value) {
        ValueOperations opsForValue = redisClusterTemplate.opsForValue();
        String nodeKey = RedisUtil.buildKey(business, key);
        opsForValue.set(nodeKey, value, seconds, TimeUnit.SECONDS);
    }

    /**
     * 获取集合类型Set中所有的元素
     *
     * @param business 业务类型
     * @param key      在redis数据库中存储节点
     * @return
     */
    @Override
    public Set<String> smembers(String business, String key) {
        SetOperations opsForSet = redisClusterTemplate.opsForSet();
        String nodeKey = RedisUtil.buildKey(business, key);
        return opsForSet.members(nodeKey);
    }

    /**
     * 移除集合Set中的一个或多个元素
     *
     * @param business 业务类型
     * @param key      在redis数据库中存储节点
     * @param values   集合中将要删除的元素
     * @return 从集合中删除元素的个数
     */
    @Override
    public Long srem(String business, String key, String... values) {
        SetOperations opsForSet = redisClusterTemplate.opsForSet();
        String nodeKey = RedisUtil.buildKey(business, key);
        return opsForSet.remove(nodeKey, values);
    }

    /**
     * 对列表list类型的集合进行修剪，只保留start和end之间的元素，其他元素清楚
     *
     * @param business 业务类型
     * @param key      在redis数据库中存储节点
     * @param start    ...-2,-1,0,1,2....
     * @param end      ...-2,-1,0,1,2....
     * @return
     */
    @Override
    public void ltrim(String business, String key, Long start, Long end) {
        ListOperations opsForList = redisClusterTemplate.opsForList();
        String nodeKey = RedisUtil.buildKey(business, key);
        opsForList.trim(nodeKey, start, end);
    }

    /**
     * 判断集合Set中是否存在memeber元素
     *
     * @param business 业务类型
     * @param key      在redis数据库中存储节点
     * @param member   元素
     * @return
     */
    @Override
    public Boolean sismember(String business, String key, String member) {
        SetOperations opsForSet = redisClusterTemplate.opsForSet();
        String nodeKey = RedisUtil.buildKey(business, key);
        return opsForSet.isMember(nodeKey, member);
    }

    /**
     * @param business       业务类型
     * @param key            在redis数据库中存储节点
     * @param value
     * @param existEnum      设置保存条件
     *                       NX: 只有当redis库中不存在key，才能设置成功
     *                       XX: 只有当redis库中已经存在key，才能设置成功
     * @param expireTimeEnum 设置过期时间的单位
     *                       EX: 单位秒
     *                       PX: 单位毫秒
     * @param time
     * @return
     */
    @Override
    public String set(String business, String key, String value, ExistEnum existEnum, ExpireTimeEnum expireTimeEnum, long time) {
        String nodeKey = RedisUtil.buildKey(business, key);
        return getJedisCluster().set(nodeKey, value, existEnum.name(), expireTimeEnum.name(), time);
    }

    /**
     * 获取当前节点的值，并用新值更新当前节点的值
     *
     * @param business 业务类型
     * @param key      在redis数据库中存储节点
     * @param value    存储节点的新值
     * @return oldValue
     */
    @Override
    public String getSet(String business, String key, String value) {
        ValueOperations opsForValue = redisClusterTemplate.opsForValue();
        String nodeKey = RedisUtil.buildKey(business, key);
        return (String) opsForValue.getAndSet(nodeKey, value);
    }

    @Override
    public Long hset(String business, byte[] key, byte[] field, byte[] value) {
        byte[] nodeKey = RedisUtil.buildKey(business, key);
        return getJedisCluster().hset(nodeKey, field, value);
    }

    @Override
    public byte[] hget(String business, byte[] key, byte[] field) {
        byte[] nodeKey = RedisUtil.buildKey(business, key);
        return getJedisCluster().hget(nodeKey, field);
    }

}
