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
import org.dlut.mycloudserver.client.common.classmanage.ClassDTO;
import org.dlut.mycloudserver.client.common.classmanage.QueryClassCondition;
import org.dlut.mycloudserver.client.service.classmanage.IClassManageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 类ClassBiz.java的实现描述：TODO 类实现描述
 * 
 * @author xuyizhen Dec 11, 2014 4:27:41 PM
 */
@Service
public class ClassBiz {
	private static Logger log = LoggerFactory.getLogger(UserBiz.class);

	@Resource(name = "classManageService")
	private IClassManageService classManageService;

	/**
	 * 根据id获取课程信息
	 * 
	 * @param classId
	 * @return
	 */
	public ClassDTO getClassById(int classId) {
		MyCloudResult<ClassDTO> result = this.classManageService
				.getClassById(classId);
		if (!result.isSuccess()) {
			log.warn("调用classManageService.getClassById(" + classId + ")出错，"
					+ result.getMsgCode() + ":" + result.getMsgInfo());
			return null;
		}
		return result.getModel();
	}

	/**
	 * 创建一个新的课程
	 * 
	 * @param classDTO
	 * @return
	 */
	public int createClass(ClassDTO classDTO) {
		MyCloudResult<Integer> result = this.classManageService
				.createClass(classDTO);
		if (!result.isSuccess()) {
			log.warn("调用classManageService.createClass()出错，"
					+ result.getMsgCode() + ":" + result.getMsgInfo());
			return -1;
		}
		return result.getModel();
	}

	/**
	 * 更新课程
	 * 
	 * @param classDTO
	 * @return
	 */
	public boolean updateClass(ClassDTO classDTO) {
		MyCloudResult<Boolean> result = this.classManageService
				.updateClass(classDTO);
		if (!result.isSuccess()) {
			log.warn("调用classManageService.updateClass()出错，"
					+ result.getMsgCode() + ":" + result.getMsgInfo());
			return false;
		}
		return result.getModel();

	}

	/**
	 * 删除课程
	 * 
	 * @param classDTO
	 * @return
	 */
	public boolean deleteClass(int classId) {
		MyCloudResult<Boolean> result = this.classManageService
				.deleteClass(classId);

		if (!result.isSuccess()) {
			log.warn("调用classManageService.deleteClass()出错，"
					+ result.getMsgCode() + ":" + result.getMsgInfo());
			return false;
		}
		return result.getModel();
	}

	/**
	 * 根据条件统计课程数量
	 * 
	 * @param queryClassCondition
	 * @return
	 */
	public int countQuery(QueryClassCondition queryClassCondition) {
		MyCloudResult<Integer> result = this.classManageService
				.countQuery(queryClassCondition);

		if (!result.isSuccess()) {
			log.warn("调用classManageService.countQuery()出错，"
					+ result.getMsgCode() + ":" + result.getMsgInfo());
			return -1;
		}
		return result.getModel();
	}

	/**
	 * 根据条件查询课程列表
	 * 
	 * @param queryClassCondition
	 * @return
	 */
	public Pagination<ClassDTO> query(QueryClassCondition queryClassCondition) {
		MyCloudResult<Pagination<ClassDTO>> result = this.classManageService
				.query(queryClassCondition);

		if (!result.isSuccess()) {
			log.warn("调用classManageService.query()出错，" + result.getMsgCode()
					+ ":" + result.getMsgInfo());
			return null;
		}
		return result.getModel();
	}

}
