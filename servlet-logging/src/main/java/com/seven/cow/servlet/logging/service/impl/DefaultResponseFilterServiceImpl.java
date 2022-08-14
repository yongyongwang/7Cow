package com.seven.cow.servlet.logging.service.impl;

import com.seven.cow.servlet.logging.service.ResponseFilterService;

public class DefaultResponseFilterServiceImpl implements ResponseFilterService {
    @Override
    public byte[] handle(int responseStatus, byte[] rspBytes) {
        return rspBytes;
    }
}
