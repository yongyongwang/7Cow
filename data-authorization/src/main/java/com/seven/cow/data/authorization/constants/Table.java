package com.seven.cow.data.authorization.constants;

/**
 * @description: TODO
 * @author：EDY
 * @date: 2022/3/9 14:49
 * @version: 1.0
 */
public interface Table {

    interface DataObject {
        String TABLE_NAME = "dt_data_object";
        String COLUMN_ID = "id";
        String COLUMN_DOMAIN_ID = "domain_id";
        String COLUMN_DATA_ID = "data_id";
        String COLUMN_OWNER_ID = "owner_id";
    }

    interface DataAccess {
        String TABLE_NAME = "dt_data_access";
        String COLUMN_ID = "id";
        String COLUMN_DOMAIN_ID = "domain_id";
        String COLUMN_DATA_ID = "data_id";
        String COLUMN_DATA_ACCESS_ID = "data_access_id";
        String COLUMN_DATA_ACCESS = "data_access";
    }

}
