package com.seven.cow.event.spring.boot.starter.processor;

import com.seven.cow.event.spring.boot.starter.annotations.BusinessEventListener;
import com.seven.cow.event.spring.boot.starter.service.EventRunnable;
import com.seven.cow.event.spring.boot.starter.util.EventUtils;
import com.seven.cow.spring.boot.autoconfigure.util.LoggerUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.PriorityOrdered;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * @ClassName BusinessEventListenerAnnotationBeanPostProcessor
 * @Date 2021/3/16 8:34
 * @Auther wangyongyong
 * @Version 1.0
 * @Description 业务事件消息监听初始化
 * @see BeanPostProcessor
 * @see BusinessEventListener
 */
public class BusinessEventListenerAnnotationBeanPostProcessor implements BeanPostProcessor, PriorityOrdered {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> targetClass = AopUtils.getTargetClass(bean);
        ReflectionUtils.doWithMethods(targetClass, method ->
        {
            Collection<BusinessEventListener> listenerAnnotations = findListenerAnnotations(method);
            if (listenerAnnotations.size() > 0) {
                BusinessEventListener annotation = method.getAnnotation(BusinessEventListener.class);
                String eventCode = annotation.value();
                String messageType = method.getGenericParameterTypes()[0].getTypeName();
                EventUtils.registerEvent((eventCode + ":" + messageType), new EventRunnable() {
                    @Override
                    public void run(Object message) {
                        try {
                            method.invoke(bean, message);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            LoggerUtils.error(e.getMessage(), e);
                        }
                    }

                    @Override
                    public int getOrder() {
                        return annotation.order();
                    }
                });
            }
        }, ReflectionUtils.USER_DECLARED_METHODS);
        return bean;
    }

    private Collection<BusinessEventListener> findListenerAnnotations(AnnotatedElement element) {
        BusinessEventListener[] businessEventListeners = element.getAnnotationsByType(BusinessEventListener.class);
        if (null != businessEventListeners) {
            return Arrays.asList(businessEventListeners);
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
