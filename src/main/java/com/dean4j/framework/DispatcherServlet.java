package com.dean4j.framework;

import com.dean4j.framework.bean.Data;
import com.dean4j.framework.bean.Handler;
import com.dean4j.framework.bean.Param;
import com.dean4j.framework.bean.View;
import com.dean4j.framework.helper.*;
import com.dean4j.framework.uitl.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求转发器
 *
 * @author hunan
 * @since 1.0.0
 */
@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        /**
         * 初始化相关Helper类
         */
        HelperLoader.init();
        /**
         * 获取ServletContext 对象 （用于注册Servlet）
         */
        ServletContext servletContext = servletConfig.getServletContext();
        /**
         * 注册jsp的Servlet
         */
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");
        /**
         * 注册处理静态资源的Servlet
         */
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getAppAssetPath() + "*");

        /**
         * 初始化附件上传
         */
        UploadHelper.init(servletContext);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /**
         * 获取请求方法和请求路径
         */
        String requestMethod = req.getMethod().toLowerCase();
        String requestPath = req.getPathInfo();

        if (requestPath.equals("/favicon.ico")) {
            return;
        }

        /**
         * 获取Action处理器
         */
        Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);
        if (handler != null) {
            /**
             * 获取Controller 类和其实例
             */
            Class<?> controllerClass = handler.getControllerClass();
            Object controllerBean = BeanHelper.getBean(controllerClass);

            /**
             * 创建请求参数对象
             */
            Param param;
            if (UploadHelper.isMultiPart(req))
            {
                param = UploadHelper.createParam(req);
            }
            else
            {
                param = RequestHelper.createParam(req);
            }
            /**
             * 调用Action的方法
             */
            Method actionMethod = handler.getActionMethod();
            Object result = null;
            /**
             * 判断是否存在参数
             */
            if (param.isEmpty()) {
                result = ReflectionUtil.invokeMethod(controllerBean, actionMethod);
            } else {
                result = ReflectionUtil.invokeMethod(controllerBean, actionMethod, param);
            }

            /**
             * 处理Action的返回值
             */
            if (result instanceof View) {
                handleViewResult(req, resp, (View) result);
            } else if (result instanceof Data) {
                handlerDataResult(resp, (Data) result);
            }
        }
    }

    private void handlerDataResult(HttpServletResponse resp, Data result) throws IOException {
        //返回Json数据
        Data data = result;
        Object model = data.getModel();
        if (model != null) {
            resp.setContentType("application/json");
            PrintWriter writer = resp.getWriter();
            String json = JsonUtil.toJson(model);
            writer.write(json);
            writer.flush();
            writer.close();
        }
    }

    private void handleViewResult(HttpServletRequest req, HttpServletResponse resp, View result) throws IOException, ServletException {
        //返回jsp页面
        View view = result;
        String path = view.getPath();
        if (StringUtil.isNotEmpty(path)) {
            if (path.startsWith("/")) {
                /**
                 * 跳转到其他的 Action
                 */
                resp.sendRedirect(req.getContextPath() + path);
            } else {
                Map<String, Object> model = view.getModel();
                for (Map.Entry<String, Object> entry : model.entrySet()) {
                    req.setAttribute(entry.getKey(), entry.getValue());
                }
                req.getRequestDispatcher(ConfigHelper.getAppJspPath() + path).forward(req, resp);
            }
        }
    }
}
