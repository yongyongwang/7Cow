package com.seven.cow.data.authorization.service.impl;

import com.seven.cow.data.authorization.constants.DataAccess;
import com.seven.cow.data.authorization.dao.DataAccessDao;
import com.seven.cow.data.authorization.entity.DataAuthorization;
import com.seven.cow.data.authorization.entity.DataObject;
import com.seven.cow.data.authorization.service.DataAccessService;
import com.seven.cow.spring.boot.autoconfigure.util.Builder;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @description: TODO
 * @author：EDY
 * @date: 2022/3/7 15:52
 * @version: 1.0
 */
public class DefaultDataAccessServiceImpl implements DataAccessService {

    @Resource
    private DataAccessDao dataAccessDao;

    @Override
    public String upsertDataAccess(String domainId, String dataId, String dataAccessId, DataAccess dataAccess) {
        DataAuthorization dataAuthorization = dataAccessDao.selectAccessByDomainIdAndDataIdAndDataAccessId(domainId, dataId, dataAccessId);
        if (null != dataAuthorization) {
            if (DataAccess.NONE.equals(dataAccess)) {
                dataAccessDao.deleteAccessByDomainIdAndDataIdAndDataAccessId(domainId, dataId, dataAccessId);
            } else {
                Builder.of(dataAuthorization)
                        .with(DataAuthorization::setDataAccess, dataAccess.getCode())
                        .build();
                dataAccessDao.updateAccessById(dataAuthorization.getId(), dataAccess.getCode());
            }
        } else {
            DataObject dataObject = dataAccessDao.selectObjectByDomainIdAndDataId(domainId, dataId);
            if (null == dataObject) {
                dataObject = Builder.of(DataObject::new)
                        .with(DataObject::setDataId, dataId)
                        .with(DataObject::setDomainId, domainId)
                        .with(DataObject::setOwnerId, dataAccessId)
                        .build();
                dataAccessDao.insertObject(dataObject);
            }
            dataAuthorization = Builder.of(DataAuthorization::new)
                    .with(DataAuthorization::setDomainId, domainId)
                    .with(DataAuthorization::setDataId, dataId)
                    .with(DataAuthorization::setDataAccessId, dataAccessId)
                    .with(DataAuthorization::setDataAccess, dataAccess.getCode())
                    .build();
            dataAccessDao.insertAccess(dataAuthorization);
        }
        return dataAuthorization.getId().toString();
    }

    @Override
    public int updateDataAccess(String domainId, String dataId, DataAccess dataAccess) {
        DataObject dataObject = dataAccessDao.selectObjectByDomainIdAndDataId(domainId, dataId);
        List<DataAuthorization> dataAuthorizations = dataAccessDao.selectAccessByDomainIdAndDataIdAndDataAccessId(domainId, dataId);
        int i = 0;
        if (null != dataObject && !CollectionUtils.isEmpty(dataAuthorizations)) {
            for (DataAuthorization dataAuthorization : dataAuthorizations) {
                if (!dataObject.getOwnerId().equalsIgnoreCase(dataAuthorization.getDataAccessId())) {
                    dataAuthorization.setDataAccess(dataAccess.getCode());
                    i += dataAccessDao.updateAccessById(dataAuthorization.getId(), dataAccess.getCode());
                }
            }
        }
        return i;
    }

    @Override
    public DataAccess takeDataAccess(String domainId, String dataId, String dataAccessId) {
        DataAuthorization dataAuthorization = dataAccessDao.selectAccessByDomainIdAndDataIdAndDataAccessId(domainId, dataId, dataAccessId);
        if (null != dataAuthorization) {
            return DataAccess.codeOf(dataAuthorization.getDataAccess());
        }
        return DataAccess.NONE;
    }

    @Override
    public Map<String, DataAccess> takeDataAccess(String domainId, String dataAccessId) {
        Map<String, DataAccess> dataAccessMap = new HashMap<>();
        List<DataAuthorization> dataAuthorizations = dataAccessDao.selectAccessByDomainIdAndDataAccessId(domainId, dataAccessId);
        if (!CollectionUtils.isEmpty(dataAuthorizations)) {
            return dataAuthorizations.stream().collect(Collectors.toMap(DataAuthorization::getDataId, dataAuthorization -> DataAccess.codeOf(dataAuthorization.getDataAccess())));
        }
        return Collections.unmodifiableMap(dataAccessMap);
    }
}
