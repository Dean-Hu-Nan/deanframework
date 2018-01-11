package com.dean4j.deandemo.controller;

import com.dean4j.deandemo.model.DataDemo;
import com.dean4j.deandemo.service.FirstDemoService;
import com.dean4j.framework.annotation.Action;
import com.dean4j.framework.annotation.Controller;
import com.dean4j.framework.annotation.Inject;
import com.dean4j.framework.bean.Data;
import com.dean4j.framework.bean.Param;
import com.dean4j.framework.bean.View;

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
     * 返回页面
     */
    @Action("get:/hello")
    public View hello(Param param) {
        String current = firstDemoService.getTime();
        return new View("hello.jsp").addModel("current", current);
    }

    /**
     * 返回Json数据
     */
    @Action("get:/data")
    public Data data(Param param) {
        return new Data(new DataDemo(1, "Dean"));
    }
}
