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

    @Update({"CREATE TABLE IF NOT EXISTS `" + "#{tableName}" + "` (" +
            "`" + Table.DataObject.COLUMN_ID + "` bigint auto_increment NOT NULL," +
            "`" + Table.DataObject.COLUMN_DOMAIN_ID + "` varchar(255) default NULL," +
            "`" + Table.DataObject.COLUMN_DATA_ID + "` varchar(255) default NULL," +
            "`" + Table.DataObject.COLUMN_OWNER_ID + "` varchar(255) default NULL," +
            "PRIMARY KEY (`" + Table.DataObject.COLUMN_ID + "`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;"})
    void initTableDataObject(@Param("tableName") String tableName);

    @Update({"CREATE TABLE IF NOT EXISTS `" + "#{tableName}" + "` (" +
            "`" + Table.DataAccess.COLUMN_ID + "` bigint auto_increment NOT NULL," +
            "`" + Table.DataAccess.COLUMN_DOMAIN_ID + "` varchar(255) default NULL," +
            "`" + Table.DataAccess.COLUMN_DATA_ID + "` varchar(255) default NULL," +
            "`" + Table.DataAccess.COLUMN_DATA_ACCESS_ID + "` varchar(255) default NULL," +
            "`" + Table.DataAccess.COLUMN_DATA_ACCESS + "` int default NULL," +
            "PRIMARY KEY (`" + Table.DataObject.COLUMN_ID + "`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;"})
    void initTableDataAccess(@Param("tableName") String tableName);

    @Delete("delete from " + Table.DataAccess.TABLE_NAME + " where " + Table.DataAccess.COLUMN_DOMAIN_ID + "=#{domainId} and " + Table.DataAccess.COLUMN_DATA_ID + "=#{dataId} and " + Table.DataAccess.COLUMN_DATA_ACCESS_ID + "=#{dataAccessId}")
    int deleteAccessByDomainIdAndDataIdAndDataAccessId(@Param("domainId") String domainId, @Param("dataId") String dataId, @Param("dataAccessId") String dataAccessId);

    @Select("select " + Table.DataAccess.COLUMN_ID + " as id,"
            + Table.DataAccess.COLUMN_DATA_ACCESS_ID + " as dataAccessId,"
            + Table.DataAccess.COLUMN_DATA_ACCESS + " as dataAccess,"
            + Table.DataAccess.COLUMN_DATA_ID + " as dataId,"
            + Table.DataAccess.COLUMN_DOMAIN_ID + " as domainId from " +
            Table.DataAccess.TABLE_NAME + " where " + Table.DataAccess.COLUMN_DOMAIN_ID + "=#{domainId} and "
            + Table.DataAccess.COLUMN_DATA_ID + "=#{dataId} and " + Table.DataAccess.COLUMN_DATA_ACCESS_ID + "=#{dataAccessId}")
    DataAuthorization selectAccessByDomainIdAndDataIdAndDataAccessId(@Param("domainId") String domainId, @Param("dataId") String dataId, @Param("dataAccessId") String dataAccessId);

    @Select("select " + Table.DataAccess.COLUMN_ID + " as id,"
            + Table.DataAccess.COLUMN_DATA_ACCESS_ID + " as dataAccessId,"
            + Table.DataAccess.COLUMN_DATA_ACCESS + " as dataAccess,"
            + Table.DataAccess.COLUMN_DATA_ID + " as dataId,"
            + Table.DataAccess.COLUMN_DOMAIN_ID + " as domainId from " +
            Table.DataAccess.TABLE_NAME + " where " + Table.DataAccess.COLUMN_DOMAIN_ID + "=#{domainId} and "
            + Table.DataAccess.COLUMN_DATA_ID + "=#{dataId} ")
    List<DataAuthorization> selectAccessListByDomainIdAndDataId(@Param("domainId") String domainId, @Param("dataId") String dataId);

    @Select("select " + Table.DataAccess.COLUMN_ID + " as id,"
            + Table.DataAccess.COLUMN_DATA_ACCESS_ID + " as dataAccessId,"
            + Table.DataAccess.COLUMN_DATA_ACCESS + " as dataAccess,"
            + Table.DataAccess.COLUMN_DATA_ID + " as dataId,"
            + Table.DataAccess.COLUMN_DOMAIN_ID + " as domainId from " +
            Table.DataAccess.TABLE_NAME + " where " + Table.DataAccess.COLUMN_DOMAIN_ID + "=#{domainId} "
            + "and " + Table.DataAccess.COLUMN_DATA_ACCESS_ID + "=#{dataAccessId}")
    List<DataAuthorization> selectAccessListByDomainIdAndDataAccessId(@Param("domainId") String domainId, @Param("dataAccessId") String dataAccessId);

    @Update({"<script>", "update " + Table.DataAccess.TABLE_NAME + " set " + Table.DataAccess.COLUMN_DATA_ACCESS + "=#{dataAccess}" +
            " where " + Table.DataAccess.COLUMN_ID + " in ", "<foreach collection=\"ids\" item=\"id\" index=\"index\" open=\"(\" separator=\",\" close=\")\">",
            "#{id}",
            "</foreach>",
            "</script>"})
    int updateAccessByIds(@Param("ids") List<Long> ids, @Param("dataAccess") Integer dataAccess);

    @Delete({"<script>",
            "delete from " + Table.DataAccess.TABLE_NAME + " where id in",
            "<foreach collection=\"ids\" item=\"id\" index=\"index\" open=\"(\" separator=\",\" close=\")\">",
            "#{id}",
            "</foreach>",
            "</script>"})
    int deleteAccessByIds(@Param("ids") List<Long> ids);

    @Insert("insert into " + Table.DataAccess.TABLE_NAME + "(" + Table.DataAccess.COLUMN_DOMAIN_ID + "," + Table.DataAccess.COLUMN_DATA_ID +
            "," + Table.DataAccess.COLUMN_DATA_ACCESS_ID + "," + Table.DataAccess.COLUMN_DATA_ACCESS +
            ") values (#{domainId} ,#{dataId} ,#{dataAccessId} ,#{dataAccess} )")
    int insertAccess(DataAuthorization dataAuthorization);


    @Select("select " + Table.DataObject.COLUMN_ID + "," + Table.DataObject.COLUMN_DOMAIN_ID + " as domainId," + Table.DataObject.COLUMN_DATA_ID
            + " as dataId," + Table.DataObject.COLUMN_OWNER_ID + " as ownerId from " + Table.DataObject.TABLE_NAME + " where " + Table.DataObject.COLUMN_DOMAIN_ID + "=#{domainId} and " + Table.DataObject.COLUMN_DATA_ID + "=#{dataId} ")
    DataObject selectObjectByDomainIdAndDataId(@Param("domainId") String domainId, @Param("dataId") String dataId);

    @Insert("insert into " + Table.DataObject.TABLE_NAME + "(" + Table.DataObject.COLUMN_DOMAIN_ID + "," + Table.DataObject.COLUMN_DATA_ID + "," + Table.DataObject.COLUMN_OWNER_ID + ")" +
            " values (#{domainId} ,#{dataId} ,#{ownerId} )")
    int insertObject(DataObject dataObject);

    @Update("update " + Table.DataObject.TABLE_NAME + " set " + Table.DataObject.COLUMN_OWNER_ID + "=#{ownerId} where id=#{id}")
    int updateObject(@Param("id") Long id, @Param("ownerId") String ownerId);
}
