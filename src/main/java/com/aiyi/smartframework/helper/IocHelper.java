package com.aiyi.smartframework.helper;

import com.aiyi.smartframework.annotation.Inject;
import com.aiyi.smartframework.util.CollectionUtil;
import com.aiyi.smartframework.util.ReflectionUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Map;

public final class IocHelper {

    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(IocHelper.class);

    static {
        LOGGER.info("IocHelper Start");
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();

        if (CollectionUtil.isNotEmpty(beanMap)) {
            for (Map.Entry<Class<?>, Object> entry : beanMap.entrySet()) {
                Class<?> beanClass = entry.getKey();
                Object beanInstance = entry.getValue();
                Field[] fields = beanClass.getDeclaredFields();
                LOGGER.info("start inject {}", beanClass.toString());
                if (ArrayUtils.isNotEmpty(fields)) {
                    for (Field field : fields) {
                        if (field.isAnnotationPresent(Inject.class)) {
                            Class<?> filedType = field.getType();
                            Object beanFiledInstance = beanMap.get(filedType);
                            if (beanFiledInstance != null) {
                                ReflectionUtil.setFiled(beanInstance, field, beanFiledInstance);
                            }
                        }
                    }
                }
                LOGGER.info("end inject {}", beanClass.toString());
            }
        }
        LOGGER.info("IocHelper end");

    }

}
