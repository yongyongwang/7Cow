package com.seven.cow.data.authorization.dao;

import com.seven.cow.data.authorization.entity.DataAuthorization;
import com.seven.cow.data.authorization.entity.DataObject;

import java.util.List;

public interface DataAccessDao {

    int deleteAccessByDomainIdAndDataIdAndDataAccessId(String domainId, String dataId, String dataAccessId);

    DataAuthorization selectAccessByDomainIdAndDataIdAndDataAccessId(String domainId, String dataId, String dataAccessId);

    List<DataAuthorization> selectAccessByDomainIdAndDataIdAndDataAccessId(String domainId, String dataId);

    List<DataAuthorization> selectAccessByDomainIdAndDataAccessId(String domainId, String dataAccessId);

    int updateAccessById(Long id, Integer dataAccess);

    int insertAccess(DataAuthorization dataAuthorization);

    DataObject selectObjectByDomainIdAndDataId(String domainId, String dataId);

    int insertObject(DataObject dataObject);

}
