package com.seven.cow.servlet.cache.service.impl;

import com.seven.cow.servlet.cache.service.RedisCacheStorageManager;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @description: TODO
 * @author：EDY
 * @date: 2022/4/14 8:42
 * @version: 1.0
 */
public class RedisCacheStorageManagerImpl implements RedisCacheStorageManager {

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

    @Override
    public void expireAt(String key, Date date) {
        redisTemplate.expireAt(key, date);
    }

    @Override
    public void remove(String... keys) {
        redisTemplate.delete(Arrays.asList(keys));
    }

    @Override
    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    @Override
    public Boolean move(String key, int dbIndex) {
        return redisTemplate.move(key, dbIndex);
    }

    @Override
    public Boolean persist(String key) {
        return redisTemplate.persist(key);
    }

    @Override
    public Long getExpire(String key, TimeUnit timeUnit) {
        return redisTemplate.getExpire(key, timeUnit);
    }

    @Override
    public void rename(String oldKey, String newKey) {
        redisTemplate.rename(oldKey, newKey);
    }

    @Override
    public Object getAndSet(String key, Object value) {
        return redisTemplate.opsForValue().getAndSet(key, value);
    }

    @Override
    public void hPut(String key, Object hashKey, Object value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    @Override
    public void hPutAll(String key, Map<Object, Object> maps) {
        redisTemplate.opsForHash().putAll(key, maps);
    }

    @Override
    public Object hGet(String key, Object hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    @Override
    public Long hDelete(String key, Object... hashKeys) {
        return redisTemplate.opsForHash().delete(key, hashKeys);
    }

    @Override
    public Boolean hHasKey(String key, Object hashKey) {
        return redisTemplate.opsForHash().hasKey(key, hashKey);
    }

    @Override
    public Map<Object, Object> hEntries(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    @Override
    public Long hSize(String key) {
        return redisTemplate.opsForHash().size(key);
    }

    @Override
    public void lSet(String key, long index, Object value) {
        redisTemplate.opsForList().set(key, index, value);
    }

    @Override
    public Long lRemove(String key, long count, Object value) {
        return redisTemplate.opsForList().remove(key, count, value);
    }

    @Override
    public void lLPush(String key, Object value) {
        redisTemplate.opsForList().leftPush(key, value);
    }

    @Override
    public void lLPushAll(String key, Collection<Object> values) {
        redisTemplate.opsForList().leftPushAll(key, values);
    }

    @Override
    public Object lLPop(String key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    @Override
    public void lRPush(String key, Object value) {
        redisTemplate.opsForList().rightPush(key, value);
    }

    @Override
    public void lRPushAll(String key, Collection<Object> values) {
        redisTemplate.opsForList().rightPushAll(key, values);
    }

    @Override
    public Object lRPop(String key) {
        return redisTemplate.opsForList().rightPop(key);
    }

    @Override
    public Long lSize(String key) {
        return redisTemplate.opsForList().size(key);
    }

    @Override
    public Object index(String key, long index) {
        return redisTemplate.opsForList().index(key, index);
    }

    @Override
    public void sAdd(String key, Object... values) {
        redisTemplate.opsForSet().add(key, values);
    }

    @Override
    public void sRemove(String key, Object... values) {
        redisTemplate.opsForSet().remove(key, values);
    }

    @Override
    public void sMove(String key, Object value, String destKey) {
        redisTemplate.opsForSet().move(key, value, destKey);
    }

    @Override
    public Long sSize(String key) {
        return redisTemplate.opsForSet().size(key);
    }

    @Override
    public Boolean sIsMember(String key, Object value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }

    @Override
    public Set<Object> sMembers(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    @Override
    public Boolean zAdd(String key, Object value, double score) {
        return redisTemplate.opsForZSet().add(key, value, score);
    }

    @Override
    public Long zRemove(String key, Object... values) {
        return redisTemplate.opsForZSet().remove(key, values);
    }

    @Override
    public Double zIncrementScore(String key, Object value, double delta) {
        return redisTemplate.opsForZSet().incrementScore(key, value, delta);
    }

    @Override
    public Long zRank(String key, Object value) {
        return redisTemplate.opsForZSet().rank(key, value);
    }

    @Override
    public Long zReverseRank(String key, Object value) {
        return redisTemplate.opsForZSet().reverseRank(key, value);
    }

    @Override
    public Set<Object> zRange(String key, long start, long end) {
        return redisTemplate.opsForZSet().range(key, start, end);
    }

    @Override
    public Set<Object> zRangeByScore(String key, double min, double max) {
        return redisTemplate.opsForZSet().rangeByScore(key, min, max);
    }

    @Override
    public Long zCount(String key, double min, double max) {
        return redisTemplate.opsForZSet().count(key, min, max);
    }

    @Override
    public Long zSize(String key) {
        return redisTemplate.opsForZSet().size(key);
    }

    @Override
    public Long zZCard(String key) {
        return redisTemplate.opsForZSet().zCard(key);
    }

    @Override
    public Double zScore(String key, Object value) {
        return redisTemplate.opsForZSet().score(key, value);
    }

    @Override
    public Long zRemoveRange(String key, long start, long end) {
        return redisTemplate.opsForZSet().removeRange(key, start, end);
    }

    @Override
    public Long zRemoveRangeByScore(String key, double min, double max) {
        return redisTemplate.opsForZSet().removeRangeByScore(key, min, max);
    }


}
