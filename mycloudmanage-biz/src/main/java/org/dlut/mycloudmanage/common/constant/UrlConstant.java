/*
 * Copyright 2014 etao.com All right reserved. This software is the
 * confidential and proprietary information of etao.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with etao.com .
 */
package org.dlut.mycloudmanage.common.constant;

/**
 * 类UrlConstant.java的实现描述：TODO 类实现描述
 *
 * @author luojie.lj 2014年9月25日 上午11:01:57
 */
public class UrlConstant {

    /***** 以下为全局常量 ******/
    /**
     * 登陆页面
     */
    public static final String LOGIN_URL = "/login";

    /**
     * 登出页面
     */
    public static final String LOGOUT_URL = "/logout";

    /**
     * 错误页面
     */
    public static final String ERROR_URL = "/error";
    /**
     * spcie_html5显示页面
     */
    public static final String SPICE_HTML5_URL = "/spicehtml5";
    public static final String DEFAULT_URL = "index";

    /**
     * 物理机--镜像是否删除--查询页面
     */
    public static final String IMAGE_CAN_DELETE = "/common/imagedel.common";
    public static final String META_DATA_GET = "/common/metadataget.common";

    /***** 以上为全局常量 ******/

    /************** 以下为管理员相关 **********************/

    /**
     * 管理员登陆后的默认页
     */
    public static final String ADMIN_DEFAULT_URL = "/admin/index";

    /**
     * 管理员-物理机-操作
     */
    public static final String ADMIN_HOST_LIST = "/admin/host/list";
    public static final String ADMIN_HOST_REMOVE = "/admin/host/remove.do";
    public static final String ADMIN_HOST_REMOVEALL = "/admin/host/removeAll.do";
    public static final String ADMIN_HOST_ADD_FORM = "/admin/host/add/form";
    public static final String ADMIN_HOST_ADD = "/admin/host/add.do";
    public static final String ADMIN_HOST_EDIT_FORM = "/admin/host/edit/form";
    public static final String ADMIN_HOST_EDIT = "/admin/host/edit.do";
    /**
     * 管理员-账户管理-操作
     */
    public static final String ADMIN_ACCOUNT_LIST = "/admin/account/list";
    public static final String ADMIN_ACCOUNT_ADD_FORM = "/admin/account/add/form";
    public static final String ADMIN_ACCOUNT_ADD = "/admin/account/add.do";
    public static final String ADMIN_ACCOUNT_REMOVE = "/admin/account/remove.do";

    /**
     * 管理员-课程-操作
     */
    public static final String ADMIN_CLASS_LIST = "/admin/class/list";
    public static final String ADMIN_CLASS_REMOVE = "/admin/class/remove.do";
    public static final String ADMIN_CLASS_REMOVEALL = "/admin/class/removeAll.do";
    public static final String ADMIN_CLASS_ADD_FORM = "/admin/class/add/form";
    public static final String ADMIN_CLASS_ADD = "/admin/class/add.do";
    public static final String ADMIN_CLASS_EDIT_FORM = "/admin/class/edit/form";
    public static final String ADMIN_CLASS_EDIT = "/admin/class/edit.do";
    public static final String ADMIN_CLASS_STUDENT_LIST = "/admin/class/student/list";
    public static final String ADMIN_CLASS_ADDSTUDENT_FORM = "/admin/class/addstudent/form";
    public static final String ADMIN_CLASS_ADDSTUDENT = "/admin/class/addstudent.do";

    /**
     * 管理员-镜像-操作
     */
    public static final String ADMIN_IMAGE_LIST = "/admin/image/list";
    public static final String ADMIN_IMAGE_REMOVE = "/admin/image/remove.do";
    public static final String ADMIN_IMAGE_ADD_FORM = "/admin/image/add/form";
    public static final String ADMIN_IMAGE_ADD = "/admin/image/add.do";

    /**
     * 管理员-虚拟机-操作
     */
    public static final String ADMIN_VM_LIST = "/admin/vm/list";
    public static final String ADMIN_VM_EDIT_FORM = "/admin/vm/edit/form";
    public static final String ADMIN_VM_EDIT = "/admin/vm/edit.do";
    public static final String ADMIN_VM_START = "/admin/vm/start.do";
    public static final String ADMIN_VM_SHUTDOWN = "/admin/vm/shutdown.do";
    public static final String ADMIN_VM_ADD_FORM = "/admin/vm/add/form";
    public static final String ADMIN_VM_ADD = "/admin/vm/add.do";
    public static final String ADMIN_VM_CONVERT = "/admin/vm/convert.do";
    public static final String ADMIN_VM_REMOVE = "/admin/vm/remove.do";

    /**
     * 管理员-虚拟硬盘-操作
     */
    public static final String ADMIN_DISK_LIST = "/admin/disk/list";
    public static final String ADMIN_DISK_REMOVE = "/admin/disk/remove.do";
    public static final String ADMIN_DISK_ATTACH_FORM = "/admin/disk/attach/form";
    public static final String ADMIN_DISK_ATTACH = "/admin/disk/attach.do";
    public static final String ADMIN_DISK_UNLOAD = "/admin/disk/unload.do";
    public static final String ADMIN_DISK_ADD_FORM = "/admin/disk/add/form";
    public static final String ADMIN_DISK_ADD = "/admin/disk/add";
    public static final String ADMIN_DISK_EDIT_FORM = "/admin/disk/edit/form";
    public static final String ADMIN_DISK_EDIT = "/admin/disk/edit.do";

    //    /**
    //     * 管理员-性能监控-操作
    //     */
    //    public static final String ADMIN_MONITOR_LIST          = "/admin/monitor/list";
    //    public static final String ADMIN_MONITOR_GETLIST       = "/admin/monitor/getlist.do";
    //    public static final String ADMIN_MONITOR_ADD_FORM      = "/admin/monitor/add/form";
    //    public static final String ADMIN_MONITOR_ADD           = "/admin/monitor/add.do";
    //    public static final String ADMIN_MONITOR_REMOVE        = "/admin/monitor/remove.do";

    /************** 以上为管理员相关 **********************/

    /************** 以下为学生相关 **********************/
    /**
     * 学生角色登陆后的默认页
     */
    public static final String STUDENT_DEFAULT_URL = "/student/index";
    /**
     * 学生-私有虚拟机-操作
     */
    public static final String STUDENT_VM_LIST = "/student/vm/list";
    public static final String STUDENT_VM_EDIT_FORM = "/student/vm/edit/form";
    public static final String STUDENT_VM_EDIT = "/student/vm/edit.do";
    public static final String STUDENT_VM_START = "/student/vm/start.do";
    public static final String STUDENT_VM_SHUTDOWN = "/student/vm/shutdown.do";
    public static final String STUDENT_VM_REINSTALL = "/student/vm/reinstall.do";
    /**
     * 学生-虚拟硬盘-操作
     */
    public static final String STUDENT_DISK_LIST = "/student/disk/list";
    public static final String STUDENT_DISK_REMOVE = "/student/disk/remove.do";
    public static final String STUDENT_DISK_ATTACH_FORM = "/student/disk/attach/form";
    public static final String STUDENT_DISK_ATTACH = "/student/disk/attach.do";
    public static final String STUDENT_DISK_UNLOAD = "/student/disk/unload.do";
    public static final String STUDENT_DISK_ADD_FORM = "/student/disk/add/form";
    public static final String STUDENT_DISK_ADD = "/student/disk/add";
    public static final String STUDENT_DISK_EDIT_FORM = "/student/disk/edit/form";
    public static final String STUDENT_DISK_EDIT = "/student/disk/edit.do";

    /************** 以上为学生相关 **********************/

    /************** 以下为教师相关 **********************/
    /**
     * 教师角色登陆后的默认页
     */
    public static final String TEACHER_DEFAULT_URL = "/teacher/index";
    /**
     * 教师-私有虚拟机-操作
     */
    public static final String TEACHER_VM_LIST = "/teacher/vm/list";
    public static final String TEACHER_VM_EDIT_FORM = "/teacher/vm/edit/form";
    public static final String TEACHER_VM_EDIT = "/teacher/vm/edit.do";
    public static final String TEACHER_VM_START = "/teacher/vm/start.do";
    public static final String TEACHER_VM_SHUTDOWN = "/teacher/vm/shutdown.do";
    public static final String TEACHER_VM_ADD_FORM = "/teacher/vm/add/form";
    public static final String TEACHER_VM_ADD = "/teacher/vm/add.do";
    public static final String TEACHER_VM_CONVERT = "/teacher/vm/convert.do";
    public static final String TEACHER_VM_REMOVE = "/teacher/vm/remove.do";
    //    /**
    //     * 教师-模板虚拟机-操作
    //     */
    //    public static final String TEACHER_TVM_LIST            = "/teacher/tvm/list";
    //    public static final String TEACHER_TVM_EDIT_FORM       = "/teacher/tvm/edit/form";
    //    public static final String TEACHER_TVM_EDIT            = "/teacher/tvm/edit.do";
    //    public static final String TEACHER_TVM_CONVERT         = "/teacher/tvm/convert.do";
    /**
     * 教师-虚拟硬盘-操作
     */
    public static final String TEACHER_DISK_LIST = "/teacher/disk/list";
    public static final String TEACHER_DISK_REMOVE = "/teacher/disk/remove.do";
    public static final String TEACHER_DISK_ATTACH_FORM = "/teacher/disk/attach/form";
    public static final String TEACHER_DISK_ATTACH = "/teacher/disk/attach.do";
    public static final String TEACHER_DISK_UNLOAD = "/teacher/disk/unload.do";
    public static final String TEACHER_DISK_ADD_FORM = "/teacher/disk/add/form";
    public static final String TEACHER_DISK_ADD = "/teacher/disk/add";
    public static final String TEACHER_DISK_EDIT_FORM = "/teacher/disk/edit/form";
    public static final String TEACHER_DISK_EDIT = "/teacher/disk/edit.do";
    /**
     * 教师-课程虚拟机-操作
     */
    public static final String TEACHER_CLASS_LIST = "/teacher/class/list";
    public static final String TEACHER_CLASS_STUDENT_LIST = "/teacher/class/student/list";
    public static final String TEACHER_CLASS_VM_LIST = "/teacher/class/vm/list";
    public static final String TEACHER_CLASS_VM_BIND_FORM = "/teacher/class/vm/bind/form";
    public static final String TEACHER_CLASS_VM_BIND = "/teacher/class/vm/bind.do";
    public static final String TEACHER_CLASS_VM_UNBIND = "/teacher/class/vm/unbind.do";

    /************** 以上为教师相关 **********************/
}
