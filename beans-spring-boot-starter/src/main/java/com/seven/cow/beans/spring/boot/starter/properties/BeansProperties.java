package com.seven.cow.beans.spring.boot.starter.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@ConfigurationProperties(prefix = "spring.beans")
public class BeansProperties {

    /**
     * 排除过滤器
     */
    @NestedConfigurationProperty
    private TypeFiltersProperties excludeFilters;

    public TypeFiltersProperties getExcludeFilters() {
        return excludeFilters;
    }

    public void setExcludeFilters(TypeFiltersProperties excludeFilters) {
        this.excludeFilters = excludeFilters;
    }

}
