package com.seven.cow.data.authorization.entity;

/**
 * @description: 数据对象
 * @author：EDY
 * @date: 2022/3/8 14:23
 * @version: 1.0
 */
public class DataObject {

    /**
     * 主键
     */
    private Long id;

    /**
     * 数据Id
     */
    private String dataId;

    /**
     * 数据域 Id
     */
    private String domainId;

    /**
     * 数据拥有者
     */
    private String ownerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getDomainId() {
        return domainId;
    }

    public void setDomainId(String domainId) {
        this.domainId = domainId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }
}
