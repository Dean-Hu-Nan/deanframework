package com.dean4j.framework.helper;

import com.dean4j.framework.bean.FormParam;
import com.dean4j.framework.bean.Param;
import com.dean4j.framework.uitl.ArrayUtil;
import com.dean4j.framework.uitl.CodecUtil;
import com.dean4j.framework.uitl.StreamUtil;
import com.dean4j.framework.uitl.StringUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * 请求助手类
 *
 * @author hunan
 * @since 1.0.0
 */
public final class RequestHelper {

    /**
     * 创建请求对象
     */
    public static Param createParam(HttpServletRequest request) throws IOException {
        List<FormParam> formParamList = new ArrayList<FormParam>();
        formParamList.addAll(parseParameterNames(request));
        formParamList.addAll(parseInputStream(request));
        return new Param(formParamList, null);
    }

    private static List<FormParam> parseParameterNames(HttpServletRequest request) {
        List<FormParam> formParamList = new ArrayList<FormParam>();
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            String[] paramValue = request.getParameterValues(paramName);
            if (ArrayUtil.isNotEmpty(paramValue)) {
                Object fieldValue;
                if (paramValue.length == 1) {
                    fieldValue = paramValue[0];
                } else {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < paramValue.length; i++) {
                        sb.append(paramValue[i]);
                        if (i != paramValue.length - 1) {
                            sb.append(StringUtil.SEPARATOR);
                        }
                    }
                    fieldValue = sb.toString();
                }
                formParamList.add(new FormParam(paramName, fieldValue));
            }
        }
        return formParamList;
    }

    private static List<FormParam> parseInputStream(HttpServletRequest request) throws IOException {
        List<FormParam> formParamList = new ArrayList<FormParam>();
        String body = CodecUtil.decodeURL(StreamUtil.getString(request.getInputStream()));
        if (StringUtil.isNotEmpty(body)) {
            String[] params = body.split("&");
            if (ArrayUtil.isNotEmpty(params)) {
                for (String param : params) {
                    String[] array = param.split("=");
                    if (ArrayUtil.isNotEmpty(array) && array.length == 2) {
                        String paramName = array[0];
                        String paramValue = array[1];
                        formParamList.add(new FormParam(paramName, paramValue));
                    }
                }
            }
        }
        return formParamList;
    }
}
