package com.aiyi.smartframework.helper;

import com.aiyi.smartframework.annotation.Controller;
import com.aiyi.smartframework.annotation.Service;
import com.aiyi.smartframework.util.ClassUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * 系统助手类
 */
public class ClassHelper {

    private static final Set<Class<?>> CLASS_SET;

    static {

        String packageName = ConfigHelper.getAppBasePackage();
        CLASS_SET = ClassUtil.getClassSet(packageName);
    }

    /**
     * 获取应用包下的所有类
     *
     * @return set
     */
    public static Set<Class<?>> getClassSet() {
        return CLASS_SET;
    }

    /**
     * 获取应用包下的所有Service类
     *
     * @return set
     */
    public static Set<Class<?>> getServiceClassSet() {
        Set<Class<?>> serviceClassSet = new HashSet<>();
        for (Class<?> cls : CLASS_SET) {
            if (cls.isAnnotationPresent(Service.class))
                serviceClassSet.add(cls);


        }

        return serviceClassSet;
    }

    /**
     * 获取应用包下的所有Controller类
     *
     * @return set
     */
    public static Set<Class<?>> getControllerSet() {
        Set<Class<?>> controllerSet = new HashSet<>();
        for (Class<?> cls : CLASS_SET) {
            if (cls.isAnnotationPresent(Controller.class))
                controllerSet.add(cls);
        }

        return controllerSet;
    }

    /**
     * 获取应用包下的所有Bean类
     *
     * @return set
     */
    public static Set<Class<?>> getAllBeanSet() {
        Set<Class<?>> allBeanSet = new HashSet<>();
        allBeanSet.addAll(getControllerSet());
        allBeanSet.addAll(getServiceClassSet());
        return allBeanSet;
    }


}
