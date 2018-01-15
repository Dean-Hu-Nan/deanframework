package com.dean4j.framework.helper;

import com.dean4j.framework.ConfigConstant;
import com.dean4j.framework.uitl.CastUtil;
import com.dean4j.framework.uitl.PropsUtil;

import java.util.Properties;

/**
 * 属性文件助手类
 *
 * @author hunan
 * @since 1.0.0
 */
public final class ConfigHelper {
    private static final Properties CONFIG_PROPS = PropsUtil.loadProps(ConfigConstant.CONFIG_FILE);

    /**
     * 获取JDBC驱动
     * @return
     */
    public static String getJdbcDriver() {
        return CONFIG_PROPS.getProperty(ConfigConstant.JDBC_DRIVER);
    }

    /**
     * 获取JDBC url
     * @return
     */
    public static String getJdbcUrl() {
        return CONFIG_PROPS.getProperty(ConfigConstant.JDBC_URL);
    }

    /**
     * 获取JDBC 用户名
     * @return
     */
    public static String getJdbcUsername() {
        return CONFIG_PROPS.getProperty(ConfigConstant.JDBC_USERNAME);
    }

    /**
     * 获取JDBC 密码
     * @return
     */
    public static String getJdbcPassword() {
        return CONFIG_PROPS.getProperty(ConfigConstant.JDBC_PASSWORD);
    }

    /**
     * 获取应用的基础包名
     * @return
     */
    public static String getAppBasePackage() {
        return CONFIG_PROPS.getProperty(ConfigConstant.APP_BASE_PACKAGE);
    }

    /**
     * 获取应用的 Jsp 路径
     * @return
     */
    public static String getAppJspPath() {
        return CONFIG_PROPS.getProperty(ConfigConstant.APP_JSP_PATH);
    }

    /**
     * 获取应用的 静态资源 路径
     * @return
     */
    public static String getAppAssetPath() {
        return CONFIG_PROPS.getProperty(ConfigConstant.APP_ASSET_PATH);
    }

    /**
     * 获取应用的 上传文件的大小限制
     * @return
     */
    public static int getAppUploadLimit() {
        return CastUtil.castInt(CONFIG_PROPS.getProperty(ConfigConstant.APP_UPLOAD_LIMIT));
    }
}
