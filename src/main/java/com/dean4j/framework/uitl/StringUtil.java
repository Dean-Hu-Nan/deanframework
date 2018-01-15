package com.dean4j.framework.uitl;

import org.apache.commons.lang3.StringUtils;

/**
 * 字符串工具类
 *
 * @author hunan
 * @since 1.0.0
 */
public final class StringUtil {

    /**
     * 相同参数名时的分隔符 (CheckBox 等控件)
     */
    public static final String SEPARATOR = String.valueOf((char) 29);

    /**
     * 判断字符串是否为空
     */
    public static boolean isEmpty(String str) {
        if (str != null) {
            str = str.trim();
        }
        return StringUtils.isEmpty(str);
    }

    /**
     * 判断字符串是否非空
     */
    public static boolean isNotEmpty(String str)
    {
        return !isEmpty(str);
    }
}
