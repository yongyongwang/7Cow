package com.seven.cow.beans.spring.boot.starter.processor;

import com.seven.cow.beans.spring.boot.starter.annotations.OuterService;
import com.seven.cow.beans.spring.boot.starter.properties.BeansProperties;
import com.seven.cow.beans.spring.boot.starter.properties.TypeFiltersProperties;
import com.seven.cow.beans.spring.boot.starter.proxy.OuterServiceFactoryBean;
import com.seven.cow.spring.boot.autoconfigure.util.LoggerUtils;
import com.seven.cow.spring.boot.autoconfigure.util.VUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.env.Environment;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.seven.cow.beans.spring.boot.starter.util.TypeFiltersUtils.processTypeFilter;

public class BeanRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {

    private final BeansProperties beansProperties;

    private final MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory();

    private final ResourceLoader resourceLoader = new DefaultResourceLoader();

    private final AnnotationBeanNameGenerator beanNameGenerator = new AnnotationBeanNameGenerator();

    private final Environment environment;

    private final ClassLoader classLoader;

    public BeanRegistryPostProcessor(BeansProperties beansProperties, Environment environment, ClassLoader classLoader) {
        this.beansProperties = beansProperties;
        this.environment = environment;
        this.classLoader = classLoader;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        String[] beanDefinitionsNames = beanDefinitionRegistry.getBeanDefinitionNames();
        List<TypeFilter> excludeFilters = new ArrayList<>();
        List<TypeFilter> includeFilters = new ArrayList<>();
        TypeFiltersProperties excludeTypeFiltersProperties = beansProperties.getExcludeFilters();
        VUtils.choose(() -> null != excludeTypeFiltersProperties ? 0 : 1).handle(() -> {
            processTypeFilter(excludeTypeFiltersProperties, excludeFilters, this.classLoader);
        });
        TypeFiltersProperties includeTypeFiltersProperties = beansProperties.getIncludeFilters();
        VUtils.choose(() -> null != includeTypeFiltersProperties ? 0 : 1).handle(() -> {
            processTypeFilter(includeTypeFiltersProperties, includeFilters, this.classLoader);
        });
        for (String beanDefinitionsName : beanDefinitionsNames) {
            BeanDefinition beanDefinition = beanDefinitionRegistry.getBeanDefinition(beanDefinitionsName);
            String className = beanDefinition.getBeanClassName();
            try {
                if (StringUtils.isEmpty(className)) {
                    continue;
                }
                MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(className);
                for (TypeFilter tf : excludeFilters) {
                    if (tf.match(metadataReader, metadataReaderFactory)) {
                        beanDefinitionRegistry.removeBeanDefinition(beanDefinitionsName);
                        break;
                    }
                }
            } catch (IOException e) {
                LoggerUtils.error(e.getMessage(), e);
            }
        }
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(beanDefinitionRegistry,
                true, this.environment, this.resourceLoader);
        for (TypeFilter excludeFilter : excludeFilters) {
            scanner.addExcludeFilter(excludeFilter);
        }
        for (TypeFilter includeFilter : includeFilters) {
            scanner.addIncludeFilter(includeFilter);
        }
        List<String> basePackages = beansProperties.getBasePackages();
        if (!CollectionUtils.isEmpty(basePackages)) {
            for (String basePackage : basePackages) {
                Set<BeanDefinition> beanDefinitions = scanner.findCandidateComponents(basePackage);
                for (BeanDefinition beanDefinition : beanDefinitions) {
                    String beanName = beanNameGenerator.generateBeanName(beanDefinition, beanDefinitionRegistry);
                    beanDefinitionRegistry.registerBeanDefinition(beanName, beanDefinition);
                }
            }
        }
        List<String> appBasePackages = beansProperties.getAppBasePackages();
        if (!CollectionUtils.isEmpty(appBasePackages)) {
            for (String appBasePackage : appBasePackages) {
                Set<BeanDefinition> beanDefinitions = scanner.findCandidateComponents(appBasePackage);
                for (BeanDefinition beanDefinition : beanDefinitions) {
                    String beanName = beanNameGenerator.generateBeanName(beanDefinition, beanDefinitionRegistry);
                    String className = beanDefinition.getBeanClassName();
                    BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(OuterServiceFactoryBean.class);
                    BeanDefinition beanDefinitionProxy = builder.getBeanDefinition();
                    try {
                        assert className != null;
                        Class<?> clazz = ClassUtils.forName(className, this.classLoader);
                        OuterService outerService = clazz.getAnnotation(OuterService.class);
                        if (null != outerService) {
                            beanDefinitionProxy.getConstructorArgumentValues().addGenericArgumentValue(clazz);
                            beanDefinitionRegistry.registerBeanDefinition(beanName, beanDefinitionProxy);
                        }
                    } catch (ClassNotFoundException e) {
                        LoggerUtils.error(e.getMessage(), e);
                    }
                }
            }
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }
}
