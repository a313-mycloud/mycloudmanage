/*
 * Copyright 2014 etao.com All right reserved. This software is the
 * confidential and proprietary information of etao.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with etao.com .
 */
package org.dlut.mycloudmanage.controller.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dlut.mycloudmanage.common.constant.MenuEnum;
import org.dlut.mycloudserver.client.common.usermanage.RoleEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;

/**
 * 类HostController.java的实现描述：TODO 类实现描述
 * 
 * @author xuyizhen Dec 7, 2014 10:19:01 AM
 */

public class BaseDefaultController extends BaseController {

    private static Logger log = LoggerFactory.getLogger(BaseDefaultController.class);

    /**
     * 重定向到对应角色的首页
     * 
     * @param request
     * @param response
     * @param model
     * @return
     */
    public String defaultPage(HttpServletRequest request, HttpServletResponse response, ModelMap model, RoleEnum role) {
        String errorDesc = this.setDefaultEnv(request, response, model);
        if (errorDesc != null) {
            return this.goErrorPage(errorDesc);
        }

        if (role == RoleEnum.ADMIN)
            this.setShowMenuList(RoleEnum.ADMIN, MenuEnum.ADMIN_INDEX, model);
        else if (role == RoleEnum.TEACHER)
            this.setShowMenuList(RoleEnum.TEACHER, MenuEnum.TEACHER_INDEX, model);
        else if (role == RoleEnum.STUDENT)
            this.setShowMenuList(RoleEnum.STUDENT, MenuEnum.STUDENT_INDEX, model);
        model.put("screen", "common/index");
        return "default";
    }

}
