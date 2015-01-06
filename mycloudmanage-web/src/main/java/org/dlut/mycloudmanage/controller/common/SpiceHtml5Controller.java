/*
 * Copyright 2015 etao.com All right reserved. This software is the
 * confidential and proprietary information of etao.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with etao.com .
 */
package org.dlut.mycloudmanage.controller.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.dlut.mycloudmanage.common.constant.UrlConstant;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 类SpiceHtml5Controller.java的实现描述：TODO 类实现描述
 * 
 * @author luojie 2015年1月6日 下午1:54:53
 */
@Controller
public class SpiceHtml5Controller extends BaseController {

    @RequestMapping(value = UrlConstant.SPICE_HTML5_URL, method = { RequestMethod.POST, RequestMethod.GET })
    public String spiceHtml5Client(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                                   String hostIp, Integer port) {
        if (!StringUtils.isBlank(hostIp) && port != null) {
            model.put("hostIp", hostIp);
            model.put("port", port + 60);
        }
        return "common/spice_html5";
    }
}
