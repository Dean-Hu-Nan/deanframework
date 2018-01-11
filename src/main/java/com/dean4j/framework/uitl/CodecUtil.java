package com.dean4j.framework.uitl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * 编码与解码操作工具类
 *
 * @author hunan
 * @since 1.0.0
 */
public final class CodecUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(CodecUtil.class);


    /**
     * 将URL 编码
     */
    public static String encodeURL(String source) {
        try {
            String target = null;
            target = URLEncoder.encode(source, "UTF-8");
            return target;
        } catch (Exception e) {
            LOGGER.error("Url 编码失败", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 将URL 解码
     */
    public static String decodeURL(String source) {
        try {
            String target = null;
            target = URLDecoder.decode(source, "UTF-8");
            return target;
        } catch (Exception e) {
            LOGGER.error("Url 解码失败", e);
            throw new RuntimeException(e);
        }
    }
}
