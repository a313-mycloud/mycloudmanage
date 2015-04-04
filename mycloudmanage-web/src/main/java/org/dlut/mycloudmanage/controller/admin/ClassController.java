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

import org.apache.commons.lang3.StringUtils;
import org.dlut.mycloudmanage.biz.ClassBiz;
import org.dlut.mycloudmanage.biz.UserBiz;
import org.dlut.mycloudmanage.biz.VmBiz;
import org.dlut.mycloudmanage.common.constant.MenuEnum;
import org.dlut.mycloudmanage.common.constant.UrlConstant;
import org.dlut.mycloudmanage.common.property.utils.MyPropertiesUtil;
import org.dlut.mycloudmanage.common.utils.MyJsonUtils;
import org.dlut.mycloudmanage.controller.common.BaseController;
import org.dlut.mycloudserver.client.common.Pagination;
import org.dlut.mycloudserver.client.common.classmanage.ClassDTO;
import org.dlut.mycloudserver.client.common.classmanage.QueryClassCondition;
import org.dlut.mycloudserver.client.common.usermanage.QueryUserCondition;
import org.dlut.mycloudserver.client.common.usermanage.RoleEnum;
import org.dlut.mycloudserver.client.common.usermanage.UserCreateReqDTO;
import org.dlut.mycloudserver.client.common.usermanage.UserDTO;
import org.dlut.mycloudserver.client.common.vmmanage.QueryVmCondition;
import org.dlut.mycloudserver.client.common.vmmanage.VmDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

/**
 * 类ClassController.java的实现描述：TODO 类实现描述
 * 
 * @author xuyizhen Dec 11, 2014 5:12:18 PM
 */
@Controller
public class ClassController extends BaseController {

    private static Logger log = LoggerFactory.getLogger(ClassController.class);

    @Resource(name = "classBiz")
    private ClassBiz      classBiz;

    @Resource(name = "userBiz")
    private UserBiz       userBiz;

    @Resource(name = "vmBiz")
    private VmBiz         vmBiz;

    /**
     * 管理员-课程-显示列表
     * 
     * @param request
     * @param response
     * @param model
     * @param currentPage
     * @param perPage
     * @return
     */
    @RequestMapping(value = UrlConstant.ADMIN_CLASS_LIST)
    public String adminClassList(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                                 Integer currentPage) {

        String errorDesc = this.setDefaultEnv(request, response, model);
        if (errorDesc != null) {
            return this.goErrorPage(errorDesc);
        }
        if (currentPage == null)
            currentPage = 1;
        int PAGESIZE = Integer.parseInt(MyPropertiesUtil.getValue("pagesize"));

        QueryClassCondition queryClassCondition = new QueryClassCondition();
        queryClassCondition.setLimit(PAGESIZE);
        queryClassCondition.setOffset((currentPage - 1) * PAGESIZE);
        Pagination<ClassDTO> pageClassDTO = this.classBiz.query(queryClassCondition);
        if (pageClassDTO.getTotalPage() >= 1 && (currentPage < 1 || currentPage > pageClassDTO.getTotalPage()))
            return this.goErrorPage("该页面不存在");
        List<ClassDTO> classList = pageClassDTO.getList();
        model.put("classList", classList);
        model.put("currentPage", currentPage);
        model.put("totalPage", pageClassDTO.getTotalPage());

        this.setShowMenuList(RoleEnum.ADMIN, MenuEnum.ADMIN_CLASS_LIST, model);
        model.put("screen", "admin/class_list");
        model.put("js", "admin/class_list");
        return "default";
    }

    /**
     * 管理员-课程-删除 处理异步请求，返回JSON
     * 
     * @param classId
     * @return
     */

    @RequestMapping(value = UrlConstant.ADMIN_CLASS_REMOVE, produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public String removeClass(Integer classId) {
        JSONObject json = new JSONObject();

        if (classId == null)
            return MyJsonUtils.getFailJsonString(json, "classId不能为空");
        QueryClassCondition queryClassCondition = new QueryClassCondition();
        queryClassCondition.setClassId(classId);
        if (this.classBiz.countQuery(queryClassCondition) <= 0)
            return MyJsonUtils.getFailJsonString(json, "要删除的课程不存在");
        //以下应该使用事务
        //删除当前课程的所有的学生
        if (!this.classBiz.deleteAllStudentInOneClass(classId))
            return MyJsonUtils.getFailJsonString(json, "删除失败");

        //删除当前课程的所有关联的模板虚拟机
        if (!this.classBiz.deleteAllTemplateVmInOneClass(classId))
            return MyJsonUtils.getFailJsonString(json, "删除失败");
        
        //删除当前课程的所有虚拟机(学生)
        QueryVmCondition queryVmCondition=new QueryVmCondition();
        queryVmCondition.setClassId(classId);
        if(this.vmBiz.countQuery(queryVmCondition)>0)
        {
        	if (!this.vmBiz.deleteVmByClassId(classId))
        		return MyJsonUtils.getFailJsonString(json, "删除失败");
        }
            
        //删除当前课程
        if (!this.classBiz.deleteClass(classId))
            return MyJsonUtils.getFailJsonString(json, "删除失败");
        return MyJsonUtils.getSuccessJsonString(json, "删除成功");
        //以上应该使用事务
    }

    @RequestMapping(value = UrlConstant.ADMIN_CLASS_ADD_FORM)
    public String addClassForm(HttpServletRequest request, HttpServletResponse response, ModelMap model) {

        String errorDesc = this.setDefaultEnv(request, response, model);
        if (errorDesc != null) {
            return this.goErrorPage(errorDesc);
        }
        //获取当前用户帐号
        UserDTO userDTO = (UserDTO) model.get("loginUser");

        QueryUserCondition queryUserCondition = new QueryUserCondition();
        queryUserCondition.setRole(RoleEnum.TEACHER);
        Pagination<UserDTO> pagination = this.userBiz.query(queryUserCondition);
        if (pagination == null) {
            log.warn("查询用户失败，原因：");
            return this.goErrorPage("无法跳转到添加页面");
        }

        model.put("userList", pagination.getList());
        this.setShowMenuList(RoleEnum.ADMIN, MenuEnum.ADMIN_CLASS_LIST, model);
        model.put("screen", "admin/class_add_form");
        model.put("js", "admin/class_list");
        return "default";
    }

    /**
     * 异步添加课程操作
     * 
     * @param request
     * @param response
     * @param model
     * @param className
     * @param teacherAccount
     * @return
     */
    @RequestMapping(value = UrlConstant.ADMIN_CLASS_ADD, produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public String addClass(HttpServletRequest request, HttpServletResponse response, ModelMap model, String className,
                           String teacherAccount) {
        JSONObject json = new JSONObject();
        if (StringUtils.isBlank(className))
            return MyJsonUtils.getFailJsonString(json, "课程名不能为空");
        if (StringUtils.isBlank(teacherAccount))
            return MyJsonUtils.getFailJsonString(json, "教师不能为空");
        // 检查该老师的课程是否已经存在
        QueryClassCondition queryClassCondition = new QueryClassCondition();
        queryClassCondition.setClassName(className);
        queryClassCondition.setTeacherAccount(teacherAccount);
        if (this.classBiz.query(queryClassCondition).getTotalCount() > 0)
            return MyJsonUtils.getFailJsonString(json, "该老师的该课程已经存在");
        ClassDTO classDTO = new ClassDTO();
        classDTO.setClassName(className);
        classDTO.setTeacherAccount(teacherAccount);
        if (this.classBiz.createClass(classDTO) <= 0)
            return MyJsonUtils.getFailJsonString(json, "添加课程失败");
        return MyJsonUtils.getSuccessJsonString(json, "添加成功");
    }

    /**
     * 返回编辑表单
     * 
     * @param request
     * @param response
     * @param model
     * @param classId
     * @return
     */
    @RequestMapping(value = UrlConstant.ADMIN_CLASS_EDIT_FORM)
    public String editForm(HttpServletRequest request, HttpServletResponse response, ModelMap model, Integer classId) {
        String errorDesc = this.setDefaultEnv(request, response, model);
        if (errorDesc != null) {
            return this.goErrorPage(errorDesc);
        }
        //获取当前用户帐号
        UserDTO userDTO = (UserDTO) model.get("loginUser");

        if (classId == null)
            return this.goErrorPage("classId不能为空");
        ClassDTO classDTO = this.classBiz.getClassById(classId);
        if (classDTO == null)
            return this.goErrorPage("要编辑的课程不存在");

        QueryUserCondition queryUserCondition = new QueryUserCondition();
        queryUserCondition.setRole(RoleEnum.TEACHER);
        Pagination<UserDTO> pagination = this.userBiz.query(queryUserCondition);
        if (pagination == null) {
            log.warn("查询用户失败，原因：");
            return this.goErrorPage("无法跳转到编辑页面");
        }

        model.put("userList", pagination.getList());
        model.put("class", classDTO);

        this.setShowMenuList(RoleEnum.ADMIN, MenuEnum.ADMIN_CLASS_LIST, model);
        model.put("screen", "admin/class_edit_form");
        model.put("js", "admin/class_list");
        return "default";
    }

    /**
     * 处理更新异步请求，返回JSON
     * 
     * @param request
     * @param response
     * @param model
     * @param classId
     * @param className
     * @param teacherAccount
     * @return
     */
    @RequestMapping(value = UrlConstant.ADMIN_CLASS_EDIT, produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public String editClass(HttpServletRequest request, HttpServletResponse response, ModelMap model, String className,
                            String teacherAccount, Integer classId) {
        JSONObject json = new JSONObject();
        if (classId == null)
            return MyJsonUtils.getFailJsonString(json, "classId不能为空");
        if (StringUtils.isBlank(className))
            return MyJsonUtils.getFailJsonString(json, "课程名不能为空");
        if (StringUtils.isBlank(teacherAccount))
            return MyJsonUtils.getFailJsonString(json, "教师不能为空");
        QueryUserCondition queryUserCondition = new QueryUserCondition();
        queryUserCondition.setRole(RoleEnum.TEACHER);
        queryUserCondition.setAccount(teacherAccount);
        if (this.userBiz.countQuery(queryUserCondition) <= 0)
            return MyJsonUtils.getFailJsonString(json, "该教师不存在");
        QueryClassCondition queryClassCondition = new QueryClassCondition();
        queryClassCondition.setClassName(className);
        queryClassCondition.setTeacherAccount(teacherAccount);
        if (this.classBiz.countQuery(queryClassCondition) > 0)
            return MyJsonUtils.getFailJsonString(json, "该记录已经存在，不需要重新编辑");

        ClassDTO classDTO = new ClassDTO();
        classDTO.setClassName(className);
        classDTO.setTeacherAccount(teacherAccount);
        classDTO.setClassId(classId);
        if (!this.classBiz.updateClass(classDTO))
            return MyJsonUtils.getFailJsonString(json, "更新失败");
        return MyJsonUtils.getSuccessJsonString(json, "更新成功");
    }

    /**
     * 管理员-课程-选课学生-列表
     * 
     * @param request
     * @param response
     * @param model
     * @param currentPage
     * @return
     */
    @RequestMapping(value = UrlConstant.ADMIN_CLASS_STUDENT_LIST)
    public String classStudentList(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                                   Integer currentPage, Integer classId) {
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

        if (classId == null)
            return this.goErrorPage("课程ID不能为空");

        //查看当前课程

        if (this.classBiz.getClassById(classId) == null)
            return this.goErrorPage("当前账户不存在该课程");

        //获得选择一门课程的所有学生
        Pagination<UserDTO> pagination = this.classBiz.getStudentsInOneClass(classId, (currentPage - 1) * PAGESIZE,
                PAGESIZE);
        List<UserDTO> studentsInOneClass = pagination.getList();
        model.put("class", this.classBiz.getClassById(classId));
        model.put("students", studentsInOneClass);

        model.put("totalPage", pagination.getTotalPage());
        model.put("currentPage", currentPage);
        this.setShowMenuList(RoleEnum.ADMIN, MenuEnum.ADMIN_CLASS_LIST, model);
        model.put("screen", "admin/class_student_list");
        model.put("js", "admin/class_student_list");
        return "default";
    }

    @RequestMapping(value = UrlConstant.ADMIN_CLASS_ADDSTUDENT_FORM)
    public String addStudentForm(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                                 Integer classId) {

        String errorDesc = this.setDefaultEnv(request, response, model);
        if (errorDesc != null) {
            return this.goErrorPage(errorDesc);
        }
        //获取当前用户帐号
        UserDTO userDTO = (UserDTO) model.get("loginUser");

        if (classId == null)
            return this.goErrorPage("classId不能为空");
        ClassDTO classDTO = this.classBiz.getClassById(classId);
        if (classDTO == null)
            return this.goErrorPage("要添加学生的课程不存在");

        model.put("class", classDTO);

        this.setShowMenuList(RoleEnum.ADMIN, MenuEnum.ADMIN_CLASS_LIST, model);
        model.put("screen", "admin/class_addstudent_form");
        model.put("js", "admin/class_list");
        return "default";
    }

    @RequestMapping(value = UrlConstant.ADMIN_CLASS_ADDSTUDENT, produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public String addStudent(HttpServletRequest request, HttpServletResponse response, ModelMap model, Integer classId,
                             String account, String username) {
        String errorDesc = this.setDefaultEnv(request, response, model);
        if (errorDesc != null) {
            return this.goErrorPage(errorDesc);
        }
        //获取当前用户帐号
        UserDTO userDTO = (UserDTO) model.get("loginUser");

        JSONObject json = new JSONObject();
        if (classId == null) {
            log.error("classId不能为空");
            return MyJsonUtils.getFailJsonString(json, "classId不能为空");
        }
        ClassDTO classDTO = this.classBiz.getClassById(classId);
        if (classDTO == null) {
            log.error("要添加学生的课程不存在");
            return MyJsonUtils.getFailJsonString(json, "要添加学生的课程不存在");
        }
        if (StringUtils.isBlank(account)) {
            log.error("学生账户不能为空");
            return MyJsonUtils.getFailJsonString(json, "学生账户不能为空");
        }
        if (StringUtils.isBlank(username))
            username = "";

        /** 以下应该使用事物 ****/
        QueryUserCondition queryUserCondition = new QueryUserCondition();
        queryUserCondition.setAccount(account);

        //如果账号不存在，则创建学生
        String inilPassword = MyPropertiesUtil.getValue("initialPassword");
        UserCreateReqDTO userCreateReqDTO = new UserCreateReqDTO();
        userCreateReqDTO.setAccount(account);
        userCreateReqDTO.setUserName(username);
        userCreateReqDTO.setPassword(inilPassword);
        userCreateReqDTO.setRole(RoleEnum.STUDENT);
        if (this.userBiz.countQuery(queryUserCondition) <= 0) {
            if (!this.userBiz.createUser(userCreateReqDTO)) {
                log.error("创建学生用户" + account + "失败");
                return MyJsonUtils.getFailJsonString(json, "创建学生用户" + account + "失败");
            }
            log.info("创建学生用户" + account + "--" + username + "成功");
        }
        //为该学生添加对应课程的虚拟机
        Pagination<VmDTO> pagination=this.classBiz.getTemplateVmsInOneClass(classId, 0,1000);
        if(pagination!=null){
        	List<VmDTO> vmDTOs=pagination.getList();
        	for(VmDTO srcVmDTO:vmDTOs){
        		VmDTO destVmDTO = new VmDTO();
	            destVmDTO.setVmName(srcVmDTO.getVmName());
	            destVmDTO.setVmVcpu(srcVmDTO.getVmVcpu());
	            destVmDTO.setVmMemory(srcVmDTO.getVmMemory());
	            destVmDTO.setShowType(srcVmDTO.getShowType());
	            destVmDTO.setShowPassword(srcVmDTO.getShowPassword());
	            destVmDTO.setClassId(classId);
	            destVmDTO.setUserAccount(account);
	            destVmDTO.setVmNetworkType(srcVmDTO.getVmNetworkType());
	            destVmDTO.setIsTemplateVm(false);
	            destVmDTO.setIsPublicTemplate(false);
	            destVmDTO.setDesc("克隆自" + this.classBiz.getClassById(classId).getClassName());
	            if (StringUtils.isBlank(this.vmBiz.cloneVm(destVmDTO, srcVmDTO.getVmUuid()))) {
	                log.error( account+ "关联" + srcVmDTO.getVmName()+ "模板虚拟机失败");
	            }
        	}
        }   
       //将学生与课程相互关联
        if (!this.classBiz.addStudentInOneClass(account, classId)) {
            log.error("添加学生--" + account + "--" + username + "--到课程《" + classDTO.getClassName() + "》失败");
            return MyJsonUtils.getFailJsonString(json,
                    "添加学生" + account + "--" + username + "到课程《" + classDTO.getClassName() + "》失败");
        }
        log.info("添加学生" + account + "--" + username + "到课程《" + classDTO.getClassName() + "》成功");
        
        return MyJsonUtils.getSuccessJsonString(json, "添加成功");
        /** 以上应该使用事物 ****/
    }
}
