/*
 * Copyright 2014 etao.com All right reserved. This software is the
 * confidential and proprietary information of etao.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with etao.com .
 */
package org.dlut.mycloudmanage.controller.common;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dlut.mycloudmanage.biz.UserBiz;
import org.dlut.mycloudmanage.common.constant.MenuEnum;
import org.dlut.mycloudmanage.common.constant.SessionConstant;
import org.dlut.mycloudmanage.common.constant.UrlConstant;
import org.dlut.mycloudmanage.common.obj.MenuVO;
import org.dlut.mycloudmanage.interceptor.LoginInterceptor;
import org.dlut.mycloudserver.client.common.usermanage.RoleEnum;
import org.dlut.mycloudserver.client.common.usermanage.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;

/**
 * 类BaseController.java的实现描述：TODO 类实现描述
 * 该类提供了Controller具有的一些基础的功能，是所有Controller类的父类
 * 
 * @author xuyizhen 2014年12月23日 下午11:08:41
 */
public abstract class BaseController {

    private static Logger log = LoggerFactory.getLogger(LoginInterceptor.class);

    @Resource(name = "userBiz")
    private UserBiz       userBiz;

    /**
     * 设置当前页面的上下文
     * 
     * @param request
     * @param response
     * @param model
     * @return
     */
    protected String setDefaultEnv(HttpServletRequest request, HttpServletResponse response, ModelMap model) {

        // 设置登陆用户的信息
        String account = (String) request.getSession().getAttribute(SessionConstant.USER_ACCOUNT);
        UserDTO userDTO = userBiz.getUserByAccount(account);
        if (userDTO == null) {
            log.error("用户账号" + account + "不存在");
            return "用户账号" + account + "不存在";
        }
        model.put("loginUser", userDTO);

        return null;
    }

    /**
     * 设置当前角色要展示的功能菜单
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

    /**
     * 跳转到错误页面
     * 
     * @param errorDesc
     * @return
     */
    protected String goErrorPage(String errorDesc) {
        return "redirect:" + UrlConstant.ERROR_URL + "?errorDesc=" + URLEncoder.encode(errorDesc);
    }

    /**
     * 将String类型的json写入到response流中
     * 
     * @param response
     * @param jsonString
     */
    protected void outputResponseJson(HttpServletResponse response, String jsonString) {
        try {
            response.getWriter().println(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
