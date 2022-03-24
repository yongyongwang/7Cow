package com.seven.cow.beans.spring.boot.starter.proxy;


import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

public class ProxyFactory implements MethodInterceptor {

    public Class<?> getTarget() {
        return target;
    }

    private final Class<?> target;

    private Object object;

    public ProxyFactory(Class<?> target) {
        this.target = target;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) {
        return ReflectionUtils.invokeMethod(method, object, objects);
    }
}
