package com.roger.core.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roger.core.config.redis.RedisPoolConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Configuration
@Import({RedisPoolConfig.class})
@Slf4j
public class RedisClusterConnectionFactory {

    @Autowired
    private RedisPoolConfig redisPoolConfig;

    @Bean(name = "jedisClusterConnectionFactory")
    public JedisConnectionFactory jedisClusterConnectionFactory() {

        Properties prop = new Properties();
        InputStream in = this.getClass().getResourceAsStream("/redisCluster.properties");
        try {
            prop.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PropertiesPropertySource propertySource = new PropertiesPropertySource("clusterNodes", prop);
        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration(propertySource);
        //设置集群密码
        redisClusterConfiguration.setPassword(RedisPassword.of(redisPoolConfig.getPassword()));

        JedisClientConfiguration.JedisPoolingClientConfigurationBuilder
                jedisPoolConfigBuilder = (JedisClientConfiguration.JedisPoolingClientConfigurationBuilder) JedisClientConfiguration.builder();

        jedisPoolConfigBuilder.poolConfig(redisPoolConfig.initJedisPoolConfig());

        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(redisClusterConfiguration, jedisPoolConfigBuilder.build());
        return jedisConnectionFactory;
    }

    @Bean(name = "redisClusterTemplate")
    public RedisTemplate redisTemplate(@Qualifier("jedisClusterConnectionFactory") JedisConnectionFactory jedisConnectionFactory) {
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        RedisSerializer stringRedisSerializer = new StringRedisSerializer();

        RedisTemplate redisTemplate = new RedisTemplate();

        redisTemplate.setConnectionFactory(jedisConnectionFactory);

        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);

        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;

    }
}
