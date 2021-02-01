package com.example.common.modules.test.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.common.service.impl.CrudServiceImpl;
import com.example.common.modules.test.dao.StudentDao;
import com.example.common.modules.test.dto.StudentDTO;
import com.example.common.modules.test.entity.StudentEntity;
import com.example.common.modules.test.service.StudentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;
@Service
public class StudentServiceImpl extends CrudServiceImpl<StudentDao, StudentEntity, StudentDTO> implements StudentService {
    @Override
    public QueryWrapper<StudentEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");
        QueryWrapper<StudentEntity> wrapper = new QueryWrapper<>();

        wrapper.eq(StringUtils.isNotBlank(id), "id", id);

        return wrapper;
    }
}
