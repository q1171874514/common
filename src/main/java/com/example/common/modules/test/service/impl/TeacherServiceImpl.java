package com.example.common.modules.test.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.common.service.impl.CrudServiceImpl;
import com.example.common.modules.test.dao.TeacherDao;
import com.example.common.modules.test.dto.TeacherDTO;
import com.example.common.modules.test.entity.TeacherEntity;
import com.example.common.modules.test.service.TeacherService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;
@Service
public class TeacherServiceImpl extends CrudServiceImpl<TeacherDao, TeacherEntity, TeacherDTO> implements TeacherService {
    @Override
    public QueryWrapper<TeacherEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");
        QueryWrapper<TeacherEntity> wrapper = new QueryWrapper<>();

        wrapper.eq(StringUtils.isNotBlank(id), "id", id);

        return wrapper;
    }
}
