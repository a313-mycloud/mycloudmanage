/*
 * Copyright 2014 etao.com All right reserved. This software is the
 * confidential and proprietary information of etao.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with etao.com .
 */
package org.dlut.mycloudmanage.common.obj;

import org.dlut.mycloudmanage.common.constant.MenuEnum;

/**
 * 类MenuVO.java的实现描述：TODO 类实现描述
 * 
 * @author luojie.lj 2014年9月23日 下午8:31:54
 */
public class MenuVO {
    private MenuEnum menuEnum;
    private boolean  current = false;

    public MenuEnum getMenuEnum() {
        return menuEnum;
    }

    public void setMenuEnum(MenuEnum menuEnum) {
        this.menuEnum = menuEnum;
    }

    public boolean isCurrent() {
        return current;
    }

    public void setCurrent(boolean current) {
        this.current = current;
    }

}
