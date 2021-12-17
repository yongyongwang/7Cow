package com.seven.cow.event.spring.boot.starter.listener;

import com.seven.cow.event.spring.boot.starter.processor.BusinessEventListenerAnnotationBeanPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

public class EventRegistryApplicationRunListener implements SpringApplicationRunListener {

    public EventRegistryApplicationRunListener(SpringApplication application, String[] args) {

    }

    @Override
    public void starting() {


    }

    @Override
    public void environmentPrepared(ConfigurableEnvironment environment) {

    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        context.getBeanFactory().addBeanPostProcessor(new BusinessEventListenerAnnotationBeanPostProcessor());
    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {

    }

    @Override
    public void started(ConfigurableApplicationContext context) {

    }

    @Override
    public void running(ConfigurableApplicationContext context) {

    }

    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {

    }

}
