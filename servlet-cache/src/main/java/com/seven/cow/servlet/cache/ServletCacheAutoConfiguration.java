package com.seven.cow.servlet.cache;

import com.seven.cow.servlet.cache.aop.CacheEvictAspect;
import com.seven.cow.servlet.cache.aop.CachePutAspect;
import com.seven.cow.servlet.cache.aop.CacheableAspect;
import com.seven.cow.servlet.cache.service.CacheStorageManager;
import com.seven.cow.servlet.cache.service.impl.DefaultCacheStorageManagerImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: TODO
 * @author：EDY
 * @date: 2022/4/11 17:06
 * @version: 1.0
 */
@Configuration
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

    @Bean
    @ConditionalOnMissingBean
    public CacheStorageManager cacheStorageManager() {
        return new DefaultCacheStorageManagerImpl();
    }

}
