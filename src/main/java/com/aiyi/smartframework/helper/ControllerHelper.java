package com.aiyi.smartframework.helper;

import com.aiyi.smartframework.annotation.Action;
import com.aiyi.smartframework.bean.Handler;
import com.aiyi.smartframework.bean.Request;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ControllerHelper {

    private static final Map<Request, Handler> ACTION_MAP = new HashMap<>();

    static {
        Set<Class<?>> controllerSet = ClassHelper.getControllerSet();
        for (Class<?> cls : controllerSet) {
            Method[] methods = cls.getDeclaredMethods();
            if (ArrayUtils.isNotEmpty(methods)) {
                for (Method method : methods) {
                    Action action;
                    if ((action = method.getAnnotation(Action.class)) != null) {
                        String requestPath = action.value();
                        String requestMethod = action.method();
                        Request request = new Request(requestMethod, requestPath);
                        Handler handler = new Handler(cls, method);
                        ACTION_MAP.put(request, handler);
                    }
                }
            }
        }

    }

    /**
     * 根据request返回handler
     * @param requestPath
     * @param requestMethod
     * @return handler
     */
    public static Handler getHandler(String requestPath, String requestMethod) {
        return ACTION_MAP.get(new Request(requestMethod, requestPath));
    }

}
