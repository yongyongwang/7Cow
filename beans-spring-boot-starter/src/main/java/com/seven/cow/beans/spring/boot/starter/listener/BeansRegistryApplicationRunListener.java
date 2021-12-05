package com.seven.cow.beans.spring.boot.starter.listener;

import com.seven.cow.beans.spring.boot.starter.processor.BeanRegistryPostProcessor;
import com.seven.cow.beans.spring.boot.starter.properties.BeansProperties;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

public class BeansRegistryApplicationRunListener implements SpringApplicationRunListener {

    public BeansRegistryApplicationRunListener(SpringApplication application, String[] args) {

    }

    @Override
    public void starting() {


    }

    @Override
    public void environmentPrepared(ConfigurableEnvironment environment) {

    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        BindResult<BeansProperties> bindResult = Binder.get(context.getEnvironment()).bind("spring.beans", Bindable.of(BeansProperties.class));
        bindResult.ifBound(beansProperties -> {
            ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
            beanFactory.registerSingleton("beansProperties", beansProperties);
            beanFactory.registerSingleton("x-beanDefinitionRegistryPostProcessor",new BeanRegistryPostProcessor(beansProperties));
        });
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
