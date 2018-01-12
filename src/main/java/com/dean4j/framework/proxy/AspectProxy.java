package com.dean4j.framework.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * 切面代理模板
 *
 * @author hunan
 * @since 1.0.0
 */
public abstract class AspectProxy implements Proxy {
    private static final Logger LOGGER = LoggerFactory.getLogger(AspectProxy.class);

    @Override
    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result = null;
        Class<?> cls = proxyChain.getTargetClass();
        Method method = proxyChain.getTargetMethod();
        Object[] params = proxyChain.getMethodParams();

        begin();

        try {
            if (intercept(cls, method, params)) {
                before(cls, method, params);
                result = proxyChain.doProxyChain();
                after(cls, method, params, result);
            } else {
                result = proxyChain.doProxyChain();
            }
        } catch (Exception e) {
            LOGGER.error("代理失败", e);
            error(cls, method, params, e);
            throw e;
        } finally {
            end();
        }

        return result;
    }

    /**
     * 初始化
     */
    public void begin() {

    }

    /**
     * 代理类剔除方法，还未实现，现在默认代理所有的方法（思路不清晰）
     */
    public boolean intercept(Class<?> cls, Method method, Object[] params) throws Throwable {
        return true;
    }

    /**
     * 前置增强
     */
    public void before(Class<?> cls, Method method, Object[] params) throws Throwable {

    }

    /**
     * 后置增强
     */
    public void after(Class<?> cls, Method method, Object[] params, Object result) throws Throwable {

    }

    /**
     * 异常增强
     */
    public void error(Class<?> cls, Method method, Object[] params, Throwable e) {

    }

    /**
     * 结束
     */
    public void end() {

    }
}
