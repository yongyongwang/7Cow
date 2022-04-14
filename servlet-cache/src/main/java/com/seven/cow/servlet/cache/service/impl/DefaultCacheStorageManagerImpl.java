package com.seven.cow.servlet.cache.service.impl;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;
import com.seven.cow.servlet.cache.entity.CacheObject;
import com.seven.cow.servlet.cache.properties.CacheProperties;
import com.seven.cow.servlet.cache.service.CacheStorageManager;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.concurrent.TimeUnit;

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
                        switch (timeUnit) {
                            case DAYS:
                                expireTime = expireTime * 24 * 3600 * 1000000000;
                                break;
                            case HOURS:
                                expireTime = expireTime * 3600 * 1000000000;
                                break;
                            case MINUTES:
                                expireTime = expireTime * 60 * 1000000000;
                                break;
                            case SECONDS:
                                expireTime = expireTime * 1000000000;
                                break;
                            // 毫秒
                            case MILLISECONDS:
                                expireTime = expireTime * 1000000;
                                break;
                            // 微妙
                            case MICROSECONDS:
                                expireTime = expireTime * 1000;
                                break;
                            default:
                                break;
                        }
                        return expireTime;
                    }

                    @Override
                    public long expireAfterUpdate(@NonNull String key, @NonNull CacheObject value, long currentTime, @NonNegative long currentDuration) {
                        return currentDuration;
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
}
