package com.dean4j.framework.uitl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Json转换工具类
 *
 * @author hunan
 * @since 1.0.0
 */
public final class JsonUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * 将POJO 转换成json 字符串
     *
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> String toJson(T obj) {
        try {
            String json;
            json = OBJECT_MAPPER.writeValueAsString(obj);
            return json;
        } catch (Exception e) {
            LOGGER.error("转化成Json字符串失败", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 将Json 转换成 POJO
     */
    public static <T> T fromJson(String json, Class<T> type) {
        try {
            T pojo;
            pojo = OBJECT_MAPPER.readValue(json, type);
            return pojo;
        } catch (Exception e) {
            LOGGER.error("Json字符串转化成POJO失败", e);
            throw new RuntimeException(e);
        }
    }
}
