package com.example.redistest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RedisTest {

  private static final Logger log = LoggerFactory.getLogger(RedisTest.class);

  @Autowired
  StringRedisTemplate stringRedisTemplate;

  @Test
  public void testSet() {
    log.info("testSet begin...");
    stringRedisTemplate.opsForValue().set("test-string-value", "Hello Redis");
    log.info("testSet end...");
  }

  @Test
  public void testGet() {
    String value = stringRedisTemplate.opsForValue().get("test-string-value");
    System.out.println("get from redis----->"+value);
  }
}
