///*
// * Copyright 2014 etao.com All right reserved. This software is the
// * confidential and proprietary information of etao.com ("Confidential
// * Information"). You shall not disclose such Confidential Information and shall
// * use it only in accordance with the terms of the license agreement you entered
// * into with etao.com .
// */
//package org.dlut.mycloudmanage.controller.teacher;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.dlut.mycloudmanage.common.constant.MenuEnum;
//import org.dlut.mycloudmanage.common.constant.UrlConstant;
//import org.dlut.mycloudmanage.controller.common.BaseDiskController;
//import org.dlut.mycloudserver.client.common.usermanage.RoleEnum;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
///**
// * 类TeacherVmController.java的实现描述：TODO 类实现描述
// * 
// * @author xuyizhen 2014年12月24日 下午8:40:36
// */
//@Controller
//public class TeacherDiskController extends BaseDiskController {
//
//    /**
//     * 教师-虚拟硬盘-显示列表
//     * 
//     * @param request
//     * @param response
//     * @param model
//     * @param currentPage
//     * @return
//     */
//    @Override
//    @RequestMapping(value = UrlConstant.TEACHER_DISK_LIST)
//    public String diskList(HttpServletRequest request, HttpServletResponse response, ModelMap model, Integer currentPage) {
//        String result = super.diskList(request, response, model, currentPage);
//        if (!result.equals("default")) {
//            return result;
//        }
//
//        this.setShowMenuList(RoleEnum.TEACHER, MenuEnum.TEACHER_DISK_LIST, model);
//        model.put("screen", "teacher/disk_list");
//        model.put("js", "teacher/disk_list");
//        return "default";
//    }
//
//    /**
//     * 教师-虚拟硬盘-删除 处理异步请求，返回JSON
//     */
//    @Override
//    @RequestMapping(value = UrlConstant.TEACHER_DISK_REMOVE, produces = { "application/json;charset=UTF-8" })
//    @ResponseBody
//    public String removeDisk(String diskUuid) {
//
//        return super.removeDisk(diskUuid);
//    }
//
//    /**
//     * @param request
//     * @param response
//     * @param model
//     * @param diskUuid
//     * @return
//     */
//    @Override
//    @RequestMapping(value = UrlConstant.TEACHER_DISK_ATTACH_FORM)
//    public String attachForm(HttpServletRequest request, HttpServletResponse response, ModelMap model, String diskUuid) {
//        String result = super.attachForm(request, response, model, diskUuid);
//        if (!result.equals("default")) {
//            return result;
//        }
//
//        this.setShowMenuList(RoleEnum.TEACHER, MenuEnum.TEACHER_DISK_LIST, model);
//        model.put("screen", "teacher/disk_attach_form");
//        model.put("js", "teacher/disk_list");
//        return "default";
//
//    }
//
//    /**
//     * @param request
//     * @param response
//     * @param model
//     * @param diskUuid
//     * @param vmUuid
//     * @return
//     */
//    @Override
//    @RequestMapping(value = UrlConstant.TEACHER_DISK_ATTACH, produces = { "application/json;charset=UTF-8" })
//    @ResponseBody
//    public String attach(HttpServletRequest request, HttpServletResponse response, ModelMap model, String diskUuid,
//                         String vmUuid) {
//        return super.attach(request, response, model, diskUuid, vmUuid);
//    }
//
//    @Override
//    @RequestMapping(value = UrlConstant.TEACHER_DISK_UNLOAD, produces = { "application/json;charset=UTF-8" })
//    @ResponseBody
//    public String unload(HttpServletRequest request, HttpServletResponse response, ModelMap model, String diskUuid) {
//
//        return super.unload(request, response, model, diskUuid);
//    }
//
//    @Override
//    @RequestMapping(value = UrlConstant.TEACHER_DISK_ADD_FORM)
//    public String addForm(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
//        String result = super.addForm(request, response, model);
//        if (!result.equals("default")) {
//            return result;
//        }
//
//        this.setShowMenuList(RoleEnum.TEACHER, MenuEnum.TEACHER_DISK_LIST, model);
//        model.put("screen", "teacher/disk_add_form");
//        model.put("js", "teacher/disk_list");
//        return "default";
//
//    }
//
//    @Override
//    @RequestMapping(value = UrlConstant.TEACHER_DISK_ADD, produces = { "application/json;charset=UTF-8" })
//    @ResponseBody
//    public String add(HttpServletRequest request, HttpServletResponse response, ModelMap model, String diskName,
//                      String diskSize, String diskBusType, String dizkDesc) {
//        return super.add(request, response, model, diskName, diskSize, diskBusType, dizkDesc);
//    }
//
//    @Override
//    @RequestMapping(value = UrlConstant.TEACHER_DISK_EDIT_FORM)
//    public String editForm(HttpServletRequest request, HttpServletResponse response, ModelMap model, String diskUuid) {
//        String result = super.editForm(request, response, model, diskUuid);
//        if (!result.equals("default")) {
//            return result;
//        }
//
//        this.setShowMenuList(RoleEnum.TEACHER, MenuEnum.TEACHER_DISK_LIST, model);
//        model.put("screen", "teacher/disk_edit_form");
//        model.put("js", "teacher/disk_list");
//        return "default";
//
//    }
//
//    @Override
//    @RequestMapping(value = UrlConstant.TEACHER_DISK_EDIT, produces = { "application/json;charset=UTF-8" })
//    @ResponseBody
//    public String diskEdit(HttpServletRequest request, HttpServletResponse response, ModelMap model, String diskUuid,
//                           String diskName) {
//        return super.diskEdit(request, response, model, diskUuid, diskName);
//
//    }
//}
