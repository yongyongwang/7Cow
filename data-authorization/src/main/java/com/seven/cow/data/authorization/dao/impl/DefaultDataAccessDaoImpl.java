package com.seven.cow.data.authorization.dao.impl;

import com.seven.cow.data.authorization.dao.DataAccessDao;
import com.seven.cow.data.authorization.entity.DataAuthorization;
import com.seven.cow.data.authorization.entity.DataObject;

import java.util.List;

/**
 * @description: TODO
 * @author：EDY
 * @date: 2022/3/8 10:55
 * @version: 1.0
 */
public class DefaultDataAccessDaoImpl implements DataAccessDao {

    @Override
    public int deleteAccessByDomainIdAndDataIdAndDataAccessId(String domainId, String dataId, String dataAccessId) {
        return 0;
    }

    @Override
    public DataAuthorization selectAccessByDomainIdAndDataIdAndDataAccessId(String domainId, String dataId, String dataAccessId) {
        return null;
    }

    @Override
    public List<DataAuthorization> selectAccessByDomainIdAndDataIdAndDataAccessId(String domainId, String dataId) {
        return null;
    }

    @Override
    public List<DataAuthorization> selectAccessByDomainIdAndDataAccessId(String domainId, String dataAccessId) {
        return null;
    }

    @Override
    public int updateAccessByIds(List<Long> ids, Integer dataAccess) {
        return 0;
    }

    @Override
    public int deleteAccessByIds(List<Long> ids) {
        return 0;
    }

    @Override
    public int insertAccess(DataAuthorization dataAuthorization) {
        return 0;
    }

    @Override
    public DataObject selectObjectByDomainIdAndDataId(String domainId, String dataId) {
        return null;
    }

    @Override
    public int insertObject(DataObject dataObject) {
        return 0;
    }
}
