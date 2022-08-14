package com.seven.cow.servlet.logging.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "servlet.logging")
public class LoggingProperties {

    /**
     * 是否打印记录请求日志
     */
    private Boolean print = true;

    /**
     * 拦截模式 默认filter，拦截请求 log 模式是指日志打印的拦截
     */
    private Mode mode = Mode.filter;

    /**
     * aop 打印controller出入参
     */
    private Boolean aopPrint = true;

    /**
     * 不记录日志请求表达式列表
     */
    private List<String> excludePatterns;

    /**
     * 只记录日志请求表达式列表
     */
    private List<String> includePatterns;

    /**
     * 日志打印 filter 排序号
     */
    private int order = 0;

    /**
     * 是否屏蔽异常请求状态码，默认不屏蔽 false
     */
    private boolean alwaysOk = false;

    public Boolean getPrint() {
        return print;
    }

    public void setPrint(Boolean print) {
        this.print = print;
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public Boolean getAopPrint() {
        return aopPrint;
    }

    public void setAopPrint(Boolean aopPrint) {
        this.aopPrint = aopPrint;
    }

    public List<String> getExcludePatterns() {
        return excludePatterns;
    }

    public void setExcludePatterns(List<String> excludePatterns) {
        this.excludePatterns = excludePatterns;
    }

    public List<String> getIncludePatterns() {
        return includePatterns;
    }

    public void setIncludePatterns(List<String> includePatterns) {
        this.includePatterns = includePatterns;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public boolean isAlwaysOk() {
        return alwaysOk;
    }

    public void setAlwaysOk(boolean alwaysOk) {
        this.alwaysOk = alwaysOk;
    }
}
