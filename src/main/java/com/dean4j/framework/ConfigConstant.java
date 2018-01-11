package com.dean4j.framework;

/**
 * 提供相关配置的常量
 *
 * @author hunan
 * @since 1.0.0
 */
public interface ConfigConstant {
    String CONFIG_FILE = "dean.properties";

    String JDBC_DRIVER = "dean.framework.jdbc.driver";
    String JDBC_URL = "dean.framework.jdbc.url";
    String JDBC_USERNAME = "dean.framework.jdbc.username";
    String JDBC_PASSWORD = "dean.framework.jdbc.password";

    String APP_BASE_PACKAGE = "dean.framework.app.base_package";
    String APP_JSP_PATH = "dean.framework.app.jsp_path";
    String APP_ASSET_PATH = "dean.framework.app.asset_path";
}
