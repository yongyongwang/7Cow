package com.seven.cow.jsch.spring.boot.starter.listener;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.seven.cow.jsch.spring.boot.starter.properties.SshProperties;
import com.seven.cow.jsch.spring.boot.starter.properties.SshProxyProperties;
import com.seven.cow.spring.boot.autoconfigure.util.LoggerUtils;
import org.springframework.context.SmartLifecycle;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public class SshAgencyStrapper implements SmartLifecycle {

    private final JSch jsch = new JSch();

    @Resource
    private SshProperties sshProperties;

    private final AtomicBoolean initialized = new AtomicBoolean(false);

    private final Set<Session> sessionSet = new HashSet<>();

    @Override
    public void start() {
        if (initialized.compareAndSet(false, true)) {
            LoggerUtils.info("ssh 2 agent init!");

            List<SshProxyProperties> sshPropertiesList = sshProperties.getProxy();
            if (!CollectionUtils.isEmpty(sshPropertiesList)) {
                sshPropertiesList.forEach(properties ->
                {
                    Session session = null;
                    try {
                        session = jsch.getSession(sshProperties.getUsername(), sshProperties.getHost(), sshProperties.getPort());
                        session.setConfig("StrictHostKeyChecking", "no");
                        session.setConfig("username", sshProperties.getUsername());
                        session.setPassword(sshProperties.getPassword());
                        session.connect(sshProperties.getTimeout()); //ssh connection established!

                        //by security policy, you must connect through a fowarded port
                        session.setPortForwardingL(properties.getLocalPort(), properties.getRemoteHost(), properties.getRemotePort());
                        sessionSet.add(session);
                        LoggerUtils.info("ssh://" + sshProperties.getUsername() + ":" + sshProperties.getPassword() + "@" + sshProperties.getHost() + ":" + sshProperties.getPort() + " connect successful!");
                    } catch (JSchException e) {
                        LoggerUtils.error("ssh://" + sshProperties.getUsername() + ":" + sshProperties.getPassword() + "@" + sshProperties.getHost() + ":" + sshProperties.getPort() + " connect exception:", e);
                    }
                });

            }
        }
    }

    @Override
    public void stop() {
        initialized.getAndSet(false);
        try {
            jsch.removeAllIdentity();
            sessionSet.forEach(session ->
            {
                if (session.isConnected()) {
                    session.disconnect();
                }
            });
            sessionSet.clear();
        } catch (JSchException e) {
            LoggerUtils.error(e.getMessage(), e);
        }
    }

    @Override
    public boolean isRunning() {
        return initialized.get();
    }
}
