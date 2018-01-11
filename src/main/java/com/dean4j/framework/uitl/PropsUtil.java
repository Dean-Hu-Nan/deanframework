package com.dean4j.framework.uitl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 * 提供加载配置文件公共类
 *
 * @author hunan
 * @since 1.0.0
 */
public final class PropsUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(PropsUtil.class);

    /**
     * 加载属性文件
     * @param fileName
     * @return
     */
    public static Properties loadProps(String fileName) {
        Properties properties = null;
        InputStream is = null;

        try {
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
            if (is == null) {
                throw new FileNotFoundException("配置文件不存在");
            }
            properties = new Properties();
            properties.load(is);

        } catch (Exception e) {
            LOGGER.error("加载文件错误", e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    LOGGER.error("关闭文件流错误", e);
                }
            }
        }
        return properties;
    }
}
