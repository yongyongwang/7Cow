package com.seven.cow.servlet.download.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @description: TODO
 * @author：EDY
 * @date: 2021/11/26 9:00
 * @version: 1.0
 */
@ConfigurationProperties(prefix = "servlet.download")
public class DownloadProperties {

    /**
     * 下载文件地址：文件 key 名称
     */
    private String keyName = "fileKey";

    /**
     * 下载地址
     */
    private String address = "/file/download/{fileKey}";

    /**
     * 下载存储地址
     */
    private String storeAddress = "./files";

    /**
     * 下载组件 filter 排序号
     */
    private int order = -2;

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

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
