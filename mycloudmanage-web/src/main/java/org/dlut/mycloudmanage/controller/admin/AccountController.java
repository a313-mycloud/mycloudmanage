/*
 * Copyright 2014 etao.com All right reserved. This software is the
 * confidential and proprietary information of etao.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with etao.com .
 */
package org.dlut.mycloudmanage.controller.admin;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dlut.mycloudmanage.common.constant.MenuEnum;
import org.dlut.mycloudmanage.common.constant.UrlConstant;
import org.dlut.mycloudmanage.controller.common.BaseController;
import org.dlut.mycloudmanage.controller.common.LoginController;
import org.dlut.mycloudserver.client.common.MyCloudResult;
import org.dlut.mycloudserver.client.common.Pagination;
import org.dlut.mycloudserver.client.common.usermanage.QueryUserCondition;
import org.dlut.mycloudserver.client.common.usermanage.RoleEnum;
import org.dlut.mycloudserver.client.common.usermanage.UserDTO;
import org.dlut.mycloudserver.client.service.usermanage.IUserManageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

        QueryUserCondition queryUserCondition = new QueryUserCondition();
        queryUserCondition.setRole(RoleEnum.STUDENT);
        MyCloudResult<Pagination<UserDTO>> result = userManageService.query(queryUserCondition);
        if (!result.isSuccess()) {
            log.warn("查询用户失败，原因：" + result.getMsgInfo());
            return this.goErrorPage("查询用户失败");
        }
        Pagination<UserDTO> paginateion = result.getModel();
        model.put("userList", paginateion.getList());

        this.setShowMenuList(userDTO.getRole(), MenuEnum.ADMIN_ACCOUNT_LIST, model);
        model.put("screen", "admin/account_student_list");
        model.put("js", "admin/account_student_list");
        return "default";
    }

    @RequestMapping(value = UrlConstant.ADMIN_ACCOUNT_TEACHER_LIST)
    public String accountTeacherList(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        String errorDesc = this.setDefaultEnv(request, response, model);
        if (errorDesc != null) {
            log.warn(errorDesc);
            return this.goErrorPage(errorDesc);
        }
        UserDTO userDTO = (UserDTO) model.get("loginUser");

        this.setShowMenuList(userDTO.getRole(), MenuEnum.ADMIN_ACCOUNT_LIST, model);
        model.put("screen", "admin/account_student_list");
        model.put("js", "admin/account_student_list");
        return "default";
    }

    @RequestMapping(value = "/admin/account/list")
    public String accountList(HttpServletRequest request, HttpServletResponse response, ModelMap model, Integer role) {
        String errorDesc = this.setDefaultEnv(request, response, model);
        if (errorDesc != null) {
            log.warn(errorDesc);
            return this.goErrorPage(errorDesc);
        }
        UserDTO userDTO = (UserDTO) model.get("loginUser");

        if (role == null) {
            role = 1;
        }

        QueryUserCondition queryUserCondition = new QueryUserCondition();
        RoleEnum roleEnum = RoleEnum.getRoleByStatus(role);
        queryUserCondition.setRole(roleEnum);
        MyCloudResult<Pagination<UserDTO>> result = userManageService.query(queryUserCondition);
        List<UserDTO> userList = result.getModel().getList();
        model.put("userList", userList);
        model.put("role", roleEnum.getStatus());
        this.setShowMenuList(userDTO.getRole(), MenuEnum.ADMIN_ACCOUNT_LIST, model);
        //        if (roleEnum == RoleEnum.ADMIN) {
        //            model.put("screen", "admin/account_admin_list");
        //        } else if (roleEnum == RoleEnum.STUDENT) {
        //            model.put("screen", "admin/account_student_list");
        //        } else if (roleEnum == RoleEnum.TEACHER) {
        //            model.put("screen", "admin/account_teacher_list");
        //        }
        model.put("screen", "admin/account_list");
        model.put("js", "admin/account_list");
        return "default";
    }

    @RequestMapping(value = "/admin/account/delete.do")
    @ResponseBody
    public String accountDelete(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                                String userName, String password) {
        return "true";
    }

    @RequestMapping(value = UrlConstant.ADMIN_ACCOUNT_ADD_FORM)
    public String addAccountForm(HttpServletRequest request, HttpServletResponse response, ModelMap model, Integer role) {
        String errorDesc = this.setDefaultEnv(request, response, model);
        if (errorDesc != null) {
            return this.goErrorPage(errorDesc);
        }
        this.setShowMenuList(RoleEnum.ADMIN, MenuEnum.ADMIN_ACCOUNT_LIST, model);
        model.put("role", role);
        model.put("screen", "admin/account_add_form");
        model.put("js", "admin/account_add_form");
        return "default";
    }

    //    @RequestMapping(value = UrlConstant.ADMIN_HOST_ADD, produces = { "application/json;charset=UTF-8" })
    //    @ResponseBody
    //    public String addHost(HttpServletRequest request, HttpServletResponse response, ModelMap model, String hostName,
    //                          String hostIp) {
    //
    //        JSONObject json = new JSONObject();
    //        json.put("isLogin", true);
    //        json.put("isAuth", true);
    //        // 检查IP
    //        QueryHostCondition queryHostCondition = new QueryHostCondition();
    //        queryHostCondition.setHostIp(hostIp);
    //
    //        if (this.UserBiz.query(queryHostCondition).getTotalCount() > 0) {
    //            json.put("isSuccess", false);
    //            json.put("message", "要添加的IP已经存在");
    //            return json.toString();
    //        }
    //
    //        // 添加记录
    //        UserCreateReqDTO userDTO = new HostDTO();
    //        hostDTO.setHostIp(hostIp);
    //        hostDTO.setHostName(hostName);
    //
    //        if (this.hostBiz.createHost(hostDTO) > 0) {
    //            json.put("isSuccess", true);
    //            json.put("message", "添加成功");
    //            return json.toString();
    //        }
    //
    //        json.put("isSuccess", false);
    //        json.put("message", "添加失败");
    //        return json.toString();
    //    }
}
