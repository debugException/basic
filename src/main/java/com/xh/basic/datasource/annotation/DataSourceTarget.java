package com.xh.basic.datasource.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author szq
 * @Package com.xh.basic
 * @Description: to do ...
 * @date 2018/4/209:36
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({
        ElementType.METHOD
})
public @interface DataSourceTarget {
    String value() default "primary";
}
