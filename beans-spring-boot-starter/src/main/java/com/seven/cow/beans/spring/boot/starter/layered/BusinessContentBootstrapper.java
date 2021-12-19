package com.seven.cow.beans.spring.boot.starter.layered;

import com.seven.cow.beans.spring.boot.starter.empty.BeanEmpty;
import com.seven.cow.beans.spring.boot.starter.properties.BeansProperties;
import com.seven.cow.beans.spring.boot.starter.properties.TypeFiltersProperties;
import com.seven.cow.spring.boot.autoconfigure.util.Builder;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.SmartLifecycle;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.BusinessClassPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.seven.cow.beans.spring.boot.starter.util.TypeFiltersUtils.processTypeFilter;

public class BusinessContentBootstrapper implements SmartLifecycle, ApplicationContextAware {

    private final AtomicBoolean initialized = new AtomicBoolean(false);
    private ConfigurableApplicationContext context;

    @Resource
    private BeansProperties beansProperties;

    @Override
    public boolean isAutoStartup() {
        return null == context.getParent() || (Objects.equals(context.getParent().getId(), "bootstrap"));
    }

    @Override
    public void stop(Runnable callback) {
        callback.run();
    }

    @Override
    public void start() {
        if (initialized.compareAndSet(false, true)) {
            ConfigurableEnvironment environment = context.getEnvironment();
            TypeFiltersProperties includeTypeFilters = beansProperties.getIncludeFilters();
            TypeFiltersProperties excludeTypeFilters = beansProperties.getExcludeFilters();
            List<TypeFilter> includeFilters = new ArrayList<>();
            List<TypeFilter> excludeFilters = new ArrayList<>();
            processTypeFilter(includeTypeFilters, includeFilters, context.getClassLoader());
            processTypeFilter(excludeTypeFilters, excludeFilters, context.getClassLoader());
            List<String> appBasePackages = beansProperties.getAppBasePackages();
            if (!CollectionUtils.isEmpty(appBasePackages)) {
                for (String appBasePackage : appBasePackages) {
                    RootBeanDefinition def = new RootBeanDefinition(BusinessClassPostProcessor.class);
                    MutablePropertyValues propertyValues = new MutablePropertyValues();
                    propertyValues.add("basePackage", appBasePackage);
                    propertyValues.add("beansProperties", beansProperties);
                    def.setPropertyValues(propertyValues);
                    AnnotationConfigApplicationContext appContent = Builder.of(AnnotationConfigApplicationContext::new)
                            .with(AnnotationConfigApplicationContext::setEnvironment, environment)
                            .with(AnnotationConfigApplicationContext::setParent, context)
                            .with(AnnotationConfigApplicationContext::setClassLoader, context.getClassLoader())
                            .with(AnnotationConfigApplicationContext::register, BeanEmpty.class)
                            .with(AnnotationConfigApplicationContext::registerBeanDefinition, AnnotationConfigUtils.CONFIGURATION_ANNOTATION_PROCESSOR_BEAN_NAME, def)
                            .build();
                    appContent.refresh();
                }
            }
        }
    }

    @Override
    public void stop() {
        initialized.getAndSet(false);
    }

    @Override
    public boolean isRunning() {
        return initialized.get();
    }

    @Override
    public int getPhase() {
        return 1000;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = (ConfigurableApplicationContext) applicationContext;
    }

}
