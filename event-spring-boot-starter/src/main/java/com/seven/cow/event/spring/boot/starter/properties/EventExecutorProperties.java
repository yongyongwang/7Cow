package com.seven.cow.event.spring.boot.starter.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.concurrent.RejectedExecutionHandler;

@ConfigurationProperties(prefix = "event.executor.pool")
public class EventExecutorProperties {

    /**
     * 核心保留线程数
     */
    private Integer corePoolSize = 10;

    /*
     * 最大线程数
     */
    private Integer maximumPoolSize = 50;

    /**
     * 线程池中线程存活时间
     */
    private Long keepAliveTime = 60L;

    /**
     * 线程池名称前缀
     */
    private String namePrefix = "business-event-";

    /**
     * 线程池拒绝处理类
     */
    private Class<RejectedExecutionHandler> rejectedExecutionHandler;

    public Integer getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(Integer corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public Integer getMaximumPoolSize() {
        return maximumPoolSize;
    }

    public void setMaximumPoolSize(Integer maximumPoolSize) {
        this.maximumPoolSize = maximumPoolSize;
    }

    public Long getKeepAliveTime() {
        return keepAliveTime;
    }

    public void setKeepAliveTime(Long keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
    }

    public String getNamePrefix() {
        return namePrefix;
    }

    public void setNamePrefix(String namePrefix) {
        this.namePrefix = namePrefix;
    }

    public Class<RejectedExecutionHandler> getRejectedExecutionHandler() {
        return rejectedExecutionHandler;
    }

    public void setRejectedExecutionHandler(Class<RejectedExecutionHandler> rejectedExecutionHandler) {
        this.rejectedExecutionHandler = rejectedExecutionHandler;
    }
}
