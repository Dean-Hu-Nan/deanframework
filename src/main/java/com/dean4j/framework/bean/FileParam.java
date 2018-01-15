package com.dean4j.framework.bean;

import java.io.InputStream;

/**
 * 封装 上传文件参数
 *
 * @author hunan
 * @since 1.0.0
 */
public class FileParam {
    /**
     * 文件表单的字段名
     */
    private String fieldName;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件大小
     */
    private long fileSize;

    /**
     * Content-Type ,文件类型
     */
    private String contentType;

    /**
     * 文件的字节输入流
     */
    private InputStream inputStream;

    public FileParam(String filedName, String fileName, long fileSize, String contentType, InputStream inputStream) {
        this.fieldName = filedName;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.contentType = contentType;
        this.inputStream = inputStream;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getFileName() {
        return fileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public String getContentType() {
        return contentType;
    }

    public InputStream getInputStream() {
        return inputStream;
    }
}
