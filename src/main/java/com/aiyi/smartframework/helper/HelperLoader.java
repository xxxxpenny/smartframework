package com.aiyi.smartframework.helper;

import com.aiyi.smartframework.util.ClassUtil;

public class HelperLoader {
    public static void init() {

        Class<?>[] classList = {
                ClassHelper.class,
                BeanHelper.class,
                IocHelper.class,
                ControllerHelper.class
        };

        for (Class<?> cls : classList) {
            ClassUtil.loadClass(cls.getName(), true);
        }
    }
}
