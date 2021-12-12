package com.seven.cow.event.spring.boot.starter.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "event.executor.pool")
public class EventExecutorProperties
{
    private Integer corePoolSize = 10;

    private Integer maximumPoolSize = 50;

    private Long keepAliveTime = 60L;

    private String namePrefix = "business-event-";

    public Integer getCorePoolSize()
    {
        return corePoolSize;
    }

    public void setCorePoolSize(Integer corePoolSize)
    {
        this.corePoolSize = corePoolSize;
    }

    public Integer getMaximumPoolSize()
    {
        return maximumPoolSize;
    }

    public void setMaximumPoolSize(Integer maximumPoolSize)
    {
        this.maximumPoolSize = maximumPoolSize;
    }

    public Long getKeepAliveTime()
    {
        return keepAliveTime;
    }

    public void setKeepAliveTime(Long keepAliveTime)
    {
        this.keepAliveTime = keepAliveTime;
    }

    public String getNamePrefix()
    {
        return namePrefix;
    }

    public void setNamePrefix(String namePrefix)
    {
        this.namePrefix = namePrefix;
    }
}
