package com.dean4j.framework.helper;

import com.dean4j.framework.bean.FileParam;
import com.dean4j.framework.bean.FormParam;
import com.dean4j.framework.bean.Param;
import com.dean4j.framework.uitl.FileUtil;
import com.dean4j.framework.uitl.StreamUtil;
import com.dean4j.framework.uitl.StringUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 文件上传助手类
 *
 * @author hunan
 * @since 1.0.0
 */
public final class UploadHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(UploadHelper.class);

    /**
     * Apache 提供的 Servlet 文件上传对象
     */
    private static ServletFileUpload servletFileUpload;

    /**
     * 初始化 设置临时目录和上传文件大小限制
     */
    public static void init(ServletContext servletContext) {
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        servletFileUpload = new ServletFileUpload(new DiskFileItemFactory(DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD, repository));
        int uploadLimit = ConfigHelper.getAppUploadLimit();
        if (uploadLimit != 0) {
            servletFileUpload.setFileSizeMax(uploadLimit * 1024 * 1024);
        }
    }

    /**
     * 判断请求是否是 multipart 类型
     */
    public static boolean isMultiPart(HttpServletRequest request) {
        return ServletFileUpload.isMultipartContent(request);
    }

    /**
     * 创建请求对象
     */
    public static Param createParam(HttpServletRequest request) {
        List<FormParam> formParamList = new ArrayList<FormParam>();
        List<FileParam> fileParamList = new ArrayList<FileParam>();
        try {
            Map<String, List<FileItem>> fileItemListMap = servletFileUpload.parseParameterMap(request);
            if (fileItemListMap != null && fileItemListMap.size() > 0) {
                for (Map.Entry<String, List<FileItem>> fileItemEntry : fileItemListMap.entrySet()) {
                    String fieldName = fileItemEntry.getKey();
                    List<FileItem> fileItemList = fileItemEntry.getValue();
                    for (FileItem fileItem : fileItemList) {
                        if (fileItem.isFormField()) {
                            String fieldValue = fileItem.getString("UTF-8");
                            formParamList.add(new FormParam(fieldName, fieldValue));
                        } else {
                            String fileName = FileUtil.getRealFileName(new String(fileItem.getName().getBytes(), "UTF-8"));
                            if (StringUtil.isNotEmpty(fileName)) {
                                long fileSize = fileItem.getSize();
                                String contentType = fileItem.getContentType();
                                InputStream inputStream = fileItem.getInputStream();
                                fileParamList.add(new FileParam(fieldName, fileName, fileSize, contentType, inputStream));
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("创建请求对象失败", e);
            throw new RuntimeException(e);
        }
        return new Param(formParamList, fileParamList);
    }

    /**
     * 上传文件
     */
    public static void uploadFile(String basePath, FileParam fileParam) {
        try {
            if (fileParam != null) {
                String filePath = basePath + fileParam.getFileName();
                FileUtil.createFile(filePath);
                InputStream inputStream = new BufferedInputStream(fileParam.getInputStream());
                OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(filePath));
                StreamUtil.copyStream(inputStream, outputStream);
            }
        } catch (Exception e) {
            LOGGER.error("上传文件失败", e);
            throw new RuntimeException(e);
        }
    }


    /**
     * 批量上传文件
     */
    public static void uploadFile(String basePath, List<FileParam> fileParamList) {
        try {
            if (fileParamList != null && fileParamList.size() > 0) {
                for (FileParam fileParam : fileParamList) {
                    uploadFile(basePath, fileParam);
                }
            }

        } catch (Exception e) {
            LOGGER.error("上传文件失败", e);
            throw new RuntimeException(e);
        }
    }
}
