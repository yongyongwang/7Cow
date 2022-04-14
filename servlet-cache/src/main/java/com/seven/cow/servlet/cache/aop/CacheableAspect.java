package com.seven.cow.servlet.cache.aop;

import com.seven.cow.servlet.cache.annotations.Cacheable;
import com.seven.cow.servlet.cache.service.CacheStorageManager;
import com.seven.cow.servlet.cache.util.CacheUtils;
import com.seven.cow.spring.boot.autoconfigure.annotations.InheritedBean;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.lang.reflect.Method;

@Aspect
@Order(100)
@InheritedBean
public class CacheableAspect {

    @Resource
    private CacheStorageManager cacheStorageManager;

    @Pointcut("@annotation(com.seven.cow.servlet.cache.annotations.Cacheable) ")
    public void getCacheablePoint() {
    }

    @Around("getCacheablePoint()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object[] args = point.getArgs();
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Method method = methodSignature.getMethod();
        Cacheable cacheable = AnnotationUtils.getAnnotation(method, Cacheable.class);
        if (null != cacheable) {
            String cacheKey = cacheable.cacheName();
            if (!StringUtils.isEmpty(cacheable.key())) {
                cacheKey += ":" + CacheUtils.calculateCacheKey(cacheable.key(), method, args, null);
            }
            Object cacheValue = cacheStorageManager.get(cacheKey);
            if (null != cacheValue) {
                return cacheValue;
            } else {
                Object result = point.proceed();
                if (CacheUtils.calculateCacheCondition(cacheable.condition(), cacheable.unless(), method, args, result)) {
                    cacheStorageManager.set(cacheKey, result, cacheable.expireUnit(), cacheable.expireTime());
                }
                return result;
            }
        }
        return point.proceed();
    }

}
