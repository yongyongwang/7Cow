package com.seven.cow.servlet.cache.service.impl;

import com.seven.cow.servlet.cache.service.CacheStorageManager;

import java.util.concurrent.TimeUnit;

/**
 * @description: TODO
 * @author：EDY
 * @date: 2022/4/12 9:13
 * @version: 1.0
 */
public class DefaultCacheStorageManagerImpl implements CacheStorageManager {
    @Override
    public void set(String key, Object value) {

    }

    @Override
    public void set(String key, Object value, TimeUnit timeUnit, long timeout) {

    }

    @Override
    public Object get(String key) {
        return null;
    }

    @Override
    public void remove(String key) {

    }
}
