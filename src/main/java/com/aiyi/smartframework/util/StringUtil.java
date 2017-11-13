package com.aiyi.smartframework.util;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by aiyi on 2017/5/10.
 */
public final class StringUtil {

    public static boolean isEmpty(String str) {
        if (str != null) {
            str = str.trim();
        }
        return StringUtils.isEmpty(str);
    }

    public static boolean isNotEmpty(String str) {
        if (str != null) {
            str = str.trim();
        }
        return StringUtils.isNotEmpty(str);
    }

}
