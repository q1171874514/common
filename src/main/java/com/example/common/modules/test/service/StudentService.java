package com.example.common.modules.test.service;

import com.example.common.common.service.CrudService;
import com.example.common.modules.test.dto.StudentDTO;
import com.example.common.modules.test.entity.StudentEntity;

public interface StudentService extends CrudService<StudentEntity, StudentDTO> {
}
