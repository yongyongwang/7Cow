package com.seven.cow.beans.spring.boot.starter.proxy;


import com.seven.cow.beans.spring.boot.starter.util.OuterServiceUtils;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

public class ProxyFactory implements MethodInterceptor {

    private final Class<?> target;

    private Object object;

    public ProxyFactory(Class<?> target) {
        this.target = target;
    }

    public <T> T getProxyInstance() {
        Enhancer en = new Enhancer();
        en.setSuperclass(target);
        en.setCallback(this);
        return (T) en.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        if (null == object) {
            object = OuterServiceUtils.take(target);
        }
        return ReflectionUtils.invokeMethod(method, object, objects);
    }
}
