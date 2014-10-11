/*
 * Copyright 2014 etao.com All right reserved. This software is the
 * confidential and proprietary information of etao.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with etao.com .
 */
package org.dlut.mycloudmanage.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dlut.mycloudmanage.common.constant.SessionConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 类LoginController.java的实现描述：TODO 类实现描述
 * 
 * @author luojie.lj 2014年9月24日 下午7:55:39
 */
@RequestMapping("/logout")
@Controller
public class LogoutController extends BaseController {

    private static Logger log = LoggerFactory.getLogger(LogoutController.class);

    @RequestMapping(method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        request.getSession().removeAttribute(SessionConstant.USER_ACCOUNT);
        return this.goLoginPage();
    }
}
