package com.seven.cow.event.spring.boot.starter;

import com.seven.cow.event.spring.boot.starter.properties.EventExecutorProperties;
import com.seven.cow.event.spring.boot.starter.service.EventService;
import com.seven.cow.event.spring.boot.starter.service.impl.BusinessEventThreadFactory;
import com.seven.cow.event.spring.boot.starter.service.impl.EventServiceCombination;
import com.seven.cow.event.spring.boot.starter.service.impl.EventServiceImpl;
import com.seven.cow.spring.boot.autoconfigure.util.LoggerUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

@Configuration
@EnableConfigurationProperties(EventExecutorProperties.class)
public class EventAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public EventService eventService() {
        return new EventServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public EventServiceCombination eventServiceCombination() {
        return new EventServiceCombination();
    }

    @Bean
    @ConditionalOnMissingBean
    public ExecutorService businessEventExecutorService(EventExecutorProperties executorProperties) {
        RejectedExecutionHandler rejectedExecutionHandler = new ThreadPoolExecutor.CallerRunsPolicy();
        Class<RejectedExecutionHandler> rejectedExecutionHandlerClass = executorProperties.getRejectedExecutionHandler();
        if (null != rejectedExecutionHandlerClass) {
            try {
                rejectedExecutionHandler = rejectedExecutionHandlerClass.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                LoggerUtils.error(e.getMessage(), e);
            }
        }
        return new ThreadPoolExecutor(executorProperties.getCorePoolSize(), executorProperties.getMaximumPoolSize(), executorProperties.getKeepAliveTime(), TimeUnit.SECONDS, new SynchronousQueue<>(), new BusinessEventThreadFactory(executorProperties.getNamePrefix()), rejectedExecutionHandler);
    }

}
