package com.example.common.modules.test.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.common.modules.test.entity.TeacherEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TeacherDao extends BaseMapper<TeacherEntity> {
}
