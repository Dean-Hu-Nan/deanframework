package com.dean4j.framework.proxy;

import com.dean4j.framework.annotation.Transaction;
import com.dean4j.framework.helper.DatabaseHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * 事物代理
 * 当前只代理Service类下的方法
 *
 * @author hunan
 * @since 1.0.0
 */
public class TransacationProxy implements Proxy {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransacationProxy.class);

    /**
     * 保证同一线程下的事物控制逻辑只执行一次
     */
    private static final ThreadLocal<Boolean> FLAG_HOLDER = new ThreadLocal<Boolean>() {
        @Override
        protected Boolean initialValue() {
            return false;
        }
    };

    @Override
    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result;
        boolean flag = FLAG_HOLDER.get();
        Method method = proxyChain.getTargetMethod();
        if (!flag && method.isAnnotationPresent(Transaction.class)) {
            FLAG_HOLDER.set(true);
            try {
                DatabaseHelper.beginTransaction();
                LOGGER.debug("开始事物");
                result = proxyChain.doProxyChain();
                DatabaseHelper.commitTransaction();
                LOGGER.debug("提交事物");
            } catch (Exception e) {
                DatabaseHelper.rollbackTransaction();
                LOGGER.debug("事物回滚");
                throw e;
            } finally {
                FLAG_HOLDER.remove();
            }
        } else {
            result = proxyChain.doProxyChain();
        }
        return result;
    }
}
