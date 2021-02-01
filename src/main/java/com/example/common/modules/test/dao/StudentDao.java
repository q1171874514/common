package com.example.common.modules.test.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.common.modules.test.entity.StudentEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudentDao extends BaseMapper<StudentEntity> {
}
