/*
 * Copyright 2014 etao.com All right reserved. This software is the
 * confidential and proprietary information of etao.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with etao.com .
 */
package org.dlut.mycloudmanage.biz;

import javax.annotation.Resource;

import org.dlut.mycloudserver.client.common.MyCloudResult;
import org.dlut.mycloudserver.client.common.usermanage.RoleEnum;
import org.dlut.mycloudserver.client.common.usermanage.UserDTO;
import org.dlut.mycloudserver.client.service.usermanage.IUserManageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 类UserBiz.java的实现描述：TODO 类实现描述
 * 
 * @author luojie 2014年11月21日 下午4:18:29
 */
@Service
public class UserBiz {

    private static Logger      log = LoggerFactory.getLogger(UserBiz.class);

    @Resource(name = "userManageService")
    private IUserManageService userManageService;

    public void hello() {
        System.out.println("hello");
    }

    public UserDTO verifyAndGetUser(String account, String password, RoleEnum role) {
        MyCloudResult<UserDTO> result = userManageService.verifyAndGetUser(account, password, role);
        if (!result.isSuccess()) {
            log.warn("调用 userManageService.verifyAndGetUser（" + account + "," + password + "," + role + ") error:"
                    + result.getMsgInfo());
            return null;
        }
        return result.getModel();
    }
}
