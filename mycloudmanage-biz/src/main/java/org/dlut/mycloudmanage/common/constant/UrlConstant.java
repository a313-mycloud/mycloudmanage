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
    /**
     * 登陆页面
     */
    public static final String LOGIN_URL                  = "/login";

    /**
     * 登出页面
     */
    public static final String LOGOUT_URL                 = "/logout";

    /**
     * 错误页面
     */
    public static final String ERROR_URL                  = "/error";

    public static final String DEFAULT_URL                = "index";

    /**
     * 学生角色虚拟机列表页面
     */
    public static final String STUDENT_VM_LIST            = "/student/vm/list";

    /**
     * 学生角色登陆后的默认页
     */
    public static final String STUDENT_DEFAULT_URL        = STUDENT_VM_LIST;

    /**
     * 教师角色登陆后的默认页
     */
    public static final String TEACHER_DEFAULT_URL        = "/teacher/index";

    /**
     * 管理员登陆后的默认页
     */
    public static final String ADMIN_DEFAULT_URL          = "/admin/index";

    public static final String ADMIN_ACCOUNT_STUDENT_LIST = "/admin/account/student/list"; // 管理员-账号管理-学生列表
    public static final String ADMIN_ACCOUNT_TEACHER_LIST = "/admin/account/teacher/list"; // 管理员-账号管理-教师列表
    public static final String ADMIN_ACCOUNT_ADMIN_LIST   = "/admin/account/admin/list";  // 管理员-账号管理-管理员列表

}
