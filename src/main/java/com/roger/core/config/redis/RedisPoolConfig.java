package com.roger.core.config.redis;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import redis.clients.jedis.JedisPoolConfig;

@Data
@Configuration
@ConfigurationProperties(prefix = "redis.pool.config")
@PropertySource(value = "classpath:redis.properties")
public class RedisPoolConfig {

    private int maxIdle;
    private int maxTotal;
    private int maxWaitMillis;
    private boolean blockWhenExhausted;
    private boolean testOnBorrow;
    private String password;

    public JedisPoolConfig initJedisPoolConfig(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();

        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxTotal(maxTotal);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        jedisPoolConfig.setBlockWhenExhausted(blockWhenExhausted);
        jedisPoolConfig.setTestOnBorrow(testOnBorrow);

        return jedisPoolConfig;
    }
}
