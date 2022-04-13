package com.seven.cow.servlet.cache.service.impl;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.seven.cow.servlet.cache.service.CacheStorageManager;

import java.util.concurrent.TimeUnit;

/**
 * @description: TODO
 * @author：EDY
 * @date: 2022/4/12 9:13
 * @version: 1.0
 */
public class DefaultCacheStorageManagerImpl implements CacheStorageManager {

    private static final Cache<String, Object> cacheManager = Caffeine.newBuilder().build();

    @Override
    public void set(String key, Object value) {
        cacheManager.put(key, value);
    }

    @Override
    public void set(String key, Object value, TimeUnit timeUnit, long timeout) {
        Cache<String, Object> cache = Caffeine.newBuilder()
                .expireAfterWrite(timeout, timeUnit)
                .maximumSize(1L)
                .initialCapacity(1)
                .build();
        cache.put(key, value);
        cacheManager.put(key, cache);
    }

    @Override
    public Object get(String key) {
        Object object = cacheManager.getIfPresent(key);
        if (object instanceof Cache) {
            return ((Cache) object).getIfPresent(key);
        }
        return object;
    }

    @Override
    public void remove(String key) {
        cacheManager.invalidate(key);
    }
}
