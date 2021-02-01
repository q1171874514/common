package com.example.common.modules.test.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import com.example.common.common.annotation.OutTablesField;
import lombok.Data;

@Data
public class StudentDTO {
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    private Long id;
    /**
     * 名字
     */
    private String name;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 教师id
     */
    private Long teacherId;
    /**
     * 教师名字
     */
    @OutTablesField(foreignKey = "teacherId", outTableName = "teacher", outField = "name")
    private String teacherName;
}
