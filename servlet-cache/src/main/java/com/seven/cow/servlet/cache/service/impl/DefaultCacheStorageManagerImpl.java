package com.seven.cow.servlet.cache.service.impl;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;
import com.seven.cow.servlet.cache.entity.CacheObject;
import com.seven.cow.servlet.cache.properties.CacheProperties;
import com.seven.cow.servlet.cache.service.CacheStorageManager;
import com.seven.cow.servlet.cache.util.CacheUtils;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class DefaultCacheStorageManagerImpl implements CacheStorageManager {

    private static Cache<String, CacheObject> cacheObjectCache = null;

    public DefaultCacheStorageManagerImpl(CacheProperties cacheProperties) {
        cacheObjectCache = Caffeine.newBuilder()
                .initialCapacity(cacheProperties.getInitialCapacity())
                .maximumSize(cacheProperties.getMaximumSize())
                .weakKeys()
                .softValues()
                .expireAfter(new Expiry<String, CacheObject>() {
                    @Override
                    public long expireAfterCreate(@NonNull String key, @NonNull CacheObject value, long currentTime) {
                        TimeUnit timeUnit = value.getExpireUnit();
                        long expireTime = value.getExpireTime();
                        return CacheUtils.calculateCacheExpireTime(timeUnit, expireTime);
                    }

                    @Override
                    public long expireAfterUpdate(@NonNull String key, @NonNull CacheObject value, long currentTime, @NonNegative long currentDuration) {
                        TimeUnit timeUnit = value.getExpireUnit();
                        long expireTime = value.getExpireTime();
                        return CacheUtils.calculateCacheExpireTime(timeUnit, expireTime);
                    }

                    @Override
                    public long expireAfterRead(@NonNull String key, @NonNull CacheObject value, long currentTime, @NonNegative long currentDuration) {
                        return currentDuration;
                    }
                })
                .build();
    }

    @Override
    public void set(String key, Object value) {
        set(key, value, TimeUnit.HOURS, 2);
    }

    @Override
    public void set(String key, Object value, TimeUnit timeUnit, long expireTime) {
        CacheObject cacheObject = new CacheObject(key);
        cacheObject.setValue(value);
        cacheObject.setExpireUnit(timeUnit);
        cacheObject.setExpireTime(expireTime);
        cacheObjectCache.put(key, cacheObject);
    }

    @Override
    public Object get(String key) {
        CacheObject cacheObject = cacheObjectCache.getIfPresent(key);
        if (null != cacheObject) {
            return cacheObject.getValue();
        }
        return null;
    }

    @Override
    public Object getOrDefault(String key, Object defaultValue) {
        CacheObject cacheObject = cacheObjectCache.get(key, s -> {
            CacheObject def = new CacheObject(key);
            def.setValue(defaultValue);
            return def;
        });
        if (null != cacheObject) {
            return cacheObject.getValue();
        }
        return null;
    }

    @Override
    public void remove(String key) {
        cacheObjectCache.invalidate(key);
    }

    @Override
    public void expire(String key, TimeUnit timeUnit, long expireTime) {
        CacheObject cacheObject = cacheObjectCache.getIfPresent(key);
        if (null != cacheObject) {
            cacheObject.setExpireTime(expireTime);
            cacheObject.setExpireUnit(timeUnit);
            cacheObjectCache.put(key, cacheObject);
        }
    }

    @Override
    public void expireAt(String key, Date date) {
        long dateTime = date.getTime();
        long nowTime = System.currentTimeMillis();
        if (dateTime > nowTime) {
            expire(key, TimeUnit.MILLISECONDS, (dateTime - nowTime));
        }
    }

    @Override
    public void remove(String... keys) {
        Stream.of(keys).forEach(this::remove);
    }

    @Override
    public Boolean hasKey(String key) {
        return cacheObjectCache.asMap().containsKey(key);
    }
}
