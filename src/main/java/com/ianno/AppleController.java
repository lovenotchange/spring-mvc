package com.ianno;

import java.lang.annotation.*;

/**
 * 自定义controller注解
 * Created by cx on 2020/2/8.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AppleController {

}
