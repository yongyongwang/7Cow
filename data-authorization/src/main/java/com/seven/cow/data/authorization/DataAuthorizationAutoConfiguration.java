package com.seven.cow.data.authorization;

import com.seven.cow.data.authorization.dao.DataAccessDao;
import com.seven.cow.data.authorization.dao.impl.DefaultDataAccessDaoImpl;
import com.seven.cow.data.authorization.initization.TableDataInitRunner;
import com.seven.cow.data.authorization.mapper.DataObjectMapper;
import com.seven.cow.data.authorization.properties.DataAuthorizationProperties;
import com.seven.cow.data.authorization.service.DataAccessService;
import com.seven.cow.data.authorization.service.impl.DefaultDataAccessServiceImpl;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: TODO
 * @author：EDY
 * @date: 2022/3/7 14:39
 * @version: 1.0
 */
@Configuration
@AutoConfigureAfter({MybatisAutoConfiguration.class})
@EnableConfigurationProperties(DataAuthorizationProperties.class)
public class DataAuthorizationAutoConfiguration {

    @Bean
    public MapperFactoryBean<DataObjectMapper> dataObjectMapper(@Autowired SqlSessionFactory sqlSessionFactory, @Autowired SqlSessionTemplate sqlSessionTemplate) {
        MapperFactoryBean<DataObjectMapper> mapperFactoryBean = new MapperFactoryBean<>(DataObjectMapper.class);
        mapperFactoryBean.setSqlSessionFactory(sqlSessionFactory);
        mapperFactoryBean.setSqlSessionTemplate(sqlSessionTemplate);
        return mapperFactoryBean;
    }

    @Bean
    @ConditionalOnProperty(value = "data.authorization.enabled", havingValue = "true")
    public CommandLineRunner tableInitCommandLineRunner() {
        return new TableDataInitRunner();
    }

    @Bean
    @ConditionalOnMissingBean
    public DataAccessDao dataAccessDao() {
        return new DefaultDataAccessDaoImpl();
    }

    @Bean
    public DataAccessService dataAccessService() {
        return new DefaultDataAccessServiceImpl();
    }

}
