package com.seven.cow.data.authorization.properties;

import com.seven.cow.data.authorization.constants.Table;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @description: TODO
 * @author：EDY
 * @date: 2022/3/15 19:41
 * @version: 1.0
 */
@ConfigurationProperties(prefix = "data.authorization")
public class DataAuthorizationProperties {

    /**
     * 是否初始化表
     */
    private Boolean enabled;

    /**
     * 数据对象表名称
     */
    private String dataObjectTableName = Table.DataObject.TABLE_NAME;

    /**
     * 数据对象访问表名称
     */
    private String dataAccessTableName = Table.DataAccess.TABLE_NAME;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getDataObjectTableName() {
        return dataObjectTableName;
    }

    public void setDataObjectTableName(String dataObjectTableName) {
        this.dataObjectTableName = dataObjectTableName;
    }

    public String getDataAccessTableName() {
        return dataAccessTableName;
    }

    public void setDataAccessTableName(String dataAccessTableName) {
        this.dataAccessTableName = dataAccessTableName;
    }
}
