package com.aiyi.smartframework;

import com.aiyi.smartframework.bean.Data;
import com.aiyi.smartframework.bean.Handler;
import com.aiyi.smartframework.bean.Param;
import com.aiyi.smartframework.bean.View;
import com.aiyi.smartframework.helper.BeanHelper;
import com.aiyi.smartframework.helper.ConfigHelper;
import com.aiyi.smartframework.helper.ControllerHelper;
import com.aiyi.smartframework.helper.HelperLoader;
import com.aiyi.smartframework.util.JsonUtil;
import com.aiyi.smartframework.util.ReflectionUtil;
import com.aiyi.smartframework.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class DispatcherServlet extends HttpServlet {

    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DispatcherServlet.class);

    @Override
    public void init(ServletConfig config) throws ServletException {
        HelperLoader.init();
        LOGGER.info("容器初始化完成");

        ServletContext servletContext = config.getServletContext();

        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");

        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getAppAssetPath() + "*");


    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestMethod = req.getMethod().toLowerCase();
        String requestPath = req.getPathInfo();

        LOGGER.debug("requestPath = {}, requestMethod = {}", requestPath, requestMethod);

        Handler handler = ControllerHelper.getHandler(requestPath, requestMethod);
        if (handler == null) {
            resp.setStatus(404);
            resp.getWriter().print("NOT FOUND");
            resp.getWriter().flush();
            return;
        }
        Class<?> controllerClass = handler.getControllerClass();
        Object controllerBean = BeanHelper.getBean(controllerClass);
        Map<String, Object> map = new HashMap<>();
        Enumeration<String> parameterNames = req.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            String parameter = req.getParameter(parameterName);
            map.put(parameterName, parameter);
        }

        Param param = new Param(map);
        Method actionMethod = handler.getActionMethod();
        Object ret;
        if (actionMethod.getParameterCount() == 0) {
            ret = ReflectionUtil.invokeMethod(controllerBean, actionMethod);
        } else {
            ret = ReflectionUtil.invokeMethod(controllerBean, actionMethod, param);
        }
        if (ret instanceof View) {
            View view = (View) ret;
            String path = view.getPath();
            if (StringUtil.isNotEmpty(path)) {
                if (path.startsWith("/")) {
                    resp.sendRedirect(req.getContextPath() + path);
                } else {
                    Map<String, Object> model = view.getModel();
                    for (Map.Entry<String, Object> entry : map.entrySet()) {
                        req.setAttribute(entry.getKey(), entry.getValue());
                    }
                    req.getRequestDispatcher(ConfigHelper.getAppJspPath() + "/" + path + ".jsp")
                            .forward(req, resp);
                }
            }
        } else if (ret instanceof Data) {
            Data data = (Data) ret;
            Object model = data.getModel();
            if (model != null) {
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                PrintWriter writer = resp.getWriter();
                String json = JsonUtil.toJson(model);
                System.out.println(json);
                writer.write(json);
                writer.flush();
            }
        }


    }
}
