package com.seven.cow.servlet.upload.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @description: TODO
 * @author：EDY
 * @date: 2021/11/25 19:37
 * @version: 1.0
 */
@ConfigurationProperties(prefix = "servlet.upload")
public class UploadProperties {

    /**
     * 上传地址
     */
    private String address;

    /**
     * 上传存储地址
     */
    private String storeAddress;

    /**
     * 上传组件 filter 排序号
     */
    private int order = -1;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
