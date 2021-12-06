package com.seven.cow.beans.spring.boot.starter.processor;

import com.seven.cow.beans.spring.boot.starter.properties.BeansProperties;
import com.seven.cow.beans.spring.boot.starter.properties.TypeFiltersProperties;
import com.seven.cow.spring.boot.autoconfigure.util.LoggerUtils;
import com.seven.cow.spring.boot.autoconfigure.util.VUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
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
import org.springframework.core.type.filter.*;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

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

    private void processTypeFilter(TypeFiltersProperties typeFiltersProperties, List<TypeFilter> typeFilters) {
        List<Class<Annotation>> annotations = typeFiltersProperties.getAnnotation();
        if (!CollectionUtils.isEmpty(annotations)) {
            for (Class<Annotation> annotation : annotations) {
                typeFilters.add(new AnnotationTypeFilter(annotation));
            }
        }
        List<Class<?>> assignables = typeFiltersProperties.getAssignable();
        if (!CollectionUtils.isEmpty(assignables)) {
            for (Class<?> assignable : assignables) {
                typeFilters.add(new AssignableTypeFilter(assignable));
            }
        }
        List<String> aspectjs = typeFiltersProperties.getAspectj();
        if (!CollectionUtils.isEmpty(aspectjs)) {
            for (String aspectj : aspectjs) {
                typeFilters.add(new AspectJTypeFilter(aspectj, this.classLoader));
            }
        }
        List<String> regexs = typeFiltersProperties.getRegex();
        if (!CollectionUtils.isEmpty(regexs)) {
            for (String regex : regexs) {
                Pattern pattern = Pattern.compile(regex);
                typeFilters.add(new RegexPatternTypeFilter(pattern));
            }
        }
        List<Class<TypeFilter>> customs = typeFiltersProperties.getCustom();
        if (!CollectionUtils.isEmpty(customs)) {
            for (Class<TypeFilter> custom : customs) {
                TypeFilter typeFilter = BeanUtils.instantiateClass(custom);
                typeFilters.add(typeFilter);
            }
        }
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        String[] beanDefinitionsNames = beanDefinitionRegistry.getBeanDefinitionNames();
        List<TypeFilter> excludeFilters = new ArrayList<>();
        List<TypeFilter> includeFilters = new ArrayList<>();
        TypeFiltersProperties excludeTypeFiltersProperties = beansProperties.getExcludeFilters();
        VUtils.choose(() -> null != excludeTypeFiltersProperties ? 0 : 1).handle(() -> {
            processTypeFilter(excludeTypeFiltersProperties, excludeFilters);
        });
        TypeFiltersProperties includeTypeFiltersProperties = beansProperties.getIncludeFilters();
        VUtils.choose(() -> null != includeTypeFiltersProperties ? 0 : 1).handle(() -> {
            processTypeFilter(includeTypeFiltersProperties, includeFilters);
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
        List<String> basePackages = beansProperties.getBasePackages();
        if (!CollectionUtils.isEmpty(basePackages)) {
            ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(beanDefinitionRegistry,
                    true, this.environment, this.resourceLoader);
            for (TypeFilter excludeFilter : excludeFilters) {
                scanner.addExcludeFilter(excludeFilter);
            }
            for (TypeFilter includeFilter : includeFilters) {
                scanner.addIncludeFilter(includeFilter);
            }
            for (String basePackage : basePackages) {
                Set<BeanDefinition> beanDefinitions = scanner.findCandidateComponents(basePackage);
                for (BeanDefinition beanDefinition : beanDefinitions) {
                    String beanName = beanNameGenerator.generateBeanName(beanDefinition, beanDefinitionRegistry);
                    beanDefinitionRegistry.registerBeanDefinition(beanName, beanDefinition);
                }
            }
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }
}
