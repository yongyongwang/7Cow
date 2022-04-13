package com.seven.cow.servlet.cache.entity;

import java.util.concurrent.TimeUnit;

/**
 * @description: TODO
 * @author：EDY
 * @date: 2022/4/13 16:14
 * @version: 1.0
 */
public class CacheObject {

    private final String key;
    private Object value;
    private TimeUnit expireUnit = TimeUnit.HOURS;
    private long expireTime = 2;

    public CacheObject(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public TimeUnit getExpireUnit() {
        return expireUnit;
    }

    public void setExpireUnit(TimeUnit expireUnit) {
        this.expireUnit = expireUnit;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

}
