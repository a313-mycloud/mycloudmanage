/*
 * Copyright 2014 etao.com All right reserved. This software is the
 * confidential and proprietary information of etao.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with etao.com .
 */
package org.dlut.mycloudmanage.common.utils;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 类UrlUtil.java的实现描述：TODO 类实现描述
 * 
 * @author luojie.lj 2014年9月25日 上午11:15:00
 */
public class UrlUtil {

    private static Logger log = LoggerFactory.getLogger(UrlUtil.class);

    /**
     * 获取请求的站内地址，包括参数
     */
    public static String getCurUrl(HttpServletRequest request) {
        if (request == null) {
            return "";
        }
        if (StringUtils.isBlank(request.getQueryString()))
            return request.getRequestURI();
        else
            return request.getRequestURI() + "?" + request.getQueryString();
    }
}
