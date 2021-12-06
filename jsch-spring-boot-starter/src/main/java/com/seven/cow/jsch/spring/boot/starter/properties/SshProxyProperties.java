package com.seven.cow.jsch.spring.boot.starter.properties;

/**
 * @description: TODO
 * @author：EDY
 * @date: 2021/12/6 9:32
 * @version: 1.0
 */
public class SshProxyProperties {

    /**
     * 代理通信本地端口
     */
    private Integer localPort;

    /**
     * 代理通信远程地址
     */
    private String remoteHost;

    /**
     * 代理通信远程端口
     */
    private Integer remotePort;

    public Integer getLocalPort() {
        return localPort;
    }

    public void setLocalPort(Integer localPort) {
        this.localPort = localPort;
    }

    public String getRemoteHost() {
        return remoteHost;
    }

    public void setRemoteHost(String remoteHost) {
        this.remoteHost = remoteHost;
    }

    public Integer getRemotePort() {
        return remotePort;
    }

    public void setRemotePort(Integer remotePort) {
        this.remotePort = remotePort;
    }
}
