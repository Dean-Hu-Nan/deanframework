package com.dean4j.deandemo.aspect;

import com.dean4j.framework.annotation.Aspect;
import com.dean4j.framework.annotation.Controller;
import com.dean4j.framework.proxy.AspectProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * 控制器AOP的演示
 * 计算每个方法的执行时间
 *
 * @author hunan
 * @since 1.0.0
 */
@Aspect(Controller.class)
public class ControllerTimeAspect extends AspectProxy {
    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerTimeAspect.class);

    private Long begin;

    @Override
    public void begin() {
        LOGGER.debug("-----------执行方法开始--------------");
    }

    @Override
    public void before(Class<?> cls, Method method, Object[] params) throws Throwable {

        LOGGER.debug("类名：" + cls.getName());
        LOGGER.debug("方法名：" + method.getName());
        begin = System.currentTimeMillis();
    }

    @Override
    public void after(Class<?> cls, Method method, Object[] params, Object result) throws Throwable {
        LOGGER.debug(String.format("执行时间: %dms", System.currentTimeMillis() - begin));
    }

    @Override
    public void end() {
        LOGGER.debug("-----------执行方法结束--------------");
    }

    /**
     * 筛选需要被代理的方法
     * @return true: 被代理  false：跳过代理
     */
    @Override
    public boolean intercept(Class<?> cls, Method method, Object[] params) throws Throwable {
        return super.intercept(cls, method, params);
    }
}
