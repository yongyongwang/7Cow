package com.seven.cow.event.spring.boot.starter.service.impl;

import com.seven.cow.event.spring.boot.starter.service.EventCallbackService;
import com.seven.cow.event.spring.boot.starter.service.EventService;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;

import javax.annotation.Resource;
import java.util.List;

public class EventServiceCombination implements InitializingBean {

    @Resource
    private List<EventCallbackService> eventCallbackServices;

    @Resource
    private EventService eventService;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (!CollectionUtils.isEmpty(eventCallbackServices)) {
            for (EventCallbackService eventCallbackService : eventCallbackServices) {
                Class<?> targetClass = AopUtils.getTargetClass(eventCallbackService);
                ReflectionUtils.doWithMethods(targetClass, method ->
                {
                    if (method.getName().equals("run")) {
                        String parameterType = method.getGenericParameterTypes()[0].getTypeName();
                        eventService.registerHandler(eventCallbackService.eventCode(), parameterType, eventCallbackService::run);
                    }
                }, ReflectionUtils.USER_DECLARED_METHODS);
            }
        }
    }
}
