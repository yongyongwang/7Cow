package com.seven.cow.servlet.cors;

import com.seven.cow.servlet.cors.properties.CorsProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableConfigurationProperties(CorsProperties.class)
public class ServletCorsAutoConfiguration {

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter(@Autowired CorsProperties corsProperties) {
        //1.添加CORS配置信息
        CorsConfiguration config = new CorsConfiguration();
        //1) 允许的域,不要写*，否则cookie就无法使用了
        config.setAllowedOrigins(corsProperties.getAllowedOrigins());
        //2) 是否发送Cookie信息
        config.setAllowCredentials(corsProperties.getAllowCredentials());
        //3) 允许的请求方式
        config.setAllowedMethods(corsProperties.getAllowedMethods());
        config.setMaxAge(corsProperties.getMaxAge());
        // 4）允许的头信息
        config.setAllowedHeaders(corsProperties.getAllowedHeaders());
        // 5）跨域共享的头信息
        config.setExposedHeaders(corsProperties.getExposedHeaders());
        //2.添加映射路径，我们拦截一切请求
        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
        configSource.registerCorsConfiguration(corsProperties.getPath(), config);
        configSource.setUrlDecode(true);
        //3.返回新的CorsFilter.
        //return new CorsFilter(configSource);

        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(configSource));
        bean.setOrder(corsProperties.getOrder());
        return bean;
    }

}
