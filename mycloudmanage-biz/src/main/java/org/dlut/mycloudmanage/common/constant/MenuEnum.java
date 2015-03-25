/*
 * Copyright 2014 etao.com All right reserved. This software is the
 * confidential and proprietary information of etao.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with etao.com .
 */
package org.dlut.mycloudmanage.common.constant;

import org.dlut.mycloudserver.client.common.usermanage.RoleEnum;

/**
 * 类MenuEnum.java的实现描述：TODO 类实现描述
 * 
 * @author luojie.lj 2014年9月23日 下午8:40:38
 */
public enum MenuEnum {
    TEACHER_INDEX("首页", UrlConstant.TEACHER_DEFAULT_URL, RoleEnum.TEACHER),
    TEACHER_VM_LIST("私有虚拟机管理", UrlConstant.TEACHER_VM_LIST, RoleEnum.TEACHER),
    TEACHER_TVM_LIST("模板虚拟机管理", UrlConstant.TEACHER_TVM_LIST, RoleEnum.TEACHER),
    TEACHER_CLASS_LIST("课程虚拟机管理", UrlConstant.TEACHER_CLASS_LIST, RoleEnum.TEACHER),
    TEACHER_DISK_LIST("虚拟硬盘管理", UrlConstant.TEACHER_DISK_LIST, RoleEnum.TEACHER),

    STUDENT_INDEX("首页", UrlConstant.STUDENT_DEFAULT_URL, RoleEnum.STUDENT),
    STUDENT_VM_LIST("私有虚拟机管理", UrlConstant.STUDENT_VM_LIST, RoleEnum.STUDENT),
    STUDENT_DiSK_LIST("虚拟硬盘管理", UrlConstant.STUDENT_DISK_LIST, RoleEnum.STUDENT),

    ADMIN_INDEX("首页", UrlConstant.ADMIN_DEFAULT_URL, RoleEnum.ADMIN),
    ADMIN_ACCOUNT_LIST("账号管理", UrlConstant.ADMIN_ACCOUNT_LIST, RoleEnum.ADMIN),
    ADMIN_CLASS_LIST("课程管理", UrlConstant.ADMIN_CLASS_LIST, RoleEnum.ADMIN),
    ADMIN_IMAGE_LIST("镜像管理", UrlConstant.ADMIN_IMAGE_LIST, RoleEnum.ADMIN),
    ADMIN_VM_LIST("虚拟机管理", UrlConstant.ADMIN_VM_LIST, RoleEnum.ADMIN),
    ADMIN_HOST_LIST("物理机管理", UrlConstant.ADMIN_HOST_LIST, RoleEnum.ADMIN);

    private String   name;
    private String   route;
    private RoleEnum role;

    private MenuEnum(String name, String route, RoleEnum role) {
        this.name = name;
        this.route = route;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }

}
