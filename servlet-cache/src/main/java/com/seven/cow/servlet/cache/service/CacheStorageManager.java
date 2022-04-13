package com.seven.cow.servlet.cache.service;

import java.util.concurrent.TimeUnit;

public interface CacheStorageManager {

    void set(String key, Object value);

    void set(String key, Object value, TimeUnit timeUnit, long expireTime);

    Object get(String key);

    Object getOrDefault(String key, Object defaultValue);

    void remove(String key);

}
