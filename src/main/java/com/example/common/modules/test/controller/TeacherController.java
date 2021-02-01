package com.example.common.modules.test.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.example.common.common.page.PageData;
import com.example.common.common.utils.Result;
import com.example.common.common.validator.ValidatorUtils;
import com.example.common.common.validator.group.DefaultGroup;
import com.example.common.common.validator.group.UpdateGroup;
import com.example.common.modules.test.dto.TeacherDTO;
import com.example.common.modules.test.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/teacher")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;


    @GetMapping("/page")
    public Result page(@RequestParam Map<String, Object> params) {
        PageData<TeacherDTO> page = teacherService.page(params);
        return new Result().ok(page);
    }

    @GetMapping("{id}")
    public Result get(@PathVariable("id") Long id) {
        TeacherDTO teacherDTO = teacherService.get(id);
        return new Result().ok(teacherDTO);
    }

    @PostMapping
    public Result save(@RequestBody TeacherDTO dto) {
        teacherService.save(dto);
        return new Result().ok(dto);
    }

    @PutMapping
    public Result update(@RequestBody TeacherDTO dto) {
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);
        teacherService.update(dto);
        return new Result();
    }

    @DeleteMapping
    public Result delete(@RequestBody Long[] ids) {
        teacherService.delete(ids);
        return new Result();
    }
}
