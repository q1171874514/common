/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package com.example.common.common.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.example.common.common.annotation.OutTablesField;
import com.example.common.common.dao.OutTablesDao;
import com.example.common.common.page.PageData;
import com.example.common.common.service.CrudService;
import com.example.common.common.utils.ConvertUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.util.*;

/**
 *  CRUD基础服务类
 *
 * @author Mark sunlightcs@gmail.com
 */
public abstract class CrudServiceImpl<M extends BaseMapper<T>, T, D> extends BaseServiceImpl<M, T> implements CrudService<T, D> {
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
        return (Class<D>)ReflectionKit.getSuperClassGenericType(getClass(), 2);
    }

    /**
     * 获取dto带有OutTablesField注解的字段
     */
    public CrudServiceImpl() {
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
                if(!"null".equals(foreignVal) && !"".equals(foreignVal)) {
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


    /**
     * entity转换dto
     * @param entity
     * @return
     */
    protected D entityToDto(T entity) {
        D dto = ConvertUtils.sourceToTarget(entity, currentDtoClass());
        if(dto != null)
            addOutTablesField(dto, new HashMap<String, Map>());
        return dto;
    }

    /**
     * entityList转换dtoList
     * @param entityList
     * @return
     */
    protected List<D> entityToDto(List<T> entityList) {
        List<D> dtoList = ConvertUtils.sourceToTarget(entityList, currentDtoClass());
        HashMap<String, Map> outTablesEntityMap = new HashMap<>();
        dtoList.stream().filter(dto -> dto != null)
                .forEach(dto -> addOutTablesField(dto, outTablesEntityMap));
        return dtoList;
    }

    protected PageData<D> getPageData(List<T> list, long total, Class<D> target){

        List<D> targetList = entityToDto(list);

        return new PageData<>(targetList, total);
    }

    protected PageData<D> getPageData(IPage page, Class<D> target){
        return getPageData(page.getRecords(), page.getTotal(), target);
    }

    @Override
    public PageData<D> page(Map<String, Object> params) {
        IPage<T> page = baseDao.selectPage(
            getPage(params, null, false),
            getWrapper(params)
        );

        return getPageData(page, currentDtoClass());
    }

    @Override
    public List<D> list(Map<String, Object> params) {
        List<T> entityList;
        if (params == null)
            entityList = baseDao.selectList(null);
        else
            entityList = baseDao.selectList(getWrapper(params));

        return entityToDto(entityList);
    }

    @Override
    public D get(Long id) {
        T entity = baseDao.selectById(id);

        return entityToDto(entity);
    }

    @Override
    public D save(D dto) {
        T entity = ConvertUtils.sourceToTarget(dto, currentModelClass());
        insert(entity);
        //copy主键值到dto
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    @Override
    public void update(D dto) {
        T entity = ConvertUtils.sourceToTarget(dto, currentModelClass());
        updateById(entity);
    }

    @Override
    public void delete(Long[] ids) {
        baseDao.deleteBatchIds(Arrays.asList(ids));
    }
}