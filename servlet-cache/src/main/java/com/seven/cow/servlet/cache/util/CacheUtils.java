package com.seven.cow.servlet.cache.util;

import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @description: TODO
 * @author：EDY
 * @date: 2022/4/12 9:19
 * @version: 1.0
 */
public abstract class CacheUtils {

    private static final ExpressionParser parser = new SpelExpressionParser();
    private static final LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();

    public static String calculateCacheKey(String expression, Method method, Object[] args, Object result) {
        Expression parseExpression = parser.parseExpression(expression);
        return parseExpression.getValue(bindParam(method, args, result), String.class);
    }

    public static Boolean calculateCacheCondition(String expression, Method method, Object[] args, Object result) {
        Expression parseExpression = parser.parseExpression(expression);
        return parseExpression.getValue(bindParam(method, args, result), boolean.class);
    }

    public static Boolean calculateCacheCondition(String condition, String unless, Method method, Object[] args, Object result) {
        return (StringUtils.isEmpty(condition) && StringUtils.isEmpty(unless))
                || ((!StringUtils.isEmpty(condition) && CacheUtils.calculateCacheCondition(condition, method, args, result)))
                || (!StringUtils.isEmpty(unless) && !CacheUtils.calculateCacheCondition(unless, method, args, result));
    }

    public static long calculateCacheExpireTime(final TimeUnit timeUnit, final long expireTime) {
        long cacheExpireTime = expireTime;
        switch (timeUnit) {
            case DAYS:
                cacheExpireTime = cacheExpireTime * 24 * 3600 * 1000000000;
                break;
            case HOURS:
                cacheExpireTime = cacheExpireTime * 3600 * 1000000000;
                break;
            case MINUTES:
                cacheExpireTime = cacheExpireTime * 60 * 1000000000;
                break;
            case SECONDS:
                cacheExpireTime = cacheExpireTime * 1000000000;
                break;
            // 毫秒
            case MILLISECONDS:
                cacheExpireTime = cacheExpireTime * 1000000;
                break;
            // 微妙
            case MICROSECONDS:
                cacheExpireTime = cacheExpireTime * 1000;
                break;
            default:
                break;
        }
        return cacheExpireTime;
    }

    private static EvaluationContext bindParam(Method method, Object[] args, Object result) {
        String[] params = discoverer.getParameterNames(method);
        EvaluationContext context = new StandardEvaluationContext();
        for (int len = 0; len < Objects.requireNonNull(params).length; len++) {
            context.setVariable(params[len], args[len]);
        }
        context.setVariable("result", result);
        return context;
    }

}
