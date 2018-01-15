package com.dean4j.deandemo.controller;

import com.dean4j.deandemo.model.DataDemo;
import com.dean4j.deandemo.service.FirstDemoService;
import com.dean4j.framework.annotation.Action;
import com.dean4j.framework.annotation.Controller;
import com.dean4j.framework.annotation.Inject;
import com.dean4j.framework.bean.Data;
import com.dean4j.framework.bean.Param;
import com.dean4j.framework.bean.View;
import com.dean4j.framework.uitl.CastUtil;

/**
 * 控制器的演示
 *
 * @author hunan
 * @since 1.0.0
 */
@Controller
public class FirstDemoController {

    @Inject
    private FirstDemoService firstDemoService;

    /**
     * 返回页面，不带参数
     */
    @Action("get:/hello")
    public View hello() {
        String current = firstDemoService.getTime();
        return new View("hello.jsp").addModel("current", current);
    }

    /**
     * 返回Json数据，带参数
     */
    @Action("get:/data")
    public Data data(Param param) {
        return new Data(new DataDemo(CastUtil.castInt(param.getFileMap().get("id")), "Dean"));
    }


    /**
     * 演示附件上传
     */
    @Action("post:/upload")
    public View upload(Param param) {
        boolean isSuccess = firstDemoService.uploadFiles(param);
        return new View("hello.jsp").addModel("isSuccess", isSuccess);
    }

}
