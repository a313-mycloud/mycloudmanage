/*
 * Copyright 2014 etao.com All right reserved. This software is the
 * confidential and proprietary information of etao.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with etao.com .
 */
package org.dlut.mycloudmanage.controller.admin;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.dlut.mycloudmanage.biz.ClassBiz;
import org.dlut.mycloudmanage.biz.UserBiz;
import org.dlut.mycloudmanage.biz.VmBiz;
import org.dlut.mycloudmanage.common.constant.MenuEnum;
import org.dlut.mycloudmanage.common.constant.UrlConstant;
import org.dlut.mycloudmanage.common.property.utils.MyPropertiesUtil;
import org.dlut.mycloudmanage.common.utils.MyJsonUtils;
import org.dlut.mycloudmanage.controller.common.BaseController;
import org.dlut.mycloudmanage.controller.common.LoginController;
import org.dlut.mycloudserver.client.common.Pagination;
import org.dlut.mycloudserver.client.common.classmanage.ClassDTO;
import org.dlut.mycloudserver.client.common.classmanage.QueryClassCondition;
import org.dlut.mycloudserver.client.common.usermanage.QueryUserCondition;
import org.dlut.mycloudserver.client.common.usermanage.RoleEnum;
import org.dlut.mycloudserver.client.common.usermanage.UserCreateReqDTO;
import org.dlut.mycloudserver.client.common.usermanage.UserDTO;
import org.dlut.mycloudserver.client.common.vmmanage.QueryVmCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

/**
 * 类AccountController.java的实现描述：TODO 类实现描述
 * 
 * @author luojie 2014年10月24日 下午3:36:12
 */
@Controller
public class AccountController extends BaseController {

    private static Logger   log = LoggerFactory.getLogger(LoginController.class);

    @Resource(name = "userBiz")
    private UserBiz         userBiz;

    @Resource(name = "classBiz")
    private ClassBiz        classBiz;

    @Resource(name = "vmBiz")
    private VmBiz           vmBiz;

    @Resource(name = "classController")
    private ClassController classController;

    /**
     * 管理员-账号管理-列表
     * 
     * @param request
     * @param response
     * @param model
     * @param role
     * @param currentPage
     * @return
     */
    @RequestMapping(value = UrlConstant.ADMIN_ACCOUNT_LIST)
    public String accountList(HttpServletRequest request, HttpServletResponse response, ModelMap model, Integer role,
                              Integer currentPage) {
        String errorDesc = this.setDefaultEnv(request, response, model);
        if (errorDesc != null) {
            return this.goErrorPage(errorDesc);
        }
        //获取当前用户帐号
        UserDTO userDTO = (UserDTO) model.get("loginUser");
        //默认显示第一页
        if (currentPage == null)
            currentPage = 1;
        int PAGESIZE = Integer.parseInt(MyPropertiesUtil.getValue("pagesize"));
        if (role == null) {
            role = 1;
        }
        if (!(role == 1 || role == 2 || role == 3)) {
            role = 1;
        }
        QueryUserCondition queryUserCondition = new QueryUserCondition();
        RoleEnum roleEnum = RoleEnum.getRoleByStatus(role);
        queryUserCondition.setRole(roleEnum);
        queryUserCondition.setLimit(PAGESIZE);
        queryUserCondition.setOffset((currentPage - 1) * PAGESIZE);
        Pagination<UserDTO> pagination = this.userBiz.query(queryUserCondition);
        List<UserDTO> userList = null;
        if (pagination == null)
            userList = new ArrayList<UserDTO>();
        else
            userList = pagination.getList();
        model.put("userList", userList);
        model.put("role", roleEnum.getStatus());
        model.put("totalPage", pagination.getTotalPage());//不同的查询页面对应不同的总页数
        model.put("currentPage", currentPage);
        this.setShowMenuList(userDTO.getRole(), MenuEnum.ADMIN_ACCOUNT_LIST, model);
        model.put("screen", "admin/account_list");
        model.put("js", "admin/account_list");
        return "default";
    }

    /**
     * 响应客户端请求，跳转到添加表单
     * 
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = UrlConstant.ADMIN_ACCOUNT_ADD_FORM)
    public String addAccountForm(HttpServletRequest request, HttpServletResponse response, ModelMap model, Integer role) {
        String errorDesc = this.setDefaultEnv(request, response, model);
        if (errorDesc != null) {
            return this.goErrorPage(errorDesc);
        }
        //获取当前用户帐号
        UserDTO userDTO = (UserDTO) model.get("loginUser");

        if (role == null) {
            return this.goErrorPage("您没有选定角色");
        }
        if (!(role == 1 || role == 2 || role == 3)) {
            return this.goErrorPage("您选定的角色不合理");
        }
        model.put("role", role);
        model.put("roleDesc", RoleEnum.getRoleByStatus(role).getDesc());
        model.put("password", MyPropertiesUtil.getValue("initialPassword"));
        this.setShowMenuList(RoleEnum.ADMIN, MenuEnum.ADMIN_ACCOUNT_LIST, model);
        model.put("screen", "admin/account_add_form");
        model.put("js", "admin/account_list");
        return "default";
    }

    /**
     * 响应异步添加请求，返回JSON
     * 
     * @param request
     * @param response
     * @param model
     * @param hostName
     * @param hostIp
     * @return
     */
    @RequestMapping(value = UrlConstant.ADMIN_ACCOUNT_ADD, produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public String addAccount(HttpServletRequest request, HttpServletResponse response, ModelMap model, String account,
                             Integer role, String username) {

        JSONObject json = new JSONObject();
        json.put("isLogin", true);
        json.put("isAuth", true);
        if (role == null)
            return MyJsonUtils.getFailJsonString(json, "角色不能为空");
        if (!(role == 1 || role == 2 || role == 3))
            return MyJsonUtils.getFailJsonString(json, "角色不合法");
        if (account == null)
            return MyJsonUtils.getFailJsonString(json, "登陆账号不能为空");

        QueryUserCondition queryUserCondition = new QueryUserCondition();
        queryUserCondition.setAccount(account);
        if (this.userBiz.countQuery(queryUserCondition) > 0)
            return MyJsonUtils.getFailJsonString(json, "登陆账号已经存在");

        UserCreateReqDTO userCreateReqDTO = new UserCreateReqDTO();
        userCreateReqDTO.setAccount(account);
        userCreateReqDTO.setPassword(MyPropertiesUtil.getValue("initialPassword"));
        userCreateReqDTO.setRole(RoleEnum.getRoleByStatus(role));
        if (StringUtils.isBlank(username))
            userCreateReqDTO.setUserName("");
        else
            userCreateReqDTO.setUserName(username);
        if (!this.userBiz.createUser(userCreateReqDTO))
            return MyJsonUtils.getFailJsonString(json, "创建账户失败");
        return MyJsonUtils.getSuccessJsonString(json, "创建账户成功");
    }

    /**
     * 管理员-账户-删除 处理异步请求，返回JSON
     * 
     * @param hostId
     * @return
     */
    @RequestMapping(value = UrlConstant.ADMIN_ACCOUNT_REMOVE, produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public String removeAccount(String account) {

        JSONObject json = new JSONObject();
        json.put("isLogin", true);
        json.put("isAuth", true);

        if (account == null)
            return MyJsonUtils.getFailJsonString(json, "账号不能为空");
        QueryUserCondition queryUserCondition = new QueryUserCondition();
        queryUserCondition.setAccount(account);
        if (this.userBiz.countQuery(queryUserCondition) <= 0)
            return MyJsonUtils.getFailJsonString(json, "账号不存在");
        //以下应该使用事务

        if (this.userBiz.getUserByAccount(account).getRole().getStatus() == RoleEnum.STUDENT.getStatus()) {
            if (!this.classBiz.deleteStudentAllClass(account)) {
                log.error("从student_class表中删除课程失败");
                return MyJsonUtils.getFailJsonString(json, "账户删除失败");
            }
            //删除学生的虚拟机
        }
        if (this.userBiz.getUserByAccount(account).getRole().getStatus() == RoleEnum.TEACHER.getStatus()) {
            QueryClassCondition queryClassCondition = new QueryClassCondition();
            queryClassCondition.setTeacherAccount(account);
            queryClassCondition.setLimit(1000);
            queryClassCondition.setOffset(0);
            Pagination<ClassDTO> pagination = this.classBiz.query(queryClassCondition);
            if (pagination != null) {
                for (ClassDTO classDTO : pagination.getList()) {
                    String json1 = this.classController.removeClass(classDTO.getClassId());
                    if (!(Boolean) JSONObject.parseObject(json1).get("isSuccess")) {
                        log.error("删除老师" + account + "的课程" + classDTO.getClassName() + "出错");
                    }
                }
            }
        }
        // 如果用户存在虚拟机，删除该用户的虚拟机
        QueryVmCondition queryVmCondition=new QueryVmCondition();
        queryVmCondition.setUserAccount(account);
        if(this.vmBiz.query(queryVmCondition).getList().size()>0){
	        if (!this.vmBiz.deleteVmByUserAccount(account)) {
	            log.error("从vm表中删除账户" + account + "的虚拟机失败");
	            return MyJsonUtils.getFailJsonString(json, "账户删除失败");
	        }
        } 
        //删除该用户的账号
        if (!this.userBiz.deleteUserByAccount(account)) {
            log.error("从user表中删除账户" + account + "失败");
            return MyJsonUtils.getFailJsonString(json, "账户删除失败");
        }
        return MyJsonUtils.getSuccessJsonString(json, "账户删除成功");
        //以上应该使用事务
    }
}
