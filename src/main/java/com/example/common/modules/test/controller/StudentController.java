package com.example.common.modules.test.controller;

import com.example.common.common.page.PageData;
import com.example.common.common.utils.Result;
import com.example.common.common.validator.ValidatorUtils;
import com.example.common.common.validator.group.DefaultGroup;
import com.example.common.common.validator.group.UpdateGroup;
import com.example.common.modules.test.dto.StudentDTO;
import com.example.common.modules.test.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping("/page")
    public Result page(@RequestParam Map<String, Object> params) {
        PageData<StudentDTO> page = studentService.page(params);
        return new Result().ok(page);
    }

    @GetMapping("{id}")
    public Result get(@PathVariable("id") Long id) {
        StudentDTO teacherDTO = studentService.get(id);
        return new Result().ok(teacherDTO);
    }

    @PostMapping
    public Result save(@RequestBody StudentDTO dto) {
        studentService.save(dto);
        return new Result().ok(dto);
    }

    @PutMapping
    public Result update(@RequestBody StudentDTO dto) {
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);
        studentService.update(dto);
        return new Result();
    }

    @DeleteMapping
    public Result delete(@RequestBody Long[] ids) {
        studentService.delete(ids);
        return new Result();
    }
}
