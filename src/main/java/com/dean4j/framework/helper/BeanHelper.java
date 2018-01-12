package com.dean4j.framework.helper;

import com.dean4j.framework.uitl.ReflectionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Bean 帮助类
 *
 * @author hunan
 * @since 1.0.0
 */
public final class BeanHelper {
    /**
     * 定义Bean的映射 （用于存放Bean 类与 Bean 实例的映射关系）
     */
    private static final Map<Class<?>, Object> BEAN_MAP = new HashMap<Class<?>, Object>();

    static {
        Set<Class<?>> beanClassSet = ClassHelper.getBeanClassSet();
        for (Class<?> cls : beanClassSet) {
            Object obj = ReflectionUtil.newInstance(cls);
            BEAN_MAP.put(cls, obj);
        }
    }

    /**
     * 获取Bean映射
     */
    public static Map<Class<?>, Object> getBeanMap() {
        return BEAN_MAP;
    }

    /**
     * 设置Bean实例
     */
    public static void setBeanMap(Class<?> cls, Object obj) {
        BEAN_MAP.put(cls, obj);
    }

    /**
     * 获得Bean实例
     */
    public static <T> T getBean(Class<T> cls) {
        if (!BEAN_MAP.containsKey(cls)) {
            throw new RuntimeException("无法通过类型获得实例" + cls);
        }
        return (T) BEAN_MAP.get(cls);
    }
}
