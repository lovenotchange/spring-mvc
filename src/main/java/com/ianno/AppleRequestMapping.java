package com.ianno;

import java.lang.annotation.*;

/**
 * 自定义requestMapping
 * Created by cx on 2020/2/8.
 */
@Target(value = {ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AppleRequestMapping {

    String[] value() default "";
}
