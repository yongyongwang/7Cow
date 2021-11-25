package com.seven.cow.servlet.upload;

import com.seven.cow.servlet.upload.filters.FileUploadFilter;
import com.seven.cow.servlet.upload.properties.UploadProperties;
import com.seven.cow.servlet.upload.service.UploadFileService;
import com.seven.cow.servlet.upload.service.impl.DefaultUploadFileServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: TODO
 * @author：EDY
 * @date: 2021/11/25 19:37
 * @version: 1.0
 */
@Configuration
@EnableConfigurationProperties(UploadProperties.class)
public class ServletUploadAutoConfiguration {

    @Bean("x-fileUploadFilter")
    public FileUploadFilter fileUploadFilter() {
        return new FileUploadFilter();
    }

    @Bean
    @ConditionalOnMissingBean
    public UploadFileService uploadFileService() {
        return new DefaultUploadFileServiceImpl();
    }

}
