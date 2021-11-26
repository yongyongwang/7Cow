package com.seven.cow.servlet.download;

import com.seven.cow.servlet.download.filters.FileDownloadFilter;
import com.seven.cow.servlet.download.properties.DownloadProperties;
import com.seven.cow.servlet.download.service.DownloadFileService;
import com.seven.cow.servlet.download.service.impl.DefaultDownloadFileServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: TODO
 * @author：EDY
 * @date: 2021/11/26 9:02
 * @version: 1.0
 */
@Configuration
@EnableConfigurationProperties(DownloadProperties.class)
public class ServletDownloadAutoConfiguration {

    @Bean("x-fileDownloadFilter")
    public FileDownloadFilter fileDownloadFilter() {
        return new FileDownloadFilter();
    }

    @Bean
    @ConditionalOnMissingBean
    public DownloadFileService downloadFileService() {
        return new DefaultDownloadFileServiceImpl();
    }

}
