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

import org.apache.commons.lang3.StringUtils;
import org.dlut.mycloudmanage.biz.ClassBiz;
import org.dlut.mycloudmanage.biz.VmBiz;
import org.dlut.mycloudmanage.common.constant.MenuEnum;
import org.dlut.mycloudmanage.common.constant.UrlConstant;
import org.dlut.mycloudmanage.common.property.utils.MyPropertiesUtil;
import org.dlut.mycloudmanage.common.utils.MyJsonUtils;
import org.dlut.mycloudmanage.controller.common.BaseController;
import org.dlut.mycloudserver.client.common.Pagination;
import org.dlut.mycloudserver.client.common.classmanage.ClassDTO;
import org.dlut.mycloudserver.client.common.classmanage.QueryClassCondition;
import org.dlut.mycloudserver.client.common.usermanage.RoleEnum;
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
 * 类TeacherClassController.java的实现描述：TODO 类实现描述 教师-课程虚拟机-管理
 * 
 * @author xuyizhen 2015年3月7日 上午9:24:51
 */
@Controller
public class TeacherClassController extends BaseController {
    private static Logger log = LoggerFactory.getLogger(TeacherClassController.class);
    @Resource(name = "classBiz")
    private ClassBiz      classBiz;
    @Resource(name = "vmBiz")
    private VmBiz         vmBiz;

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
            return this.goErrorPage("课程ID不能为空");

        //查看当前课程

        if (this.classBiz.getClassById(classId) == null)
            return this.goErrorPage("当前账户不存在该课程");

        //获得选择一门课程的所有学生
        Pagination<UserDTO> pagination = this.classBiz.getStudentsInOneClass(classId, (currentPage - 1) * PAGESIZE,
                PAGESIZE);
        List<UserDTO> studentsInOneClass = pagination.getList();
        model.put("class", this.classBiz.getClassById(classId));
        model.put("students", studentsInOneClass);//不同的查询页面对应不同的查询列表
        model.put("totalPage", pagination.getTotalPage());//不同的查询页面对应不同的总页数
        model.put("currentPage", currentPage);
        this.setShowMenuList(RoleEnum.TEACHER, MenuEnum.TEACHER_CLASS_LIST, model);
        model.put("screen", "teacher/class_student_list");
        model.put("js", "teacher/class_student_list");
        return "default";

    }

    /**
     * 教师-课程绑定虚拟机-列表
     */
    @RequestMapping(value = UrlConstant.TEACHER_CLASS_VM_LIST)
    public String classVmList(HttpServletRequest request, HttpServletResponse response, ModelMap model,
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

        Pagination<VmDTO> pagination = this.classBiz.getTemplateVmsInOneClass(classId, (currentPage - 1) * PAGESIZE,
                PAGESIZE);
        List<VmDTO> tvms = pagination.getList();
        model.put("class", this.classBiz.getClassById(classId));
        model.put("tvms", tvms);
        model.put("totalPage", pagination.getTotalPage());//不同的查询页面对应不同的总页数
        model.put("currentPage", currentPage);
        this.setShowMenuList(RoleEnum.TEACHER, MenuEnum.TEACHER_CLASS_LIST, model);
        model.put("screen", "teacher/class_vm_list");
        model.put("js", "teacher/class_list");
        return "default";

    }

    /**
     * 教师-课程绑定虚拟机-编辑表单
     * 
     * @param request
     * @param response
     * @param model
     * @param currentPage
     * @return
     */
    @RequestMapping(value = UrlConstant.TEACHER_CLASS_VM_BIND_FORM)
    public String classVmEdit(HttpServletRequest request, HttpServletResponse response, ModelMap model, Integer classId) {
        String errorDesc = this.setDefaultEnv(request, response, model);
        if (errorDesc != null) {
            return this.goErrorPage(errorDesc);
        }
        //获取当前用户帐号
        UserDTO userDTO = (UserDTO) model.get("loginUser");

        if (classId == null)
            return this.goErrorPage("课程ID不能为空");

        //查看当前课程

        if (this.classBiz.getClassById(classId) == null)
            return this.goErrorPage("当前账户不存在该课程");
        QueryVmCondition queryVmCondition = new QueryVmCondition();
//        queryVmCondition.setUserAccount(userDTO.getAccount());
        queryVmCondition.setLimit(1000);
        queryVmCondition.setOffset(0);
        queryVmCondition.setIsTemplateVm(true);
        queryVmCondition.setIsPublicTemplate(true);
        Pagination<VmDTO> pageVmDTO = this.vmBiz.query(queryVmCondition);

        //要绑定虚拟机的课程
        model.put("class", this.classBiz.getClassById(classId));
        //可以使用的模板虚拟机
        model.put("tvms", pageVmDTO.getList());
        this.setShowMenuList(RoleEnum.TEACHER, MenuEnum.TEACHER_CLASS_LIST, model);
        model.put("screen", "teacher/class_vm_form");
        model.put("js", "teacher/class_list");
        return "default";

    }

    /**
     * 教师-课程绑定虚拟机-异步绑定
     * 
     * @param request
     * @param response
     * @param model
     * @param vmUuid
     * @param classId
     * @return
     */
    @RequestMapping(value = UrlConstant.TEACHER_CLASS_VM_BIND, produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public String classVmBind(HttpServletRequest request, HttpServletResponse response, ModelMap model, String vmUuid,
                              Integer classId) {
        System.out.println(vmUuid + "---------");
        JSONObject json = new JSONObject();
        if (classId == null)
            return MyJsonUtils.getFailJsonString(json, "课程不能为空");
        if (this.classBiz.getClassById(classId) == null)
            return MyJsonUtils.getFailJsonString(json, "课程不存在");
        VmDTO srcVmDTO = this.vmBiz.getVmByUuid(vmUuid);
        if (srcVmDTO == null)
            return MyJsonUtils.getFailJsonString(json, "模板虚拟机不存在");
        if (this.classBiz.isBind(classId, vmUuid)) {
            System.out.println("此模板虚拟机已关联到该课程");
            return MyJsonUtils.getFailJsonString(json, "此模板虚拟机已经关联到该课程");
        }
        /***** 以下理论上应该使用事务 ****/
        if (!this.classBiz.addTemplateVmToClass(vmUuid, classId)) {
            return MyJsonUtils.getFailJsonString(json, "课程虚拟机关联失败");
        }
        List<UserDTO> students = this.classBiz.getStudentsInOneClass(classId, 0, 1000).getList();
        for (UserDTO student : students) {
            VmDTO destVmDTO = new VmDTO();
            destVmDTO.setVmName(srcVmDTO.getVmName());
            destVmDTO.setVmVcpu(srcVmDTO.getVmVcpu());
            destVmDTO.setVmMemory(srcVmDTO.getVmMemory());
            destVmDTO.setShowType(srcVmDTO.getShowType());
            destVmDTO.setShowPassword(srcVmDTO.getShowPassword());
            destVmDTO.setClassId(classId);
            destVmDTO.setUserAccount(student.getAccount());
            destVmDTO.setVmNetworkType(srcVmDTO.getVmNetworkType());
            destVmDTO.setIsTemplateVm(false);
            destVmDTO.setIsPublicTemplate(false);
            destVmDTO.setDesc("克隆自" + this.classBiz.getClassById(classId).getClassName());
            if (StringUtils.isBlank(this.vmBiz.cloneVm(destVmDTO, vmUuid))) {
                log.error(student.getAccount() + "关联" + this.classBiz.getClassById(classId).getClassName() + "课程失败");
            }
        }
        /***** 以上理论上应该使用事务 ****/
        return MyJsonUtils.getSuccessJsonString(json, "关联成功");

    }

    /**
     * 教师-课程绑定虚拟机-异步解除绑定
     * 
     * @param request
     * @param response
     * @param model
     * @param vmUuid
     * @param classId
     * @return
     */
    @RequestMapping(value = UrlConstant.TEACHER_CLASS_VM_UNBIND, produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public String classVmUnBind(String vmUuid, Integer classId) {
        System.out.println(vmUuid + "--------------" + classId);
        JSONObject json = new JSONObject();
        if (classId == null)
            return MyJsonUtils.getFailJsonString(json, "课程不能为空");
        if (this.classBiz.getClassById(classId) == null)
            return MyJsonUtils.getFailJsonString(json, "课程不存在");
        VmDTO srcVmDTO = this.vmBiz.getVmByUuid(vmUuid);
        if (srcVmDTO == null)
            return MyJsonUtils.getFailJsonString(json, "模板虚拟机不存在");
        if (!this.classBiz.isBind(classId, vmUuid)) {
            return MyJsonUtils.getFailJsonString(json, "此模板虚拟机未关联到该课程，无须解绑定");
        }
        /***** 以下理论上应该使用事务 ****/
        if (!this.classBiz.deleteOneTemplateVmInOneClass(vmUuid, classId))
            return MyJsonUtils.getFailJsonString(json, "课程虚拟机解绑定失败");
        List<UserDTO> students = this.classBiz.getStudentsInOneClass(classId, 0, 1000).getList();
        if (!students.isEmpty()) {
            for (UserDTO student : students) {
                QueryVmCondition queryVmCondition = new QueryVmCondition();
                queryVmCondition.setClassId(classId);
                queryVmCondition.setParentVmUuid(vmUuid);
                queryVmCondition.setUserAccount(student.getAccount());
                queryVmCondition.setOffset(0);
                queryVmCondition.setLimit(1000);
                List<VmDTO> vms = this.vmBiz.query(queryVmCondition).getList();
                for (VmDTO vm : vms) {
                    if (!this.vmBiz.deleteVm(vm.getVmUuid())) {
                        log.error("课程号" + classId + "下的学生" + student.getAccount() + "解绑定失败");
                    }
                }
            }
        }
        /***** 以上理论上应该使用事务 ****/
        return MyJsonUtils.getSuccessJsonString(json, "解绑成功");

    }
}
