package com.seven.cow.servlet.cache.service;

import java.util.concurrent.TimeUnit;

public interface RedisCacheStorageManager extends CacheStorageManager {

    /**
     * 移动当前数据库中的key到dbIndex数据库
     *
     * @param key     键
     * @param dbIndex 数据库
     * @return 是否成功
     */
    Boolean move(String key, int dbIndex);

    /**
     * 移除key的过期时间，key将持久保持
     *
     * @param key 键
     * @return 是否成功
     */
    Boolean persist(String key);


    Long getExpire(String key, TimeUnit timeUnit);

    void rename(String oldKey, String newKey);


}
