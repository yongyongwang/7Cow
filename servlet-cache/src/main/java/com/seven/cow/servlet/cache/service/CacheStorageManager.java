package com.seven.cow.servlet.cache.service;

import java.util.concurrent.TimeUnit;

public interface CacheStorageManager {

    void set(String key, Object value);

    void set(String key, Object value, TimeUnit timeUnit, long timeout);

    Object get(String key);

    void remove(String key);

}
