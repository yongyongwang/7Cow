package com.seven.cow.data.authorization.initization;

import com.seven.cow.data.authorization.constants.Table;
import com.seven.cow.data.authorization.mapper.DataObjectMapper;
import com.seven.cow.data.authorization.properties.DataAuthorizationProperties;
import org.springframework.boot.CommandLineRunner;

import javax.annotation.Resource;

/**
 * @description: TODO
 * @author：EDY
 * @date: 2022/3/15 18:01
 * @version: 1.0
 */
public class TableDataInitRunner implements CommandLineRunner {

    @Resource
    private DataObjectMapper dataObjectMapper;

    @Resource
    private DataAuthorizationProperties dataAuthorizationProperties;

    @Override
    public void run(String... args) throws Exception {
        if (dataAuthorizationProperties.getEnabled()) {
            dataObjectMapper.initTableDataObject(dataAuthorizationProperties.getDataObjectTableName());
            dataObjectMapper.initTableDataAccess(dataAuthorizationProperties.getDataAccessTableName());
        }
    }
}
