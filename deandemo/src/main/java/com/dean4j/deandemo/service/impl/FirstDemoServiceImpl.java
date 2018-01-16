package com.dean4j.deandemo.service.impl;

import com.dean4j.deandemo.service.FirstDemoService;
import com.dean4j.framework.annotation.Service;
import com.dean4j.framework.bean.Param;
import com.dean4j.framework.helper.UploadHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Service 的演示
 *
 * @author hunan
 * @since 1.0.0
 */
@Service
public class FirstDemoServiceImpl implements FirstDemoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FirstDemoServiceImpl.class);

    @Override
    public String getTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String current = dateFormat.format(new Date());
        return current;
    }

    @Override
    public Boolean uploadFiles(Param param) {
        UploadHelper.uploadFile("/tem/upload/", param.getFile("demo_file"));
        return true;
    }
}
