/*
 * Copyright 2014 etao.com All right reserved. This software is the
 * confidential and proprietary information of etao.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with etao.com .
 */
package org.dlut.mycloudmanage.controller;

import javax.annotation.Resource;

import org.dlut.mycloudmanage.service.TestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 类HelloController.java的实现描述：TODO 类实现描述
 * 
 * @author luojie 2014年10月11日 下午11:02:56
 */
@Controller
@RequestMapping("/hello")
public class HelloController {

    @Resource
    private TestService testService;

    @RequestMapping(method = RequestMethod.GET)
    public String sayHello(ModelMap model) {
        model.put("message", testService.getMessage());
        return "hello";
    }
}
