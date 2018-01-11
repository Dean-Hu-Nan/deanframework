package com.dean4j.framework.helper;

import com.dean4j.framework.annotation.Action;
import com.dean4j.framework.bean.Handler;
import com.dean4j.framework.bean.Request;
import com.dean4j.framework.uitl.ArrayUtil;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 控制器帮助类
 *
 * @author hunan
 * @since 1.0.0
 */
public final class ControllerHelper {
    /**
     * 用于存放请求与处理器的映射关系
     */
    private static final Map<Request, Handler> ACTION_MAP = new HashMap<Request, Handler>();

    static {
        /**
         * 获取所有的Controller类
         */
        Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
        if (controllerClassSet != null && controllerClassSet.size() > 0) {
            for (Class<?> controllerClass : controllerClassSet) {
                /**
                 * 获取Controller类中定义的方法
                 */
                Method[] methods = controllerClass.getDeclaredMethods();
                if (ArrayUtil.isNotEmpty(methods)) {
                    /**
                     * 遍历所有的方法
                     */
                    for (Method method : methods) {
                        /**
                         * 判断方法上是否存在Action注解
                         */
                        if (method.isAnnotationPresent(Action.class)) {
                            /**
                             * 从Action注解中获取URL的映射规则
                             */
                            Action action = method.getAnnotation(Action.class);
                            String mapping = action.value();

                            /**
                             * 验证映射规则 "get:/path" "post:/path" ....
                             */
                            if (mapping.matches("\\w+:/\\w*")) {
                                String[] array = mapping.split(":");
                                if (ArrayUtil.isNotEmpty(array) && array.length == 2) {
                                    /**
                                     * 获取请求方法和请求的路径
                                     */
                                    String requestMethod = array[0];
                                    String requestPath = array[1];
                                    Request request = new Request(requestMethod, requestPath);
                                    Handler handler = new Handler(controllerClass, method);

                                    /**
                                     * 初始化映射 Map
                                     */
                                    ACTION_MAP.put(request, handler);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 获取 Handler
     * @param requestMethod 请求方法
     * @param requestPath 请求URI
     * @return
     */
    public static Handler getHandler(String requestMethod, String requestPath) {
        Request request = new Request(requestMethod, requestPath);
        return ACTION_MAP.get(request);
    }

}
