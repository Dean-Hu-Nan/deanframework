package com.dean4j.framework.bean;

import java.util.Map;

/**
 * 封装 请求参数对象
 *
 * @author hunan
 * @since 1.0.0
 */
public class Param {
    private Map<String,Object> paramMap;

    public Param(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    /**
     * 根据参数名获取参数值
     */
    public Object getParamByName(String name)
    {
        return paramMap.get(name);
    }

    /**
     * 获取所有字段信息
     */
    public Map<String, Object> getParamMap() {
        return paramMap;
    }
}
