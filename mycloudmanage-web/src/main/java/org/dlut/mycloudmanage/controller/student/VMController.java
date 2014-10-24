/*
 * Copyright 2014 etao.com All right reserved. This software is the
 * confidential and proprietary information of etao.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with etao.com .
 */
package org.dlut.mycloudmanage.controller.student;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dlut.mycloudmanage.common.constant.MenuEnum;
import org.dlut.mycloudmanage.common.constant.UrlConstant;
import org.dlut.mycloudmanage.controller.BaseController;
import org.dlut.mycloudserver.client.common.usermanage.UserDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 类VMController.java的实现描述：TODO 类实现描述
 * 
 * @author luojie 2014年10月9日 下午11:33:03
 */
@Controller
public class VMController extends BaseController {

    @RequestMapping(value = UrlConstant.STUDENT_VM_LIST)
    public String vmList(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        String errorDesc = setDefaultEnv(request, response, model);
        if (errorDesc != null) {
            return goErrorPage(errorDesc);
        }
        UserDTO userDTO = (UserDTO) model.get("loginUser");

        this.setShowMenuList(userDTO.getRole(), MenuEnum.STUDENT_MENU_VM, model);
        model.put("screen", "student/vm_list");
        return "default";
    }

}
