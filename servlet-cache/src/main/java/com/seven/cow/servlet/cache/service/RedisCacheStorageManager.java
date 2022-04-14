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


    /**
     * 返回key的剩余的过期时间
     *
     * @param key      键
     * @param timeUnit 时间单位
     * @return 过期时间
     */
    Long getExpire(String key, TimeUnit timeUnit);

    /**
     * 修改key的名称
     *
     * @param oldKey 要修改的键值
     * @param newKey 修改后的键值
     */
    void rename(String oldKey, String newKey);

    /**
     * 将给定 key 的值设为 value ，并返回key的旧值
     *
     * @param key   键
     * @param value 值
     * @return key的旧值
     */
    Object getAndSet(String key, Object value);


}
