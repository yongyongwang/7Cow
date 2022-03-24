package com.seven.cow.beans.spring.boot.starter.proxy;

import org.springframework.beans.factory.FactoryBean;


public class OuterServiceFactoryBean implements FactoryBean<Object> {

    private final Class<?> target;

    public OuterServiceFactoryBean(Class<?> target) {
        this.target = target;
    }

    @Override
    public Object getObject() throws Exception {
        return new ProxyFactory(getObjectType()).getProxyInstance();
    }

    @Override
    public Class<?> getObjectType() {
        return this.target;
    }
}
