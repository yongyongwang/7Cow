package com.seven.cow.data.authorization.dao.impl;

import com.seven.cow.data.authorization.dao.DataAccessDao;
import com.seven.cow.data.authorization.entity.DataAuthorization;
import com.seven.cow.data.authorization.entity.DataObject;
import com.seven.cow.data.authorization.mapper.DataObjectMapper;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description: TODO
 * @author：EDY
 * @date: 2022/3/8 10:55
 * @version: 1.0
 */
public class DefaultDataAccessDaoImpl implements DataAccessDao {

    @Resource
    private DataObjectMapper dataObjectMapper;

    @Override
    public int deleteAccessByDomainIdAndDataIdAndDataAccessId(String domainId, String dataId, String dataAccessId) {
        return dataObjectMapper.deleteAccessByDomainIdAndDataIdAndDataAccessId(domainId, dataId, dataAccessId);
    }

    @Override
    public DataAuthorization selectAccessByDomainIdAndDataIdAndDataAccessId(String domainId, String dataId, String dataAccessId) {
        return dataObjectMapper.selectAccessByDomainIdAndDataIdAndDataAccessId(domainId, dataId, dataAccessId);
    }

    @Override
    public List<DataAuthorization> selectAccessListByDomainIdAndDataId(String domainId, String dataId) {
        return dataObjectMapper.selectAccessListByDomainIdAndDataId(domainId, dataId);
    }

    @Override
    public List<DataAuthorization> selectAccessByDomainIdAndDataAccessId(String domainId, String dataAccessId) {
        return dataObjectMapper.selectAccessListByDomainIdAndDataAccessId(domainId, dataAccessId);
    }

    @Override
    public int updateAccessByIds(List<Long> ids, Integer dataAccess) {
        return dataObjectMapper.updateAccessByIds(ids, dataAccess);
    }

    @Override
    public int deleteAccessByIds(List<Long> ids) {
        return dataObjectMapper.deleteAccessByIds(ids);
    }

    @Override
    public int insertAccess(DataAuthorization dataAuthorization) {
        return dataObjectMapper.insertAccess(dataAuthorization);
    }

    @Override
    public DataObject selectObjectByDomainIdAndDataId(String domainId, String dataId) {
        return dataObjectMapper.selectObjectByDomainIdAndDataId(domainId, dataId);
    }

    @Override
    public int insertObject(DataObject dataObject) {
        return dataObjectMapper.insertObject(dataObject);
    }

    @Override
    public int updateObject(Long id, String ownerId) {
        return dataObjectMapper.updateObject(id, ownerId);
    }
}
