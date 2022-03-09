package com.seven.cow.data.authorization.service;

import com.seven.cow.data.authorization.constants.DataAccess;

import java.util.Map;

public interface DataAccessService {

    /**
     * 新增/更新数据对象访问权限
     * DataAccess.NONE ,清空权限记录
     *
     * @param domainId     数据所属域
     * @param dataId       数据对象
     * @param dataAccessId 数据访问对象
     * @param dataAccess   数据访问权限 如果数据对象是首次创建，该参数设置无效，默认重置为 DataAccess.READ_WRITE_EXECUTE
     * @return 数据权限Id
     */
    String upsertDataAccess(String domainId, String dataId, String dataAccessId, DataAccess dataAccess);

    /**
     * 批量更新数据对象访问数据权限,无法更新数据对象拥有者权限
     *
     * @param domainId   数据所属域
     * @param dataId     数据对象
     * @param dataAccess 数据访问权限
     * @return 修改的数据访问对象数量
     */
    int updateDataAccess(String domainId, String dataId, DataAccess dataAccess);

    /**
     * 获取数据对象访问权限
     *
     * @param domainId     数据所属域
     * @param dataId       数据对象
     * @param dataAccessId 数据访问对象
     * @return 数据访问对象
     */
    DataAccess takeDataAccess(String domainId, String dataId, String dataAccessId);

    /**
     * 获取数据访问对象可访问的数据对象权限列表
     *
     * @param domainId     数据所属域
     * @param dataAccessId 数据访问对象
     * @return 数据对象列表 key:数据Id value: dataAccess 权限
     */
    Map<String, DataAccess> takeDataAccess(String domainId, String dataAccessId);

}
