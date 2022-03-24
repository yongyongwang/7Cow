package com.seven.cow.beans.spring.boot.starter.util;


import com.seven.cow.beans.spring.boot.starter.proxy.ProxyFactory;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public abstract class OuterServiceUtils {

    private static final Map<Class<?>, ProxyFactory> proxyFactoryHashMap = new HashMap<>();

    public static void putOuterServiceProxy(ProxyFactory proxyFactory) {
        proxyFactoryHashMap.put(proxyFactory.getTarget(), proxyFactory);
    }

    public static void setOuterServiceProxyBean(Class<?> beanClass, Object beanInstance) {
        ProxyFactory proxyFactory = proxyFactoryHashMap.get(beanClass);
        if (null == proxyFactory) {
            return;
        }
        Field field = ReflectionUtils.findField(ProxyFactory.class, "object");
        if (null != field) {
            ReflectionUtils.makeAccessible(field);
            ReflectionUtils.setField(field, proxyFactory, beanInstance);
        }
        proxyFactoryHashMap.remove(beanClass);
    }

}
