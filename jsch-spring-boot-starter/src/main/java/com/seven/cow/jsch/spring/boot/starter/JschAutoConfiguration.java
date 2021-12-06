package com.seven.cow.jsch.spring.boot.starter;

import com.seven.cow.jsch.spring.boot.starter.listener.SshAgencyStrapper;
import com.seven.cow.jsch.spring.boot.starter.properties.SshProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: TODO
 * @author：EDY
 * @date: 2021/12/6 9:15
 * @version: 1.0
 */
@Configuration
@EnableConfigurationProperties(SshProperties.class)
public class JschAutoConfiguration {

    @Bean("x-sshAgencyStrapper")
    public SshAgencyStrapper sshAgencyStrapper() {
        return new SshAgencyStrapper();
    }

}
