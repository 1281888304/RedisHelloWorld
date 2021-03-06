package com.example.redistest;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.cache.interceptor.KeyGenerator;
import java.lang.reflect.Method;


//配置redis整入spring的缓存框架

@Configuration
@EnableCaching  //继承CachingConfigurerSupport并重写方法，配合该注解实现spring缓存框架的使用
public class RedisConfig extends CachingConfigurerSupport {


  /**载入配置文件配置的连接工厂**/
  @Autowired
  RedisConnectionFactory redisConnectionFactory;
  /*不提示警告信息*/
  @SuppressWarnings("rawtypes")
  @Autowired
  RedisTemplate redisTemplate;

  @Bean
  RedisTemplate<String,Object> objectRedisTemplate(){
    RedisTemplate<String,Object> redisTemplate=new RedisTemplate<>();
    redisTemplate.setConnectionFactory(redisConnectionFactory);
    return redisTemplate;
  }

  // 管理缓存
  @Bean
  public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
    RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
        .entryTtl( Duration.ofHours(1)); // 设置缓存有效期一小时
    return RedisCacheManager
        .builder( RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory))
        .cacheDefaults(redisCacheConfiguration).build();
  }

  /**
   * 重写缓存key生成策略，可根据自身业务需要进行自己的配置生成条件
   * @return
   */
  @Bean
  @Override
  public KeyGenerator keyGenerator() {
    return new KeyGenerator() {
      @Override
      public Object generate(Object target, Method method, Object... params) {
        StringBuilder sb = new StringBuilder();
        sb.append(target.getClass().getName());
        sb.append(method.getName());
        for (Object obj : params) {
          sb.append(obj.toString());
        }
        return sb.toString();
      }
    };
  }





}
