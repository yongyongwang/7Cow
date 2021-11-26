package com.seven.cow.servlet.logging.service.impl;

import com.seven.cow.servlet.logging.service.ResponseFilterService;

/**
 * @description: TODO
 * @author：EDY
 * @date: 2021/11/26 12:49
 * @version: 1.0
 */
public class DefaultResponseFilterServiceImpl implements ResponseFilterService {
    @Override
    public byte[] handle(int responseStatus, byte[] rspBytes) {
        return rspBytes;
    }
}
