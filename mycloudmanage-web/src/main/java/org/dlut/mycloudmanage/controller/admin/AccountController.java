/*
 * Copyright 2014 etao.com All right reserved. This software is the
 * confidential and proprietary information of etao.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with etao.com .
 */
package org.dlut.mycloudmanage.controller.admin;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dlut.mycloudmanage.common.constant.MenuEnum;
import org.dlut.mycloudmanage.common.constant.UrlConstant;
import org.dlut.mycloudmanage.controller.BaseController;
import org.dlut.mycloudmanage.controller.LoginController;
import org.dlut.mycloudserver.client.common.usermanage.UserDTO;
import org.dlut.mycloudserver.client.service.usermanage.IUserManageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 类AccountController.java的实现描述：TODO 类实现描述
 * 
 * @author luojie 2014年10月24日 下午3:36:12
 */
@Controller
public class AccountController extends BaseController {

    private static Logger      log = LoggerFactory.getLogger(LoginController.class);

    @Resource(name = "userManageService")
    private IUserManageService userManageService;

    @RequestMapping(value = UrlConstant.ADMIN_ACCOUNT_STUDENT_LIST)
    public String accountStudentList(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        String errorDesc = this.setDefaultEnv(request, response, model);
        if (errorDesc != null) {
            log.warn(errorDesc);
            return this.goErrorPage(errorDesc);
        }
        UserDTO userDTO = (UserDTO) model.get("loginUser");

        this.setShowMenuList(userDTO.getRole(), MenuEnum.ADMIN_MENU_ACCOUNT, model);
        model.put("screen", "admin/account_student_list");
        model.put("js", "admin/account_student_list");
        return "default";
    }
}
