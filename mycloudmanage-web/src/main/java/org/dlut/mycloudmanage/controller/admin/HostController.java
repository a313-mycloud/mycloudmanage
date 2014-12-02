/*
 * Copyright 2014 etao.com All right reserved. This software is the
 * confidential and proprietary information of etao.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with etao.com .
 */
package org.dlut.mycloudmanage.controller.admin;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.dlut.mycloudmanage.biz.HostBiz;
import org.dlut.mycloudmanage.common.constant.MenuEnum;
import org.dlut.mycloudmanage.common.constant.UrlConstant;
import org.dlut.mycloudmanage.controller.BaseController;
import org.dlut.mycloudserver.client.common.Pagination;
import org.dlut.mycloudserver.client.common.hostmanage.HostDTO;
import org.dlut.mycloudserver.client.common.hostmanage.QueryHostCondition;
import org.dlut.mycloudserver.client.common.usermanage.RoleEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 类HostController.java的实现描述：TODO 类实现描述
 * 
 * @author xuyizhen 2014年11月30日
 */
@Controller
public class HostController extends BaseController {

	private static Logger log = LoggerFactory.getLogger(HostController.class);

	@Resource(name = "hostBiz")
	private HostBiz hostBiz;

	/**
	 * 管理员-物理机-显示列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */

	@RequestMapping(value = UrlConstant.ADMIN_HOST_LIST)
	public String adminHostList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model, int page) {

		String errorDesc = this.setDefaultEnv(request, response, model);
		if (errorDesc != null) {
			return this.goErrorPage(errorDesc);
		}

		QueryHostCondition queryHostCondition = new QueryHostCondition();
		Pagination<HostDTO> pageHostDTO = this.hostBiz
				.query(queryHostCondition);
		List<HostDTO> hostList = pageHostDTO.getList();
		model.put("hostList", hostList);
		this.setShowMenuList(RoleEnum.ADMIN, MenuEnum.ADMIN_HOST_LIST, model);
		model.put("screen", "admin/host_list");
		model.put("js", "admin/host_list");
		return "default";
	}

	/**
	 * 管理员-物理机-删除 处理异步请求，返回JSON
	 * 
	 * @param hostId
	 * @return
	 */
	@RequestMapping(value = UrlConstant.ADMIN_HOST_REMOVE)
	@ResponseBody
	public String removeHost(int hostId) {
		JSONObject json = new JSONObject();
		json.put("isLogin", true);
		json.put("isAuth", true);
		if (this.hostBiz.deleteHostById(hostId)) {
			log.info("删除" + hostId + "成功");
			json.put("isSuccess", true);
			json.put("message", "删除成功");
			json.put("data", "");
			return json.toString();
		}
		log.info("删除" + hostId + "失败");
		json.put("isSuccess", false);
		json.put("message", "该物理机不存在");
		json.put("data", "");

		return json.toString();
	}

	/**
	 * 响应客户端请求，跳转到添加表单
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = UrlConstant.ADMIN_ADD_FORM)
	public String addHostForm(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		this.setShowMenuList(RoleEnum.ADMIN, MenuEnum.ADMIN_HOST_LIST, model);
		model.put("screen", "admin/add_form");
		model.put("js", "admin/host_list");
		return "default";
	}

	/**
	 * 响应异步添加请求，返回JSON
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param hostName
	 * @param hostIp
	 * @return
	 */
	@RequestMapping(value = UrlConstant.ADMIN_HOST_ADD, method = RequestMethod.POST)
	@ResponseBody
	public String addHost(HttpServletRequest request,
			HttpServletResponse response, ModelMap model, String hostName,
			String hostIp) {

		JSONObject json = new JSONObject();
		json.put("isLogin", true);
		json.put("isAuth", true);
		// 检查IP
		QueryHostCondition queryHostCondition = new QueryHostCondition();
		queryHostCondition.setHostIp(hostIp);

		if (this.hostBiz.query(queryHostCondition).getTotalCount() > 0) {
			json.put("isSuccess", false);
			json.put("message", "要添加的IP已经存在");
			return json.toString();
		}

		// 添加记录
		HostDTO hostDTO = new HostDTO();
		hostDTO.setHostIp(hostIp);
		hostDTO.setHostName(hostName);

		if (this.hostBiz.createHost(hostDTO) > 0) {
			json.put("isSuccess", true);
			json.put("message", "添加成功");
			return json.toString();
		}

		json.put("isSuccess", false);
		json.put("message", "添加失败");
		return json.toString();
	}

	/**
	 * 返回编辑表单
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param hostId
	 * @return
	 */
	@RequestMapping(value = UrlConstant.ADMIN_EDIT_FORM)
	public String editForm(HttpServletRequest request,
			HttpServletResponse response, ModelMap model, int hostId) {
		// 查询要修改的内容，以显示在编辑表单中
		QueryHostCondition queryHostCondition = new QueryHostCondition();
		queryHostCondition.setHostId(hostId);
		HostDTO hostDTO = this.hostBiz.query(queryHostCondition).getList()
				.get(0);
		model.put("host", hostDTO);
		this.setShowMenuList(RoleEnum.ADMIN, MenuEnum.ADMIN_HOST_LIST, model);
		model.put("screen", "admin/edit_form");
		model.put("js", "admin/host_list");
		return "default";

	}

	/**
	 * 处理更新异步请求，返回JSON
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param hostId
	 * @param hostName
	 * @param hostIp
	 * @return
	 */
	@RequestMapping(value = UrlConstant.ADMIN_HOST_EDIT)
	@ResponseBody
	public String editHost(HttpServletRequest request,
			HttpServletResponse response, ModelMap model, int hostId,
			String hostName, String hostIp) {
		JSONObject json = new JSONObject();
		json.put("isLogin", true);
		json.put("isAuth", true);
		// 检查id是否存在
		QueryHostCondition queryHostCondition1 = new QueryHostCondition();
		queryHostCondition1.setHostId(hostId);
		if (this.hostBiz.query(queryHostCondition1).getTotalCount() <= 0) {
			json.put("isSuccess", false);
			json.put("message", "要编辑的物理机不存在");
			return json.toString();
		}
		// 检查Ip是否存在
		QueryHostCondition queryHostCondition2 = new QueryHostCondition();
		queryHostCondition2.setHostIp(hostIp);
		if (this.hostBiz.query(queryHostCondition2).getTotalCount() > 0) {
			json.put("isSuccess", false);
			json.put("message", "要编辑的Ip已经存在");
			return json.toString();
		}
		HostDTO hostDTO = new HostDTO();
		hostDTO.setHostId(hostId);
		hostDTO.setHostIp(hostIp);
		hostDTO.setHostName(hostName);
		if (this.hostBiz.updateHost(hostDTO)) {
			json.put("isSuccess", true);
			json.put("message", "更新成功");
			return json.toString();
		}
		json.put("isSuccess", false);
		json.put("message", "更新失败");
		return json.toString();
	}
}
