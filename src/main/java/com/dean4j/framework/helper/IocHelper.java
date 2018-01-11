package com.dean4j.framework.helper;

import com.dean4j.framework.annotation.Inject;
import com.dean4j.framework.uitl.ArrayUtil;
import com.dean4j.framework.uitl.ReflectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 依赖注入 帮助类
 *
 * @author hunan
 * @since 1.0.0
 */
public final class IocHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(IocHelper.class);
    static {
        /**
         * 获取所有的Bean类与Bean实例之间的映射关系 （Service 和 Controller）
         */
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();

        if (beanMap != null && beanMap.size() > 0) {
            for (Map.Entry<Class<?>, Object> beanEntry : beanMap.entrySet()) {
                /**
                 * 从 map 中获取Bean类 和 Bean 的实例
                 */
                Class<?> beanClass = beanEntry.getKey();
                Object beanInstance = beanEntry.getValue();

                /**
                 * 获取Bean类定义的所有的成员变量
                 */
                Field[] beanFields = beanClass.getDeclaredFields();

                if (ArrayUtil.isNotEmpty(beanFields)) {
                    /**
                     * 遍历 Bean Field
                     */
                    for (Field beanField : beanFields) {
                        /**
                         * 判断当前的Field 是否带有 Inject 注解
                         */
                        if (beanField.isAnnotationPresent(Inject.class)) {
                            /**
                             * 获取变量的类型
                             */
                            Class<?> beanFieldClass = beanField.getType();
                            /**
                             * 获取变量的实例
                             */
                            Class<?> beanFieldInstanceClass = beanField.getType();
                            /**
                             * 判断是否是个接口
                             */
                            if (beanFieldClass.isInterface()) {
                                for (Map.Entry<Class<?>, Object> bean : beanMap.entrySet())
                                {
                                    if (beanFieldClass.isAssignableFrom(bean.getKey()))
                                    {
                                        beanFieldInstanceClass = bean.getKey();
                                        break;
                                    }
                                }
                            } else {
                                beanFieldInstanceClass = beanFieldClass;
                            }

                            Object beanFieldInstance = beanMap.get(beanFieldInstanceClass);
                            if (beanFieldInstance != null) {
                                /**
                                 * 通过反射初始化BeanField的值
                                 */
                                ReflectionUtil.setField(beanInstance, beanField, beanFieldInstance);
                                LOGGER.info("IOC注入：" + beanField + " 实例：" + beanFieldInstanceClass);
                            }
                        }
                    }
                }
            }
        }
    }
}
