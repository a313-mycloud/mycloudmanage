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
    STUDENT_MENU_VM("虚拟机", UrlConstant.STUDENT_VM_LIST, RoleEnum.STUDENT),
    STUDENT_MENU_DISK("硬盘", "", RoleEnum.STUDENT);

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
