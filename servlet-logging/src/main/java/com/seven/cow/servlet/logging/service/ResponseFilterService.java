package com.seven.cow.servlet.logging.service;

public interface ResponseFilterService {

    byte[] handle(int responseStatus, byte[] rspBytes);

}
