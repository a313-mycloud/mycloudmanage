/*
 * Copyright 2014 etao.com All right reserved. This software is the
 * confidential and proprietary information of etao.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with etao.com .
 */
package org.dlut.mycloudmanage.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dlut.mycloudmanage.common.constant.MenuEnum;
import org.dlut.mycloudmanage.common.constant.SessionConstant;
import org.dlut.mycloudmanage.common.constant.UrlConstant;
import org.dlut.mycloudmanage.common.obj.MenuVO;
import org.dlut.mycloudmanage.interceptor.LoginInterceptor;
import org.dlut.mycloudserver.client.common.MyCloudResult;
import org.dlut.mycloudserver.client.common.usermanage.RoleEnum;
import org.dlut.mycloudserver.client.common.usermanage.UserDTO;
import org.dlut.mycloudserver.client.service.usermanage.IUserManageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;

/**
 * 类BaseController.java的实现描述：TODO 类实现描述
 * 
 * @author luojie 2014年9月21日 下午5:30:34
 */
public abstract class BaseController {

    private static Logger      log = LoggerFactory.getLogger(LoginInterceptor.class);

    @Resource
    private IUserManageService userManageService;

    /**
     * 设置默认的一些属性
     * 
     * @param request
     * @param response
     * @param model
     * @return
     */
    protected String setDefaultEnv(HttpServletRequest request, HttpServletResponse response, ModelMap model) {

        // 设置登陆用户的信息
        String account = (String) request.getSession().getAttribute(SessionConstant.USER_ACCOUNT);
        MyCloudResult<UserDTO> userDTOResult = userManageService.getUserByAccount(account);
        if (!userDTOResult.isSuccess() || userDTOResult.getModel() == null) {
            log.error("用户账号" + account + "不存在");
            return "用户账号" + account + "不存在";
        }
        model.put("loginUser", userDTOResult.getModel());

        return null;
    }

    /**
     * 设置需要展示的菜单
     * 
     * @param roleEnum
     * @param currentMenu
     * @param model
     */
    protected void setShowMenuList(RoleEnum roleEnum, MenuEnum currentMenu, ModelMap model) {
        if (roleEnum == null || currentMenu == null || model == null) {
            log.warn("param is null");
            return;
        }
        List<MenuVO> menuList = new ArrayList<MenuVO>();
        for (MenuEnum menuEnum : MenuEnum.values()) {
            if (menuEnum.getRole() == roleEnum) {
                MenuVO menuVO = new MenuVO();
                menuVO.setMenuEnum(menuEnum);
                if (menuEnum == currentMenu) {
                    menuVO.setCurrent(true);
                } else {
                    menuVO.setCurrent(false);
                }
                menuList.add(menuVO);
            }
        }
        model.put("menuList", menuList);
    }

    /**
     * 重定向到登陆页面
     * 
     * @return
     */
    protected String goLoginPage() {
        return "redirect:" + UrlConstant.LOGIN_URL;
    }

    protected String goDefaultPage(RoleEnum role) {
        StringBuilder page = new StringBuilder("redirect:");
        if (role == RoleEnum.ADMIN) {
            page.append(UrlConstant.ADMIN_DEFAULT_URL);
        } else if (role == RoleEnum.STUDENT) {
            page.append(UrlConstant.STUDENT_DEFAULT_URL);
        } else if (role == RoleEnum.TEACHER) {
            page.append(UrlConstant.TEACHER_DEFAULT_URL);
        }
        return page.toString();
    }

    /**
     * 跳转到错误页面
     * 
     * @param errorDesc
     * @return
     */
    protected String goErrorPage(String errorDesc) {
        return "redirect:" + UrlConstant.ERROR_URL + "?errorDesc=" + errorDesc;
    }

    protected void outputResponseJson(HttpServletResponse response, String jsonMap) {
        try {
            response.getWriter().println(jsonMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
