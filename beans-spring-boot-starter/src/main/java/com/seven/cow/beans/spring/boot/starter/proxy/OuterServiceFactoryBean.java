package com.seven.cow.beans.spring.boot.starter.proxy;

import com.seven.cow.beans.spring.boot.starter.util.OuterServiceUtils;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.cglib.proxy.Enhancer;


public class OuterServiceFactoryBean implements FactoryBean<Object> {

    private final Class<?> target;

    public OuterServiceFactoryBean(Class<?> target) {
        this.target = target;
    }

    @Override
    public Object getObject() {
        ProxyFactory proxyFactory = new ProxyFactory(getObjectType());
        OuterServiceUtils.putOuterServiceProxy(proxyFactory);
        Enhancer en = new Enhancer();
        en.setSuperclass(target);
        en.setCallback(proxyFactory);
        return en.create();
    }

    @Override
    public Class<?> getObjectType() {
        return this.target;
    }
}
