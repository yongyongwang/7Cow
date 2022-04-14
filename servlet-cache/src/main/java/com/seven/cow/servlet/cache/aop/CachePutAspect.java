package com.seven.cow.servlet.cache.aop;

import com.seven.cow.servlet.cache.annotations.CachePut;
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

/**
 * @description: TODO
 * @author：EDY
 * @date: 2022/4/11 18:34
 * @version: 1.0
 */
@Aspect
@Order(100)
@InheritedBean
public class CachePutAspect {

    @Resource
    private CacheStorageManager cacheStorageManager;

    @Pointcut("@annotation(com.seven.cow.servlet.cache.annotations.CachePut) ")
    public void getCachePutPoint() {
    }

    @Around("getCachePutPoint()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object[] args = point.getArgs();
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Method method = methodSignature.getMethod();
        Object result = point.proceed();
        CachePut cachePut = AnnotationUtils.getAnnotation(method, CachePut.class);
        if (null != cachePut) {
            String cacheKey = cachePut.cacheName();
            if (!StringUtils.isEmpty(cachePut.key())) {
                cacheKey += ":" + CacheUtils.calculateCacheKey(cachePut.key(), method, args, result);
            }
            if (CacheUtils.calculateCacheCondition(cachePut.condition(), cachePut.unless(), method, args, result)) {
                cacheStorageManager.set(cacheKey, result, cachePut.expireUnit(), cachePut.expireTime());
            }
        }
        return result;
    }

}
