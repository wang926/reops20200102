package com.weige.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.weige.entity.Employee;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class MyRedisConfig {
    @Bean
    public RedisTemplate<Object, Employee> EmpredisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<Object,Employee> redisTemplate=new RedisTemplate<Object,Employee>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        Jackson2JsonRedisSerializer<Employee> se = new Jackson2JsonRedisSerializer<Employee>(Employee.class);
        redisTemplate.setDefaultSerializer( se);
        return redisTemplate;
    }
 /*@Bean
    public RedisCacheManager empCachMager(RedisTemplate<Object, Employee> EmpredisTemplate){
     //RedisCacheManager redisCacheManager=new RedisCacheManager(EmpredisTemplate);
     RedisCacheManager redisCacheManager=new RedisCacheManager( RedisTemplate<Object, Employee> EmpredisTemplate);
     return redisCacheManager;

 }*/
    /**
     * Redis转化为json通用配置--wang
     * **/
    @Bean
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){

     RedisTemplate redisTemplate=new RedisTemplate();
     //配置连接工厂
     redisTemplate.setConnectionFactory(redisConnectionFactory);
     //修改默认的JDK序列化方式为Jackson2
     Jackson2JsonRedisSerializer jackson2JsonRedisSerializer=new Jackson2JsonRedisSerializer(Object.class);
     //
     ObjectMapper objectMapper=new ObjectMapper();
     //
     objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
     //
     objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
     //
     jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
     //采用JSON序列化
     redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
     //
     redisTemplate.setKeySerializer(new StringRedisSerializer());
     //
     redisTemplate.setHashKeySerializer(new StringRedisSerializer());
     //
     redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);

     return redisTemplate;

 }
}
