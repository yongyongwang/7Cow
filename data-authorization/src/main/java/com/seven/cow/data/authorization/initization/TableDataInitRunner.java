package com.seven.cow.data.authorization.initization;

import com.seven.cow.data.authorization.mapper.DataObjectMapper;
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

    @Override
    public void run(String... args) throws Exception {
        dataObjectMapper.initTableDataObject();
        dataObjectMapper.initTableDataAccess();
    }
}
