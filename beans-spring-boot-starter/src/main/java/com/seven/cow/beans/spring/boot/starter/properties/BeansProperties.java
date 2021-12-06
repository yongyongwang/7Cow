package com.seven.cow.beans.spring.boot.starter.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.List;

@ConfigurationProperties(prefix = "spring.beans")
public class BeansProperties {

    /**
     * 配置额外包扫描路径
     */
    private List<String> basePackages;

    /**
     * 包含过滤器，匹配的条件为 true 注册为 bean
     */
    @NestedConfigurationProperty
    private TypeFiltersProperties includeFilters;

    /**
     * 排除过滤器
     */
    @NestedConfigurationProperty
    private TypeFiltersProperties excludeFilters;

    public List<String> getBasePackages() {
        return basePackages;
    }

    public void setBasePackages(List<String> basePackages) {
        this.basePackages = basePackages;
    }

    public TypeFiltersProperties getIncludeFilters() {
        return includeFilters;
    }

    public void setIncludeFilters(TypeFiltersProperties includeFilters) {
        this.includeFilters = includeFilters;
    }

    public TypeFiltersProperties getExcludeFilters() {
        return excludeFilters;
    }

    public void setExcludeFilters(TypeFiltersProperties excludeFilters) {
        this.excludeFilters = excludeFilters;
    }

}
