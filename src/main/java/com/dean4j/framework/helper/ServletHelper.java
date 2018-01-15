package com.dean4j.framework.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet 帮助类
 *
 * @author hunan
 * @since 1.0.0
 */
public final class ServletHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServletHelper.class);

    /**
     * 每个线程独自拥有一份实例
     */
    private static final ThreadLocal<ServletHelper> SERVLET_HELPER_THREAD_LOCAL = new ThreadLocal<ServletHelper>();

    private HttpServletRequest request;
    private HttpServletResponse response;

    public ServletHelper(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    /**
     * 初始化
     */
    public static void init(HttpServletRequest request, HttpServletResponse response) {
        SERVLET_HELPER_THREAD_LOCAL.set(new ServletHelper(request, response));
    }

    /**
     * 销毁
     */
    public static void destroy() {
        SERVLET_HELPER_THREAD_LOCAL.remove();
    }

    /**
     * 获取request对象
     */
    public static HttpServletRequest getRequest() {
        return SERVLET_HELPER_THREAD_LOCAL.get().request;
    }

    /**
     * 获取response对象
     */
    public static HttpServletResponse getResponse() {
        return SERVLET_HELPER_THREAD_LOCAL.get().response;
    }

    /**
     * 获取Session
     */
    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    /**
     * 获取 ServletContext 对象
     */
    public static ServletContext getServletContext() {
        return getRequest().getServletContext();
    }

    /**
     * 将属性放入 Request 中
     */
    public static void setRequestAttribute(String key, Object value) {
        getRequest().setAttribute(key, value);
    }

    /**
     * 从Request中获取属性
     */
    public static <T> T getRequestAttribute(String key) {
        return (T) getRequest().getAttribute(key);
    }

    /**
     * 从Request中移除属性
     */
    public static void removeRequestAttribute(String key) {
        getRequest().removeAttribute(key);
    }

    /**
     * 发送重定向响应
     */
    public static void sendRedirect(String url) {
        try {
            getResponse().sendRedirect(getRequest().getContextPath() + url);
        } catch (Exception e) {
            LOGGER.error("重定向失败", e);
        }
    }

    /**
     * 设置session属性
     */
    public static void setSession(String key, Object value) {
        getSession().setAttribute(key, value);
    }

    /**
     * 获取session的属性
     */
    public static <T> T getSession(String key) {
        return (T) getSession().getAttribute(key);
    }

    /**
     * 从Session中移除属性
     */
    public static void removeSession(String key) {
        getSession().removeAttribute(key);
    }

    /**
     * 失效Session
     */
    public static void invalidSession()
    {
        getSession().invalidate();
    }

}
