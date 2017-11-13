package com.aiyi.smartframework.helper;


import com.aiyi.smartframework.ConfigConstant;
import com.aiyi.smartframework.util.PropsUtil;

import java.util.Properties;

/**
 * Created by aiyi on 2017/5/10.
 */
public final class ConfigHelper {

    private final static Properties props = PropsUtil.loadProps(ConfigConstant.CONFIG_FILE);

    public static String getJdbcDriver() {
        return PropsUtil.getString(props, ConfigConstant.JDBC_DRIVER);
    }

    public static String getJdbcUrl() {
        return PropsUtil.getString(props, ConfigConstant.JDBC_URL);
    }

    public static String getJdbcUsername() {
        return PropsUtil.getString(props, ConfigConstant.JDBC_USERNAME);
    }

    public static String getJdbcPassword() {
        return PropsUtil.getString(props, ConfigConstant.JDBC_PASSWORD);
    }

    public static String getAppBasePackage() {
        return PropsUtil.getString(props, ConfigConstant.APP_BASE_PACKAGE);
    }

    public static String getAppJspPath() {
        String appJspPath = PropsUtil.getString(props, ConfigConstant.APP_JSP_PATH, "/WEB-INF/view");
        if (!appJspPath.endsWith("/")) {
            appJspPath = appJspPath + "/";
        }
        return appJspPath;
    }

    public static String getAppAssetPath() {
        String appAssetPath = PropsUtil.getString(props, ConfigConstant.APP_ASSET_PATH, "/asset");
        if (!appAssetPath.endsWith("/")) {
            appAssetPath = appAssetPath + "/";
        }
        return appAssetPath;
    }


}
