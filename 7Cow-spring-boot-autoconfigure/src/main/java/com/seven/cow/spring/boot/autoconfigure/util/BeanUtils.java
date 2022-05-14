package com.seven.cow.spring.boot.autoconfigure.util;

import com.seven.cow.spring.boot.autoconfigure.constant.Conts;
import net.sf.cglib.beans.BeanCopier;
import net.sf.cglib.beans.BeanMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description: TODO
 * @author：EDY
 * @date: 2022/3/16 9:12
 * @version: 1.0
 */
public final class BeanUtils {

    private static final Logger logger = LoggerFactory.getLogger(BeanUtils.class);

    private static final Map<String, BeanCopier> beanCopierMap = new ConcurrentHashMap<>();

    public static <S, T> void copyProperties(S source, T target) {
        String key = source.getClass().getTypeName() + Conts.SPLIT_COLON + target.getClass().getTypeName();
        BeanCopier beanCopier;
        if (beanCopierMap.containsKey(key)) {
            beanCopier = beanCopierMap.get(key);
        } else {
            beanCopier = BeanCopier.create(source.getClass(), target.getClass(), false);
            beanCopierMap.put(key, beanCopier);
        }
        beanCopier.copy(source, target, null);
    }

    public static <S> Map<String, Object> convertToMap(S source) {
        BeanMap beanMap = BeanMap.create(source);
        Map<String, Object> objectMap = new HashMap<>(beanMap.size());
        for (Object key : beanMap.keySet()) {
            objectMap.put(key.toString(), beanMap.get(key));
        }
        return objectMap;
    }

    public static <T> T convertToBean(Map<String, Object> map, Class<T> targetClass) {
        T bean = newInstance(targetClass);
        BeanMap beanMap = BeanMap.create(bean);
        beanMap.putAll(map);
        return bean;
    }

    public static <T> T newInstance(Class<T> targetClass) {
        try {
            return targetClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }


}
