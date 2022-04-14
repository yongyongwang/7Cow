package com.seven.cow.servlet.cache;

import com.seven.cow.servlet.cache.aop.CacheEvictAspect;
import com.seven.cow.servlet.cache.aop.CachePutAspect;
import com.seven.cow.servlet.cache.aop.CacheableAspect;
import com.seven.cow.servlet.cache.config.RedisConfig;
import com.seven.cow.servlet.cache.properties.CacheProperties;
import com.seven.cow.servlet.cache.service.CacheStorageManager;
import com.seven.cow.servlet.cache.service.impl.DefaultCacheStorageManagerImpl;
import com.seven.cow.servlet.cache.service.impl.RedisCacheStorageManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @description: TODO
 * @author：EDY
 * @date: 2022/4/11 17:06
 * @version: 1.0
 */
@Configuration
@EnableConfigurationProperties(CacheProperties.class)
@Import(RedisConfig.class)
public class ServletCacheAutoConfiguration {

    @ConditionalOnClass(name = "org.aspectj.lang.annotation.Aspect")
    @Bean("x-cacheableAspect")
    @ConditionalOnMissingBean
    public CacheableAspect cacheableAspect() {
        return new CacheableAspect();
    }

    @ConditionalOnClass(name = "org.aspectj.lang.annotation.Aspect")
    @Bean("x-cacheEvictAspect")
    @ConditionalOnMissingBean
    public CacheEvictAspect cacheEvictAspect() {
        return new CacheEvictAspect();
    }

    @ConditionalOnClass(name = "org.aspectj.lang.annotation.Aspect")
    @Bean("x-cachePutAspect")
    @ConditionalOnMissingBean
    public CachePutAspect cachePutAspect() {
        return new CachePutAspect();
    }

    @Bean("cacheStorageManager")
    @ConditionalOnMissingBean
    @ConditionalOnClass(name = "org.springframework.data.redis.core.RedisOperations")
    public CacheStorageManager redisCacheStorageManager() {
        return new RedisCacheStorageManagerImpl();
    }

    @Bean("cacheStorageManager")
    @ConditionalOnMissingBean
    public CacheStorageManager defaultCacheStorageManager(@Autowired CacheProperties cacheProperties) {
        return new DefaultCacheStorageManagerImpl(cacheProperties);
    }

}
