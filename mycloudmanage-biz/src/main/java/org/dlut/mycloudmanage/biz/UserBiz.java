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
import org.dlut.mycloudserver.client.common.Pagination;
import org.dlut.mycloudserver.client.common.usermanage.QueryUserCondition;
import org.dlut.mycloudserver.client.common.usermanage.RoleEnum;
import org.dlut.mycloudserver.client.common.usermanage.UserCreateReqDTO;
import org.dlut.mycloudserver.client.common.usermanage.UserDTO;
import org.dlut.mycloudserver.client.service.usermanage.IUserManageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author xuyizhen
 */
@Service
public class UserBiz {

    private static Logger      log = LoggerFactory.getLogger(UserBiz.class);

    @Resource(name = "userManageService")
    private IUserManageService userManageService;

    
    public void hello() {
        System.out.println("hello");
    }

    /**
     * 验证用户是否合法
     * 
     * @param account
     * @param password
     * @param role
     * @return 如果合法，则返回userDTO，否则返回null
     */
    public UserDTO verifyAndGetUser(String account, String password, RoleEnum role) {
        MyCloudResult<UserDTO> result = userManageService.verifyAndGetUser(account, password, role);
        if (!result.isSuccess()) {
            log.warn("调用 userManageService.verifyAndGetUser（" + account + "," + password + "," + role + ") error:"
                    + result.getMsgInfo());
            return null;
        }
        return result.getModel();
    }
    
    /**
     * 
     * @param account
     * @return
     */
    public UserDTO getUserByAccount(String account){
    	MyCloudResult<UserDTO> result=this.userManageService.getUserByAccount(account);
    	if(!result.isSuccess()){
    		log.warn("调用userManageService.getUserByAccount()出错");
    		return null;
    	}
    	return result.getModel();
    }

 

    /**
     * 创建新用户，如果账号存在，则返回false
     * 
     * @param account
     * @param password
     * @param roleEnum
     * @return
     */
    public  boolean createUser(UserCreateReqDTO userCreateReqDTO){
    	MyCloudResult<Boolean> result =  this.userManageService.createUser(userCreateReqDTO);
    	if(!result.isSuccess()){
    		log.warn("调用userManageService.createUser()出错");
    		return false;
    	}
    	return result.getModel();
    }

    /**
     * 统计用户数量
     * 
     * @param queryUserCondition
     * @return
     */
    public int countQuery(QueryUserCondition queryUserCondition){
    	MyCloudResult<Integer> result =  this.userManageService.countQuery(queryUserCondition);
    	if(!result.isSuccess()){
    		log.warn("调用userManageService.countQuery()出错");
    		return 0;
    	}
    	return result.getModel();
    }

    /**
     * 分页查询用户
     * 
     * @param queryUserCondition
     * @return
     */
    public Pagination<UserDTO> query(QueryUserCondition queryUserCondition){
    	MyCloudResult<Pagination<UserDTO>> result =  this.userManageService.query(queryUserCondition);
    	if(!result.isSuccess()){
    		log.warn("调用userManageService.query()出错");
    		return null;
    	}
    	return result.getModel();
    	
    }
}
