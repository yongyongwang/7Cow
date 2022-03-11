package com.seven.cow.data.authorization.service.impl;

import com.seven.cow.data.authorization.constants.DataAccess;
import com.seven.cow.data.authorization.dao.DataAccessDao;
import com.seven.cow.data.authorization.entity.DataAuthorization;
import com.seven.cow.data.authorization.entity.DataObject;
import com.seven.cow.data.authorization.service.DataAccessService;
import com.seven.cow.spring.boot.autoconfigure.util.Builder;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
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
    public String transferDataAccess(String domainId, String dataId, String ownerId) {
        DataObject dataObject = dataAccessDao.selectObjectByDomainIdAndDataId(domainId, dataId);
        if (null != dataObject && !dataObject.getOwnerId().equalsIgnoreCase(ownerId)) {
            DataAuthorization dataAuthorization = dataAccessDao.selectAccessByDomainIdAndDataIdAndDataAccessId(domainId, dataId, dataObject.getOwnerId());
            if (null != dataAuthorization) {
                this.upsertDataAccess(dataAuthorization.getDomainId(), dataAuthorization.getDataId(), dataAuthorization.getDataAccessId(), DataAccess.NONE);
            }
            dataAccessDao.updateObject(dataObject.getId(), ownerId);
            return this.upsertDataAccess(dataObject.getDomainId(), dataObject.getDataId(), ownerId, DataAccess.READ_WRITE_EXECUTE);
        }
        return null;
    }

    @Override
    public String upsertDataAccess(String domainId, String dataId, String dataAccessId, DataAccess dataAccess) {
        DataAuthorization dataAuthorization = dataAccessDao.selectAccessByDomainIdAndDataIdAndDataAccessId(domainId, dataId, dataAccessId);
        DataObject dataObject = dataAccessDao.selectObjectByDomainIdAndDataId(domainId, dataId);
        if (null != dataAuthorization) {
            if (null != dataObject && !dataObject.getOwnerId().equalsIgnoreCase(dataAuthorization.getDataAccessId())) {
                if (DataAccess.NONE.equals(dataAccess)) {
                    dataAccessDao.deleteAccessByDomainIdAndDataIdAndDataAccessId(domainId, dataId, dataAccessId);
                } else {
                    Builder.of(dataAuthorization)
                            .with(DataAuthorization::setDataAccess, dataAccess.getCode())
                            .build();
                    dataAccessDao.updateAccessByIds(Collections.singletonList(dataAuthorization.getId()), dataAuthorization.getDataAccess());
                }
            }
        } else {
            if (null == dataObject) {
                dataObject = Builder.of(DataObject::new)
                        .with(DataObject::setDataId, dataId)
                        .with(DataObject::setDomainId, domainId)
                        .with(DataObject::setOwnerId, dataAccessId)
                        .build();
                dataAccessDao.insertObject(dataObject);

                dataAuthorization = Builder.of(DataAuthorization::new)
                        .with(DataAuthorization::setDomainId, domainId)
                        .with(DataAuthorization::setDataId, dataId)
                        .with(DataAuthorization::setDataAccessId, dataAccessId)
                        .with(DataAuthorization::setDataAccess, DataAccess.READ_WRITE_EXECUTE.getCode())
                        .build();
            } else {
                dataAuthorization = Builder.of(DataAuthorization::new)
                        .with(DataAuthorization::setDomainId, domainId)
                        .with(DataAuthorization::setDataId, dataId)
                        .with(DataAuthorization::setDataAccessId, dataAccessId)
                        .with(DataAuthorization::setDataAccess, dataAccess.getCode())
                        .build();
            }
            if (DataAccess.NONE.getCode() != dataAuthorization.getDataAccess()) {
                dataAccessDao.insertAccess(dataAuthorization);
            }
        }
        return dataAuthorization.getId() == null ? null : dataAuthorization.getId().toString();
    }

    @Override
    public int updateDataAccess(String domainId, String dataId, DataAccess dataAccess) {
        DataObject dataObject = dataAccessDao.selectObjectByDomainIdAndDataId(domainId, dataId);
        List<DataAuthorization> dataAuthorizations = dataAccessDao.selectAccessListByDomainIdAndDataId(domainId, dataId);
        int i = 0;
        if (null != dataObject && !CollectionUtils.isEmpty(dataAuthorizations)) {
            List<Long> ids = dataAuthorizations.stream().filter(dataAuthorization ->
                    !dataObject.getOwnerId().equalsIgnoreCase(dataAuthorization.getDataAccessId()))
                    .map(DataAuthorization::getId).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(ids)) {
                if (!DataAccess.NONE.equals(dataAccess)) {
                    i = dataAccessDao.updateAccessByIds(ids, dataAccess.getCode());
                } else {
                    i = dataAccessDao.deleteAccessByIds(ids);
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
        List<DataAuthorization> dataAuthorizations = dataAccessDao.selectAccessByDomainIdAndDataAccessId(domainId, dataAccessId);
        if (!CollectionUtils.isEmpty(dataAuthorizations)) {
            return dataAuthorizations.stream().collect(Collectors.toMap(DataAuthorization::getDataId,
                    dataAuthorization -> DataAccess.codeOf(dataAuthorization.getDataAccess())));
        }
        return Collections.emptyMap();
    }
}
