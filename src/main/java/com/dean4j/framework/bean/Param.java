package com.dean4j.framework.bean;

import com.dean4j.framework.uitl.CastUtil;
import com.dean4j.framework.uitl.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 封装 请求参数对象
 *
 * @author hunan
 * @since 1.0.0
 */
public class Param {

    private List<FormParam> formParamList;
    private List<FileParam> fileParamsList;

    public Param(List<FormParam> formParamList, List<FileParam> fileParamsList) {
        this.formParamList = formParamList;
        this.fileParamsList = fileParamsList;
    }

    /**
     * 获取请求参数映射
     */
    public Map<String, Object> getFiledMap() {
        Map<String, Object> fieldMap = new HashMap<String, Object>();
        if (formParamList != null && formParamList.size() > 0) {
            for (FormParam formParam : formParamList) {
                String fieldName = formParam.getFieldName();
                Object fieldValue = formParam.getFieldValue();
                if (fieldMap.containsKey(fieldName)) {
                    fieldValue = fieldMap.get(fieldName) + StringUtil.SEPARATOR + fieldValue;
                }
                fieldMap.put(fieldName, fieldValue);
            }
        }
        return fieldMap;
    }

    /**
     * 获取上传文件映射
     */
    public Map<String, List<FileParam>> getFileMap() {
        Map<String, List<FileParam>> fileMap = new HashMap<String, List<FileParam>>();
        if (fileParamsList != null && fileParamsList.size() > 0) {
            for (FileParam fileParam : fileParamsList) {
                String fieldName = fileParam.getFieldName();
                List<FileParam> fileParamList;
                if (fileMap.containsKey(fieldName)) {
                    fileParamList = fileMap.get(fieldName);
                } else {
                    fileParamList = new ArrayList<FileParam>();
                }
                fileParamList.add(fileParam);
                fileMap.put(fieldName, fileParamList);
            }
        }
        return fileMap;
    }

    /**
     * 获取所有的上传文件
     */
    public List<FileParam> getFileList(String fieldName) {
        return getFileMap().get(fieldName);
    }

    /**
     * 获取单个上传文件
     */
    public FileParam getFile(String fieldName) {
        List<FileParam> fileParamList = getFileMap().get(fieldName);
        if (fileParamList != null && fileParamList.size() > 0) {
            return fileParamList.get(0);
        }
        return null;
    }

    /**
     * 判断是否存在参数
     */
    public boolean isEmpty() {
        return (this.formParamList == null || this.formParamList.size() == 0) && (this.fileParamsList == null || this.fileParamsList.size() == 0);
    }

    /**
     * 根据参数名获取String型的参数值
     */
    public String getString(String name) {
        return CastUtil.castString(getFiledMap().get(name));
    }

    /**
     * 根据参数名获取 double 型的参数值
     */
    public double getDouble(String name) {
        return CastUtil.castDouble(getFiledMap().get(name));
    }

    /**
     * 根据参数名获取 long 型的参数值
     */
    public long getLong(String name) {
        return CastUtil.castLong(getFiledMap().get(name));
    }

    /**
     * 根据参数名获取 int 型的参数值
     */
    public int getInt(String name) {
        return CastUtil.castInt(getFiledMap().get(name));
    }

    /**
     * 根据参数名获取 boolean 型的参数值
     */
    public boolean getBoolean(String name) {
        return CastUtil.castBoolean(getFiledMap().get(name));
    }
}
