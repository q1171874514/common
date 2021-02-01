package com.example.common.common.service.impl;

import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.example.common.common.annotation.OutTablesField;
import com.example.common.common.dao.OutTablesDao;
import com.example.common.common.service.OutTablesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;

public class OutTablesServiceImpl<D> implements OutTablesService<D> {
    /**
     * 获取dto标有OutTablesField的字段
     */
    protected List<Field> outDtoFields = new ArrayList<>();

    /**
     * 获取对应外键字段信息(key外键名，val外键字段Field)
     */
    protected Map<String, Field> foreignNameToFieldMap = new HashMap<>();

    @Autowired
    private OutTablesDao outTablesDao;

    protected Class<D> currentDtoClass() {
        return (Class<D>) ReflectionKit.getSuperClassGenericType(getClass(), 2);
    }

    /**
     * 获取dto带有OutTablesField注解的字段
     */
    public OutTablesServiceImpl() {
        Arrays.stream(currentDtoClass().getDeclaredFields())
                .filter(field -> field.getAnnotation(OutTablesField.class) != null)
                .forEach(field -> {
                    OutTablesField annotation = field.getAnnotation(OutTablesField.class);
                    if(foreignNameToFieldMap.get(annotation.outKey()) == null) {
                        try {
                            Field foreignField = currentDtoClass().getDeclaredField(annotation.foreignKey());
                            //判断外键是否存在，存在记录
                            if(foreignField != null) {
                                //保存属性
                                outDtoFields.add(field);
                                //保存外键字段
                                foreignNameToFieldMap.put(annotation.foreignKey(),
                                        currentDtoClass().getDeclaredField(annotation.foreignKey()));
                            }
                        } catch (NoSuchFieldException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 增加外表字段
     * @param dto
     * outTablesEntityMap 外表信息（key：外键名+外键值）
     * @return
     */
    @Override
    public D addOutTablesField(D dto, Map<String, Map> outTablesEntityMap) {
        this.outDtoFields.stream().forEach(field -> {
            //获取注解信息
            OutTablesField annotation = field.getAnnotation(OutTablesField.class);
            try {
                //获取字段的外键字段
                Field foreignField = foreignNameToFieldMap.get(annotation.foreignKey());
                foreignField.setAccessible(true);
                String foreignVal = String.valueOf(foreignField.get(dto));
                foreignField.setAccessible(false);
                if(foreignVal != null && !foreignVal.equals("")) {
                    //获取外表信息
                    Map outEntityValue = outTablesEntityMap.get(annotation.foreignKey() + foreignVal);
                    if (outEntityValue == null) {
                        //编写where语句
                        String whereContent = "".equals(annotation.outTableWhere())
                                ? annotation.outKey() + " = " + foreignVal
                                : annotation.outTableWhere();
                        //查询并记录
                        outEntityValue = outTablesDao.getBy("*", annotation.outTableName(), whereContent);
                        outTablesEntityMap.put(annotation.foreignKey() + foreignVal, outEntityValue);
                    }
                    if(outEntityValue != null) {
                        field.setAccessible(true);
                        field.set(dto, outEntityValue.get(annotation.outField()));
                        field.setAccessible(false);
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        return dto;
    }

}
