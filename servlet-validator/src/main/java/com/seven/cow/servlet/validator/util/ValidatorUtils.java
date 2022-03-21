package com.seven.cow.servlet.validator.util;

import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;
import java.util.Objects;

public abstract class ValidatorUtils {

    private static final ExpressionParser parser = new SpelExpressionParser();
    private static final LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();

    public static <T> T valid(String expression, Method method, Object[] args, Class<T> returnClass) {
        Expression parseExpression = parser.parseExpression(expression);
        return parseExpression.getValue(bindParam(method, args), returnClass);
    }

    private static EvaluationContext bindParam(Method method, Object[] args) {
        String[] params = discoverer.getParameterNames(method);
        EvaluationContext context = new StandardEvaluationContext();
        for (int len = 0; len < Objects.requireNonNull(params).length; len++) {
            context.setVariable(params[len], args[len]);
        }
        return context;
    }

}
