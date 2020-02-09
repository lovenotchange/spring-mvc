package com.utils;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by fox-forever on 2020/2/7.
 * 自定义注解bean
 */
@SuppressWarnings("unused")
public class BeanUtils {

    /**
     * 通过反射，获取指定指定包下的，指定注解的类的对象集合
     *
     * @param packageName 包名
     * @param anno        注解类
     * @return 返回的map集合
     */
    public static Map<String, Object> getBeanMap(String packageName, Class<? extends Annotation> anno) {
        Set<Class<?>> classes = ClassUtil.getClasses(packageName);
        Map<String, Object> map = new ConcurrentHashMap<>();
        classes.stream().forEach(clazz -> {
            try {
                if (Class.forName(clazz.getName()).isAnnotationPresent(anno)) {
                    map.put(getClassName(clazz.getName()), clazz.newInstance());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
        return map;
    }

    private static String getClassName(String name) {
        String[] strings = name.split("\\.");
        return strings[strings.length - 1].toLowerCase();
    }

}