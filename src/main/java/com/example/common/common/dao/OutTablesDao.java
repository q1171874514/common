package com.example.common.common.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface OutTablesDao {
    @Select("SELECT ${field} FROM ${tableName} where ${whereContent} LIMIT 1")
    Map getBy(@Param("field") String field, @Param("tableName") String tableName,
              @Param("whereContent") String whereContent);

    @Select("SELECT ${field} FROM ${tableName} where ${whereContent}")
    List<Map> list(@Param("field") String field, @Param("tableName") String tableName,
                   @Param("whereContent") String whereContent);
}
