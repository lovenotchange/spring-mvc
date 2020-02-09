package com.core;

import com.ianno.AppleController;
import com.ianno.AppleRequestMapping;
import com.utils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 自定义 前端控制器
 * Created by cx on 2020/2/8.
 */
public class AppleDispacherServlet extends HttpServlet {

    /**
     * <类名，对象>
     */
    private static Map<String, Object> beanMap;

    /**
     * <url,method>
     */
    private static Map<String, Method> urlMethodMap;

    /**
     * <url,object>
     */
    private static Map<String, Object> urlObjectMap;

    @Override
    public void init() {
        beanMap = BeanUtils.getBeanMap("com.controller", AppleController.class);
        try {
            getUrlMethodMap();
        } catch (Exception e) {
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURI().replace(req.getContextPath(), "");
        Method method = urlMethodMap.get(url);
        Object o = urlObjectMap.get(url);
        try {
          Object result = method.invoke(o);
            req.getRequestDispatcher(result.toString()+".jsp").forward(req,resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 两个方法都会返回 Method 对象的一个数组，区别在于
     * <p>
     * 1、getDeclaredMethods()可以拿到反射类中的公共方法、私有方法、保护方法、默认访问，但不获得继承的方法。
     * <p>
     * 2、getMethods()可以拿到反射类及其父类中的所有公共方法。
     */
    private static void getUrlMethodMap() throws IllegalAccessException, InstantiationException {
        urlMethodMap = new ConcurrentHashMap<>();
        urlObjectMap = new ConcurrentHashMap<>();
        for (Map.Entry<String, Object> entry : beanMap.entrySet()) {
            Class<?> o = entry.getValue().getClass();
            AppleRequestMapping annotation = o.getDeclaredAnnotation(AppleRequestMapping.class);
            String rootPath = getPath(annotation);
            String subPath;
            Method[] method = o.getDeclaredMethods();
            for (Method method1 : method) {
                annotation = method1.getDeclaredAnnotation(AppleRequestMapping.class);
                subPath = getPath(annotation);
                urlMethodMap.put(rootPath + subPath, method1);
                urlObjectMap.put(rootPath + subPath, o.newInstance());
            }
        }
    }

    /**
     * 得到路径
     *
     * @return 返回
     */
    private static String getPath(AppleRequestMapping annotation) {
        String root = "";
        if (annotation != null) {
            if (annotation.value().length > 0) {
                root = annotation.value()[0];
            }
        }
        return root;
    }
}
