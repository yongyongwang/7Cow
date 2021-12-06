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

    public TypeFiltersProperties getExcludeFilters() {
        return excludeFilters;
    }

    public void setExcludeFilters(TypeFiltersProperties excludeFilters) {
        this.excludeFilters = excludeFilters;
    }

}
