package com.seven.cow.spring.boot.autoconfigure.constant;

/**
 * @description: TODO
 * @author：EDY
 * @date: 2021/11/23 19:40
 * @version: 1.0
 */
public final class Conts {

    public final static String SPLIT_COLON = ":";
    public final static String SPLIT_COMMA = ",";
    public final static String SPLIT_SEMICOLON = ";";
    public final static String STRING_EMPTY = "";

    public final static String REQUEST_OK = "ok";
    public final static String REQUEST_FAIL = "fail";

    public final static String X_CURRENT_HEADERS = "x-request" + SPLIT_COLON + "headers";

    public final static String X_CURRENT_REQUEST_BODY = "x-request" + SPLIT_COLON + "payload";

    public final static String X_CURRENT_REQUEST_EXCEPTION = "x-request" + SPLIT_COLON + "exception";

    public final static String X_CURRENT_REQUEST_PARAMETERS = "x-request" + SPLIT_COLON + "parameters";

}
