package com.seven.cow.servlet.cache.service;

import org.springframework.data.redis.core.*;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
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

    // region hash 操作
    void hPut(String key, Object hashKey, Object value);

    void hPutAll(String key, Map<Object, Object> maps);

    Object hGet(String key, Object hashKey);

    Long hDelete(String key, Object... hashKeys);

    Boolean hHasKey(String key, Object hashKey);

    Map<Object, Object> hEntries(String key);

    Long hSize(String key);
    // endregion hash 操作

    // region list 操作
    void lSet(String key, long index, Object value);

    Long lRemove(String key, long count, Object value);

    void lLPush(String key, Object value);

    void lLPushAll(String key, Collection<Object> values);

    Object lLPop(String key);

    void lRPush(String key, Object value);

    void lRPushAll(String key, Collection<Object> values);

    Object lRPop(String key);

    Long lSize(String key);

    Object index(String key, long index);
    // endregion list 操作

    // region set 操作
    void sAdd(String key, Object... values);

    void sRemove(String key, Object... values);

    void sMove(String key, Object value, String destKey);

    Long sSize(String key);

    Boolean sIsMember(String key, Object value);

    Set<Object> sMembers(String key);
    // endregion set 操作

    // region zSet 操作
    Boolean zAdd(String key, Object value, double score);

    Long zRemove(String key, Object... values);

    Double zIncrementScore(String key, Object value, double delta);

    Long zRank(String key, Object value);

    Long zReverseRank(String key, Object value);

    Set<Object> zRange(String key, long start, long end);

    Set<Object> zRangeByScore(String key, double min, double max);

    Long zCount(String key, double min, double max);

    Long zSize(String key);

    Long zZCard(String key);

    Double zScore(String key, Object value);

    Long zRemoveRange(String key, long start, long end);

    Long zRemoveRangeByScore(String key, double min, double max);
    // endregion zSet 操作

    ValueOperations<Object, Object> valueOps();

    HashOperations<Object, Object, Object> hashOps();

    ListOperations<Object, Object> listOps();

    SetOperations<Object, Object> setOps();

    ZSetOperations<Object, Object> zSetOps();

}
