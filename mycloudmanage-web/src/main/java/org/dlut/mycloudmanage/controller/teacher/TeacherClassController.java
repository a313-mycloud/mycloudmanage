/*
 * Copyright 2015 mycloud All right reserved. This software is the
 * confidential and proprietary information of etao.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with etao.com .
 */
package org.dlut.mycloudmanage.controller.teacher;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dlut.mycloudmanage.biz.ClassBiz;
import org.dlut.mycloudmanage.common.constant.MenuEnum;
import org.dlut.mycloudmanage.common.constant.UrlConstant;
import org.dlut.mycloudmanage.common.property.utils.MyPropertiesUtil;
import org.dlut.mycloudmanage.controller.common.BaseController;
import org.dlut.mycloudserver.client.common.Pagination;
import org.dlut.mycloudserver.client.common.classmanage.ClassDTO;
import org.dlut.mycloudserver.client.common.classmanage.QueryClassCondition;
import org.dlut.mycloudserver.client.common.usermanage.RoleEnum;
import org.dlut.mycloudserver.client.common.usermanage.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 类TeacherClassController.java的实现描述：TODO 类实现描述 教师-课程虚拟机-管理
 * 
 * @author xuyizhen 2015年3月7日 上午9:24:51
 */
@Controller
public class TeacherClassController extends BaseController {
    private static Logger log = LoggerFactory.getLogger(TeacherClassController.class);
    @Resource(name = "classBiz")
    private ClassBiz      classBiz;

    /**
     * 教师-课程虚拟机-列表
     * 
     * @param request
     * @param response
     * @param model
     * @param currentPage
     * @return
     */
    @RequestMapping(value = UrlConstant.TEACHER_CLASS_LIST)
    public String classList(HttpServletRequest request, HttpServletResponse response, ModelMap model,
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
        QueryClassCondition queryClassCondition = new QueryClassCondition();//不同的页面对应不同的查询类
        queryClassCondition.setLimit(PAGESIZE);
        queryClassCondition.setOffset((currentPage - 1) * PAGESIZE);
        queryClassCondition.setTeacherAccount(userDTO.getAccount());

        Pagination<ClassDTO> pageClassDTO = this.classBiz.query(queryClassCondition);
        if (pageClassDTO == null)
            return this.goErrorPage("调用 this.classBiz.query()出错");
        if (pageClassDTO.getTotalPage() >= 1 && (currentPage < 1 || currentPage > pageClassDTO.getTotalPage()))
            return this.goErrorPage("该页面不存在");
        List<ClassDTO> classes = pageClassDTO.getList();
        model.put("classes", classes);//不同的查询页面对应不同的查询列表
        model.put("totalPage", pageClassDTO.getTotalPage());//不同的查询页面对应不同的总页数
        model.put("currentPage", currentPage);
        this.setShowMenuList(RoleEnum.TEACHER, MenuEnum.TEACHER_CLASS_LIST, model);
        model.put("screen", "teacher/class_list");
        model.put("js", "teacher/class_list");
        return "default";

    }

    /**
     * 教师-课程-选课学生-列表
     * 
     * @param request
     * @param response
     * @param model
     * @param currentPage
     * @return
     */
    @RequestMapping(value = UrlConstant.TEACHER_CLASS_STUDENT_LIST)
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
            this.goErrorPage("课程ID不能为空");
        if (this.classBiz.getClassById(classId) == null)
            this.goErrorPage("当前账户不存在该课程");
        /* 从student_class表中查询 */
        //        List<UserDTO> students = new ArrayList<UserDTO>();
        //
        //        model.put("students", students);//不同的查询页面对应不同的查询列表
        //        model.put("totalPage", pageClassDTO.getTotalPage());//不同的查询页面对应不同的总页数
        //        model.put("currentPage", currentPage);
        //        this.setShowMenuList(RoleEnum.TEACHER, MenuEnum.TEACHER_CLASS_LIST, model);
        //        model.put("screen", "teacher/class_student_list");
        //        model.put("js", "teacher/class_list");
        return "default";

    }
}
