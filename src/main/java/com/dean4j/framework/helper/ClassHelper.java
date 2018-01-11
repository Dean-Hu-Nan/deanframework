package com.dean4j.framework.helper;

import com.dean4j.framework.annotation.Controller;
import com.dean4j.framework.annotation.Service;
import com.dean4j.framework.uitl.ClassUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * 类操作帮助类
 *
 * @author hunan
 * @since 1.0.0
 */
public final class ClassHelper {
    /**
     * 定义类集合 （用于存放所加载的类）
     */
    private static Set<Class<?>> CLASS_SET;

    static{
        String basePackage = ConfigHelper.getAppBasePackage();
        CLASS_SET = ClassUtil.getClassSet(basePackage);
    }

    /**
     * 获取应用包名下的所有的类
     */
    public static Set<Class<?>> getClassSet()
    {
        return CLASS_SET;
    }

    /**
     * 获取应用包名下的所有的 Service 类
     */
    public static Set<Class<?>> getServiceClassSet()
    {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for (Class<?> cls: CLASS_SET) {
            if (cls.isAnnotationPresent(Service.class))
            {
                classSet.add(cls);
            }
        }
        return classSet;
    }

    /**
     * 获取应用包名下的所有的 Controller 类
     */
    public static Set<Class<?>> getControllerClassSet()
    {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for (Class<?> cls: CLASS_SET) {
            if (cls.isAnnotationPresent(Controller.class))
            {
                classSet.add(cls);
            }
        }
        return classSet;
    }

    /**
     * 获取应用包名下的所有的 Bean 类 （包括 ： Service、Controller 等）
     */
    public static Set<Class<?>> getBeanClassSet()
    {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        classSet.addAll(getServiceClassSet());
        classSet.addAll(getControllerClassSet());
        return classSet;
    }
}
