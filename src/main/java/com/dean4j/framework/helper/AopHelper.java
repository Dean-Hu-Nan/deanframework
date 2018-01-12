package com.dean4j.framework.helper;

import com.dean4j.framework.annotation.Aspect;
import com.dean4j.framework.proxy.AspectProxy;
import com.dean4j.framework.proxy.Proxy;
import com.dean4j.framework.proxy.ProxyManager;
import com.dean4j.framework.uitl.ReflectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * 方法拦截助手类
 *
 * @author hunan
 * @since 1.0.0
 */
public final class AopHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(AopHelper.class);

    static {
        try {
            Map<Class<?>, List<Proxy>> targetMap = createTargetMap(createProxyMap());
            for (Map.Entry<Class<?>, List<Proxy>> targerEntry : targetMap.entrySet()) {
                Class<?> targetClass = targerEntry.getKey();
                List<Proxy> proxyList = targerEntry.getValue();
                Object proxy = ProxyManager.createProxy(targetClass, proxyList);
                /**
                 * 用代理类实例覆盖原来的实例 Map相同键值会直接覆盖
                 */
                BeanHelper.setBeanMap(targetClass, proxy);
            }
        } catch (Exception e) {
            LOGGER.error("aop 初始化失败", e);
        }
    }

    /**
     * 获取代理类和被代理类集合的映射
     */
    private static Map<Class<?>, Set<Class<?>>> createProxyMap() throws Exception {
        Map<Class<?>, Set<Class<?>>> proxyMap = new HashMap<Class<?>, Set<Class<?>>>();
        /**
         * 根据模板类获取继承了代理模板类的所有子类，就是开发中的切面类
         */
        Set<Class<?>> proxyClassSet = ClassHelper.getClassSetBySuper(AspectProxy.class);

        for (Class<?> proxyClass : proxyClassSet) {
            /**
             * 保证子类上必须存在Aspect的注解
             */
            if (proxyClass.isAnnotationPresent(Aspect.class)) {
                Aspect aspect = proxyClass.getAnnotation(Aspect.class);
                Set<Class<?>> targetClassSet = createTargetClassSet(aspect);
                proxyMap.put(proxyClass, targetClassSet);
            }
        }
        return proxyMap;
    }

    /**
     * 获取代理注解value值中的所有类扥集合
     */
    private static Set<Class<?>> createTargetClassSet(Aspect aspect) throws Exception {
        Set<Class<?>> targetClassSet = new HashSet<Class<?>>();
        /**
         * 获取注解的value信息
         */
        Class<? extends Annotation> annotation = aspect.value();
        /**
         * 确保value的正确性
         */
        if (annotation != null && !annotation.equals(Aspect.class)) {
            targetClassSet.addAll(ClassHelper.getClassSetByAnnotion(annotation));
        }
        return targetClassSet;
    }

    /**
     * 获取被代理类和代理类实例集合的映射
     *
     * @param proxyMap
     * @return
     */
    private static Map<Class<?>, List<Proxy>> createTargetMap(Map<Class<?>, Set<Class<?>>> proxyMap) {
        Map<Class<?>, List<Proxy>> targetMap = new HashMap<Class<?>, List<Proxy>>();
        for (Map.Entry<Class<?>, Set<Class<?>>> proxyEntry : proxyMap.entrySet()) {
            Class<?> proxyClass = proxyEntry.getKey();
            for (Class<?> targetClass : proxyEntry.getValue()) {
                /**
                 * 确保每个被代理类都有一个代理类实例
                 */
                Proxy proxy = (Proxy) ReflectionUtil.newInstance(proxyClass);
                if (targetMap.containsKey(targetClass)) {
                    targetMap.get(targetClass).add(proxy);
                } else {
                    List<Proxy> proxyList = new ArrayList<Proxy>();
                    proxyList.add(proxy);
                    targetMap.put(targetClass, proxyList);
                }
            }
        }
        return targetMap;
    }
}
