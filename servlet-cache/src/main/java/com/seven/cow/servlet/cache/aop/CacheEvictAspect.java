package com.seven.cow.servlet.cache.aop;

import com.seven.cow.servlet.cache.annotations.CacheEvict;
import com.seven.cow.servlet.cache.service.CacheStorageManager;
import com.seven.cow.servlet.cache.util.CacheUtils;
import com.seven.cow.spring.boot.autoconfigure.annotations.InheritedBean;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * @description: TODO
 * @author：EDY
 * @date: 2022/4/11 18:38
 * @version: 1.0
 */
@Aspect
@Order(100)
@InheritedBean
public class CacheEvictAspect {

    @Resource
    private CacheStorageManager cacheStorageManager;

    @Pointcut("@annotation(com.seven.cow.servlet.cache.annotations.CacheEvict) ")
    public void getCacheEvictPoint() {
    }

    @Around("getCacheEvictPoint()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object[] args = point.getArgs();
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Method method = methodSignature.getMethod();
        Object result = point.proceed();
        CacheEvict cacheEvict = method.getAnnotation(CacheEvict.class);
        if (null != cacheEvict) {
            String cacheKey = cacheEvict.cacheName();
            if (!StringUtils.isEmpty(cacheEvict.key())) {
                cacheKey += ":" + CacheUtils.calculateCacheKey(cacheEvict.key(), method, args, result);
            }
            cacheStorageManager.remove(cacheKey);
        }
        return result;
    }


}
