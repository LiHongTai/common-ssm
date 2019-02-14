package com.roger.core.annotation;

import com.roger.core.enumeration.DataSourceType;

import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface TargetDataSource {

    DataSourceType value() default DataSourceType.MASTER;
}
