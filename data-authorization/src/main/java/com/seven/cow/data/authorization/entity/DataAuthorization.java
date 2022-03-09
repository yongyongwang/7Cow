package com.seven.cow.data.authorization.entity;

/**
 * @description: 数据授权信息
 * @author：EDY
 * @date: 2022/3/7 14:55
 * @version: 1.0
 */
public class DataAuthorization {

    /**
     * 主键
     */
    private Long id;

    /**
     * 数据对象
     */
    private String dataId;

    /**
     * 数据域 Id
     */
    private String domainId;

    /**
     * 数据访问者
     */
    private String dataAccessId;

    /**
     * 授权范围
     */
    private int dataAccess;

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

    public String getDataAccessId() {
        return dataAccessId;
    }

    public void setDataAccessId(String dataAccessId) {
        this.dataAccessId = dataAccessId;
    }

    public int getDataAccess() {
        return dataAccess;
    }

    public void setDataAccess(int dataAccess) {
        this.dataAccess = dataAccess;
    }
}
