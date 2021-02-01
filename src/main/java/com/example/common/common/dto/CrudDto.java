package com.example.common.common.dto;

import lombok.Data;

import java.util.Date;

@Data
public abstract class CrudDto extends BaseDto {
    /**
     * 更新者
     */
    private Long updater;
    /**
     * 更新时间
     */
    private Date updateDate;
}
