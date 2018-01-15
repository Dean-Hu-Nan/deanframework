package com.dean4j.framework.uitl;

/**
 * 类型转换操作工具类
 *
 * @author hunan
 * @since 1.0.0
 */
public final class CastUtil {

    /**
     * 转为String型
     */
    public static String castString(Object obj) {
        return CastUtil.castString(obj, "");
    }

    /**
     * 转为String型（提供默认值）
     */
    public static String castString(Object obj, String defaultValue) {
        return obj != null ? String.valueOf(obj) : defaultValue;
    }

    /**
     * 转为double型
     */
    public static double castDouble(Object obj) {
        return CastUtil.castDouble(obj, 0);
    }

    /**
     * 转为double型（提供默认值）
     */
    public static double castDouble(Object obj, double defaultValue) {
        double value = defaultValue;
        if (obj != null) {
            String strValue = castString(obj);
            if (StringUtil.isNotEmpty(strValue)) {
                try {
                    value = Double.parseDouble(strValue);
                } catch (NumberFormatException e) {
                    value = defaultValue;
                }
            }
        }
        return value;
    }

    /**
     * 转为long型
     */
    public static long castLong(Object obj) {
        return CastUtil.castLong(obj, 0);
    }

    /**
     * 转为long型（提供默认值）
     */
    public static long castLong(Object obj, long defaultValue) {
        long Value = defaultValue;
        if (obj != null) {
            String strValue = castString(obj);
            if (StringUtil.isNotEmpty(strValue)) {
                try {
                    Value = Long.parseLong(strValue);
                } catch (NumberFormatException e) {
                    Value = defaultValue;
                }
            }
        }
        return Value;
    }

    /**
     * 转为int型
     */
    public static int castInt(Object obj) {
        return CastUtil.castInt(obj, 0);
    }

    /**
     * 转为int型（提供默认值）
     */
    public static int castInt(Object obj, int defaultValue) {
        int Value = defaultValue;
        if (obj != null) {
            String strValue = castString(obj);
            if (StringUtil.isNotEmpty(strValue)) {
                try {
                    Value = Integer.parseInt(strValue);
                } catch (NumberFormatException e) {
                    Value = defaultValue;
                }
            }
        }
        return Value;
    }

    /**
     * 转为boolean型
     */
    public static boolean castBoolean(Object obj) {
        return CastUtil.castBoolean(obj, false);
    }

    /**
     * 转为boolean型（提供默认值）
     */
    public static boolean castBoolean(Object obj, boolean defaultValue) {
        boolean value = defaultValue;
        if (obj != null) {
            value = Boolean.parseBoolean(castString(obj));
        }
        return value;
    }

}
