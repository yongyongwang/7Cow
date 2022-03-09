package com.seven.cow.data.authorization.mapper;

import com.seven.cow.data.authorization.constants.Table;
import com.seven.cow.data.authorization.entity.DataAuthorization;
import com.seven.cow.data.authorization.entity.DataObject;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @description: TODO
 * @author：EDY
 * @date: 2022/3/9 14:28
 * @version: 1.0
 */
@Mapper
public interface DataObjectMapper {

    @Delete("delete from " + Table.DataAccess.TABLE_NAME + " where " + Table.DataAccess.COLUMN_DOMAIN_ID + "=#{domainId} and " + Table.DataAccess.COLUMN_DATA_ID + "=#{dataId} and " + Table.DataAccess.COLUMN_DATA_ACCESS_ID + "=#{dataAccessId}")
    int deleteAccessByDomainIdAndDataIdAndDataAccessId(@Param("domainId") String domainId, @Param("dataId") String dataId, @Param("dataAccessId") String dataAccessId);

    @Select("select " + Table.DataAccess.ID + " as id,"
            + Table.DataAccess.COLUMN_DATA_ACCESS_ID + " as dataAccessId,"
            + Table.DataAccess.COLUMN_DATA_ACCESS + " as dataAccess,"
            + Table.DataAccess.COLUMN_DATA_ID + " as dataId,"
            + Table.DataAccess.COLUMN_DOMAIN_ID + " as domainId from " +
            Table.DataAccess.TABLE_NAME + " where " + Table.DataAccess.COLUMN_DOMAIN_ID + "=#{domainId} and "
            + Table.DataAccess.COLUMN_DATA_ID + "=#{dataId} and " + Table.DataAccess.COLUMN_DATA_ACCESS_ID + "=#{dataAccessId}")
    DataAuthorization selectAccessByDomainIdAndDataIdAndDataAccessId(String domainId, String dataId, String dataAccessId);

    @Select("select " + Table.DataAccess.ID + " as id,"
            + Table.DataAccess.COLUMN_DATA_ACCESS_ID + " as dataAccessId,"
            + Table.DataAccess.COLUMN_DATA_ACCESS + " as dataAccess,"
            + Table.DataAccess.COLUMN_DATA_ID + " as dataId,"
            + Table.DataAccess.COLUMN_DOMAIN_ID + " as domainId from " +
            Table.DataAccess.TABLE_NAME + " where " + Table.DataAccess.COLUMN_DOMAIN_ID + "=#{domainId} and "
            + Table.DataAccess.COLUMN_DATA_ID + "=#{dataId} ")
    List<DataAuthorization> selectAccessByDomainIdAndDataIdAndDataAccessId(String domainId, String dataId);

    @Select("select " + Table.DataAccess.ID + " as id,"
            + Table.DataAccess.COLUMN_DATA_ACCESS_ID + " as dataAccessId,"
            + Table.DataAccess.COLUMN_DATA_ACCESS + " as dataAccess,"
            + Table.DataAccess.COLUMN_DATA_ID + " as dataId,"
            + Table.DataAccess.COLUMN_DOMAIN_ID + " as domainId from " +
            Table.DataAccess.TABLE_NAME + " where " + Table.DataAccess.COLUMN_DOMAIN_ID + "=#{domainId} "
            + "and " + Table.DataAccess.COLUMN_DATA_ACCESS_ID + "=#{dataAccessId}")
    List<DataAuthorization> selectAccessByDomainIdAndDataAccessId(String domainId, String dataAccessId);

    @Update("update " + Table.DataAccess.TABLE_NAME + " set " + Table.DataAccess.COLUMN_DATA_ACCESS + "=#{dataAccess}" +
            " where " + Table.DataAccess.ID + " in ")
    int updateAccessByIds(List<Long> ids, Integer dataAccess);

    int deleteAccessByIds(List<Long> ids);

    @Insert("insert into " + Table.DataAccess.TABLE_NAME + " values (" + Table.DataAccess.ID
            + "," + Table.DataAccess.COLUMN_DOMAIN_ID + ")" + "")
    int insertAccess(DataAuthorization dataAuthorization);

    DataObject selectObjectByDomainIdAndDataId(String domainId, String dataId);

    int insertObject(DataObject dataObject);

    int updateObject(Long id, String ownerId);
}
