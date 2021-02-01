package com.example.common.common.annotation;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface OutTablesField {
    /**
     * 外表字段
     * @return
     */
    String outField();

    /**
     * 查询外表对应的建
     * @return
     */
    String outKey() default "id";

    /**
     * 主表的外键
     * @return
     */
    String foreignKey() default "id";

    /**
     * 外表表名
     * @return
     */
    String outTableName();

    /**
     * 查询条件where
     */
    String outTableWhere() default "";

    /**
     * 返回类型，1是查询一个，返回字段值，0是list返回多个，list</>
     * @return
     */
    int type() default 1;

}
