package com.seven.cow.servlet.cache.service.impl;

import com.seven.cow.servlet.cache.service.CacheStorageManager;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @description: TODO
 * @author：EDY
 * @date: 2022/4/14 8:42
 * @version: 1.0
 */
public class RedisCacheStorageManagerImpl implements CacheStorageManager {

    @Resource
    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void set(String key, Object value, TimeUnit timeUnit, long expireTime) {
        redisTemplate.opsForValue().set(key, value, expireTime, timeUnit);
    }

    @Override
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public Object getOrDefault(String key, Object defaultValue) {
        Object object = get(key);
        if (null == object) {
            redisTemplate.opsForValue().getAndSet(key, defaultValue);
            return defaultValue;
        }
        return object;
    }

    @Override
    public void remove(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public void expire(String key, TimeUnit timeUnit, long expireTime) {
        redisTemplate.expire(key, expireTime, timeUnit);
    }
}
