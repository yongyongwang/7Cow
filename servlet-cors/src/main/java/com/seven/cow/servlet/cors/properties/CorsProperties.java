package com.seven.cow.servlet.cors.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpMethod;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ConfigurationProperties(prefix = "servlet.cors")
public class CorsProperties {

    /**
     * 跨域 filter 排序号
     */
    private int order = -100;

    /**
     * 允许的域
     */
    private List<String> allowedOrigins = Collections.singletonList("*");

    /**
     * 允许的请求方式
     */
    private List<String> allowedMethods =
            Arrays.asList(HttpMethod.GET.name()
                    , HttpMethod.HEAD.name()
                    , HttpMethod.POST.name()
                    , HttpMethod.DELETE.name()
                    , HttpMethod.PUT.name()
                    , HttpMethod.OPTIONS.name());

    /**
     * 允许的头部信息
     */
    private List<String> allowedHeaders;

    /**
     * 跨域资源共享响应头
     */
    private List<String> exposedHeaders;

    /**
     * 是否发送Cookie信息
     */
    private Boolean allowCredentials = true;

    /**
     * OPTIONS 跨域缓存时间
     */
    private Long maxAge = 3600L;

    /**
     * cors 拦截路径
     */
    private String path = "/**";

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public List<String> getAllowedOrigins() {
        return allowedOrigins;
    }

    public void setAllowedOrigins(List<String> allowedOrigins) {
        this.allowedOrigins = allowedOrigins;
    }

    public List<String> getAllowedMethods() {
        return allowedMethods;
    }

    public void setAllowedMethods(List<String> allowedMethods) {
        this.allowedMethods = allowedMethods;
    }

    public List<String> getAllowedHeaders() {
        return allowedHeaders;
    }

    public void setAllowedHeaders(List<String> allowedHeaders) {
        this.allowedHeaders = allowedHeaders;
    }

    public List<String> getExposedHeaders() {
        return exposedHeaders;
    }

    public void setExposedHeaders(List<String> exposedHeaders) {
        this.exposedHeaders = exposedHeaders;
    }

    public Boolean getAllowCredentials() {
        return allowCredentials;
    }

    public void setAllowCredentials(Boolean allowCredentials) {
        this.allowCredentials = allowCredentials;
    }

    public Long getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Long maxAge) {
        this.maxAge = maxAge;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
