package com.dean4j.deandemo.model;

/**
 * 返回Json数据对象 演示
 *
 * @author hunan
 * @since 1.0.0
 */
public class DataDemo {
    private int id;
    private String name;

    public DataDemo(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
