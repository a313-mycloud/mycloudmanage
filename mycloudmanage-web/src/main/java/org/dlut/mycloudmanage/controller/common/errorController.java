/*
 * Copyright 2014 etao.com All right reserved. This software is the
 * confidential and proprietary information of etao.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with etao.com .
 */
package org.dlut.mycloudmanage.controller.common;

import org.dlut.mycloudmanage.common.constant.UrlConstant;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 类errorController.java的实现描述：TODO 类实现描述
 * 
 * @author luojie 2014年10月10日 下午10:29:46
 */
@RequestMapping(UrlConstant.ERROR_URL)
@Controller
public class errorController extends BaseController {

    @RequestMapping()
    public String error(String errorDesc, ModelMap model) {
        model.put("errorDesc", errorDesc);
        return "common/error";
    }
}
