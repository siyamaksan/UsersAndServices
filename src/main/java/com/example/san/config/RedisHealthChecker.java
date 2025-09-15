package com.example.san.config;

import jakarta.annotation.PostConstruct;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisHealthChecker {

  private final RedisTemplate<String, Object> redisTemplate;

  public RedisHealthChecker(RedisTemplate<String, Object> redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  @PostConstruct
  public void checkRedisConnection() {
    try {
      redisTemplate.getConnectionFactory().getConnection().ping();
      System.out.println("✅ Redis is available");
    } catch (Exception e) {
      throw new IllegalStateException("❌ Redis is not available! Application will not start.", e);
    }
  }
}