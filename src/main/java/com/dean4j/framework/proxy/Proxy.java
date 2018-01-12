package com.dean4j.framework.proxy;

/**
 * 代理接口
 *
 * @author hunan
 * @since 1.0.0
 */
public interface Proxy {
    /**
     * 执行链式代理
     */
    Object doProxy(ProxyChain proxyChain) throws Throwable;
}
