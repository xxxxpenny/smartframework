package com.aiyi.smartframework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class ReflectionUtil {

    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ReflectionUtil.class);

    /**
     * 创建实例
     */
    public static Object newInstance(Class<?> cls) {
        Object instance;
        try {
            instance = cls.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            LOGGER.error("new instance fail", e);
            throw new RuntimeException();
        }

        return instance;
    }

    /**
     * 调用方法
     */
    public static Object invokeMethod(Object obj, Method method, Object... args) {
        Object result;
        try {
            method.setAccessible(true);
            result = method.invoke(obj, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            LOGGER.error("invoke method fail", e);
            throw new RuntimeException();
        }

        return result;
    }

    /**
     * 设置值
     */
    public static void setFiled(Object obj, Field field, Object value) {
        try {
            field.setAccessible(true);
            field.set(obj, value);
        } catch (IllegalAccessException e) {
            LOGGER.error("set fail", e);
            throw new RuntimeException();
        }
    }


}
