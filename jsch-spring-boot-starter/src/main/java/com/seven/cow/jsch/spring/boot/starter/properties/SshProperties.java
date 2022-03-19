package com.seven.cow.jsch.spring.boot.starter.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.List;

/**
 * @description: TODO
 * @author：EDY
 * @date: 2021/12/6 9:26
 * @version: 1.0
 */
@ConfigurationProperties(prefix = "ssh")
public class SshProperties {

    /**
     * ssh 地址
     */
    private String host;

    /**
     * ssh 端口
     */
    private Integer port;

    /**
     * ssh 用户名
     */
    private String username;

    /**
     * ssh 密码
     */
    private String password;

    /**
     * ssh 连接超时时间
     */
    private Integer timeout;

    /**
     * ssh 跳板机代理配置信息
     */
    @NestedConfigurationProperty
    private List<SshProxyProperties> proxy;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public List<SshProxyProperties> getProxy() {
        return proxy;
    }

    public void setProxy(List<SshProxyProperties> proxy) {
        this.proxy = proxy;
    }
}
