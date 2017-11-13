package com.aiyi.smartframework.helper;

import com.aiyi.smartframework.util.ReflectionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BeanHelper {

    private final static Map<Class<?>, Object> BEAN_MAP = new HashMap<>();


    static {
        Set<Class<?>> allBeanSet = ClassHelper.getAllBeanSet();
        for (Class<?> cls : allBeanSet) {
            Object obj = ReflectionUtil.newInstance(cls);
            BEAN_MAP.put(cls, obj);
        }
    }

    /**
     * bean映射
     */
    public static Map<Class<?>, Object> getBeanMap() {
        return BEAN_MAP;
    }

    /**
     * 获取bean
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> cls) {
        if (!BEAN_MAP.containsKey(cls)) {
            throw new RuntimeException("can not get bean by class" + cls);
        }
        return (T) BEAN_MAP.get(cls);
    }

}
