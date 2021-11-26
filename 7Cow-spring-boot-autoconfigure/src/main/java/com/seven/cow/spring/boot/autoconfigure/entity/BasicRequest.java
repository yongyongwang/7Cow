package com.seven.cow.spring.boot.autoconfigure.entity;


import com.seven.cow.spring.boot.autoconfigure.util.CurrentContext;

/**
 * 请求基础数据
 */
public class BasicRequest {

    /**
     * 获取头部参数
     *
     * @param key 头部 key
     * @return 头部值
     */
    public final String getHeader(String key) {
        return CurrentContext.getHeader(key);
    }

    /**
     * 获取查询参数值
     *
     * @param parameterName 参数名称
     * @return 参数值
     */
    public final String getParameter(String parameterName) {
        return CurrentContext.getParameter(parameterName);
    }

}
