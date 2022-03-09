package com.seven.cow.data.authorization;

import com.seven.cow.data.authorization.dao.DataAccessDao;
import com.seven.cow.data.authorization.dao.impl.DefaultDataAccessDaoImpl;
import com.seven.cow.data.authorization.service.DataAccessService;
import com.seven.cow.data.authorization.service.impl.DefaultDataAccessServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: TODO
 * @author：EDY
 * @date: 2022/3/7 14:39
 * @version: 1.0
 */
@Configuration
public class DataAuthorizationAutoConfiguration {

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
