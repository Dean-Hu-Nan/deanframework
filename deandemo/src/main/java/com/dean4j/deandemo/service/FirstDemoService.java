package com.dean4j.deandemo.service;

import com.dean4j.framework.bean.Param;

/**
 * Service 接口 的演示
 *
 * @author hunan
 * @since 1.0.0
 */
public interface FirstDemoService {
    String getTime();
    Boolean uploadFiles(Param param);
}
