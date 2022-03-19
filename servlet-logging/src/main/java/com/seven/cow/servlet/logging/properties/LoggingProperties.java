package com.seven.cow.servlet.logging.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @description: 日志打印配置
 * @author：EDY
 * @date: 2021/11/24 18:12
 * @version: 1.0
 */
@ConfigurationProperties(prefix = "servlet.logging")
public class LoggingProperties {

    /**
     * 是否打印记录请求日志
     */
    private Boolean print = true;

    /**
     * 不记录日志请求表达式列表
     */
    private List<String> excludePatterns;

    /**
     * 日志打印 filter 排序号
     */
    private int order = 0;

    /**
     * 是否屏蔽异常请求状态码，默认全部返回 404
     */
    private boolean alwaysOk = true;

    public Boolean getPrint() {
        return print;
    }

    public void setPrint(Boolean print) {
        this.print = print;
    }

    public List<String> getExcludePatterns() {
        return excludePatterns;
    }

    public void setExcludePatterns(List<String> excludePatterns) {
        this.excludePatterns = excludePatterns;
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
