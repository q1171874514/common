/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.example.common.common.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础实体类，所有实体都需要继承
 *
 * @author Mark sunlightcs@gmail.com
 */
@Data
public abstract class BaseDto implements Serializable {
    /**
     * id
     */
    private Long id;
    /**
     * 创建者
     */
    private Long  creator;
    /**
     * 创建时间
     */
    private Date createDate;
}