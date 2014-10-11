/*
 * Copyright 2014 etao.com All right reserved. This software is the
 * confidential and proprietary information of etao.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with etao.com .
 */
package org.dlut.mycloudmanage.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.dlut.mycloudmanage.common.constant.SessionConstant;
import org.dlut.mycloudmanage.common.constant.UrlConstant;
import org.dlut.mycloudmanage.common.obj.LoginReqDTO;
import org.dlut.mycloudserver.client.common.MyCloudResult;
import org.dlut.mycloudserver.client.common.usermanage.RoleEnum;
import org.dlut.mycloudserver.client.common.usermanage.UserDTO;
import org.dlut.mycloudserver.client.service.usermanage.IUserManageService;
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
@RequestMapping("/login")
@Controller
public class LoginController extends BaseController {

    private static Logger      log = LoggerFactory.getLogger(LoginController.class);

    @Resource(name = "userManageService")
    private IUserManageService userManageService;

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public String login(LoginReqDTO loginReqDTO, String redirect, HttpServletRequest request,
                        HttpServletResponse response, ModelMap model) {
        if (request.getMethod().equals("GET")) {
            return UrlConstant.LOGIN_URL;
        }

        // 验证用户的合法性
        model.addAttribute("loginReqDTO", loginReqDTO);
        String errorDesc = check(loginReqDTO);
        if (!StringUtils.isBlank(errorDesc)) {
            model.addAttribute("errorDesc", errorDesc);
            return UrlConstant.LOGIN_URL;
        }

        MyCloudResult<UserDTO> result = userManageService.verifyAndGetUser(loginReqDTO.getAccount(),
                loginReqDTO.getPassword(), RoleEnum.getRoleByStatus(loginReqDTO.getRole()));
        if (result.isSuccess() && result.getModel() != null) {
            request.getSession(true).setAttribute(SessionConstant.USER_ACCOUNT, result.getModel().getAccount());
            if (StringUtils.isBlank(redirect)) {
                return goDefaultPage(result.getModel().getRole());
            }
            log.info("redirect:" + redirect);
            return "redirect:" + redirect;
        } else {
            model.addAttribute("errorDesc", "用户名或密码错误");
        }

        return UrlConstant.LOGIN_URL;
    }

    /**
     * 验证表单是否合法
     * 
     * @param loginReqDTO
     * @return
     */
    private String check(LoginReqDTO loginReqDTO) {
        String errorDesc = null;
        if (loginReqDTO == null) {
            errorDesc = "表单不能为空";
            return errorDesc;
        }
        if (StringUtils.isBlank(loginReqDTO.getAccount())) {
            errorDesc = "账号不能为空";
            return errorDesc;
        }
        if (StringUtils.isBlank(loginReqDTO.getPassword())) {
            errorDesc = "密码不能为空";
            return errorDesc;
        }
        RoleEnum role = RoleEnum.getRoleByStatus(loginReqDTO.getRole());
        if (role == null) {
            errorDesc = "请选择登陆的角色";
            return errorDesc;
        }
        return errorDesc;
    }
}
