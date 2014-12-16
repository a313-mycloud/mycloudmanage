/*
 * Copyright 2014 etao.com All right reserved. This software is the
 * confidential and proprietary information of etao.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with etao.com .
 */
package org.dlut.mycloudmanage.controller.teacher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dlut.mycloudmanage.common.constant.MenuEnum;
import org.dlut.mycloudmanage.common.constant.UrlConstant;
import org.dlut.mycloudmanage.controller.common.BaseVmController;
import org.dlut.mycloudserver.client.common.usermanage.RoleEnum;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

/**
 * 类TeacherVmController.java的实现描述：TODO 类实现描述
 * 
 * @author xuyizhen Dec 16, 2014 7:11:45 PM
 */
@Controller
public class TeacherVmController extends BaseVmController {
	/**
	 * 教师-虚拟机-列表
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param currentPage
	 * @return
	 */
	@Override
	@RequestMapping(value = UrlConstant.TEACHER_VM_LIST)
	public String vmList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model, Integer currentPage) {

		String result = super.vmList(request, response, model, currentPage);
		if (!result.equals("default")) {
			return result;
		}

		this.setShowMenuList(RoleEnum.TEACHER, MenuEnum.TEACHER_MENU_VM, model);
		model.put("screen", "teacher/vm_list");
		model.put("js", "teacher/vm_list");
		return "default";
	}

	/**
	 * 教师-虚拟机-编辑表单
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param vmUuid
	 * @return
	 */
	@Override
	@RequestMapping(value = UrlConstant.TEACHER_VM_EDIT_FORM)
	public String editForm(HttpServletRequest request,
			HttpServletResponse response, ModelMap model, String vmUuid) {
		String result = super.editForm(request, response, model, vmUuid);
		if (!result.equals("default"))
			return result;
		this.setShowMenuList(RoleEnum.TEACHER, MenuEnum.TEACHER_MENU_VM, model);
		model.put("screen", "teacher/vm_edit_form");
		model.put("js", "teacher/vm_list");

		return "default";

	}

	/**
	 * 教师-虚拟机-编辑
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param vmUuid
	 * @param vmName
	 * @param showType
	 * @param vmDesc
	 * @param showPassword
	 * @return
	 */

	@Override
	@RequestMapping(value = UrlConstant.TEACHER_VM_EDIT, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String vmEdit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model, String vmUuid,
			String vmName, String showType, String vmDesc, String showPassword,
			String vmVcpu, String vmMemory) {
		JSONObject json = new JSONObject();
		json.put("isLogin", true);
		json.put("isAuth", true);

		return super.vmEdit(request, response, model, vmUuid, vmName, showType,
				vmDesc, showPassword, vmVcpu, vmMemory);
	}

	/**
	 * 教师-虚拟机-启动
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param vmUuid
	 * @return
	 */
	@Override
	@RequestMapping(value = UrlConstant.TEACHER_VM_START, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String vmStart(HttpServletRequest request,
			HttpServletResponse response, ModelMap model, String vmUuid) {
		return super.vmStart(request, response, model, vmUuid);
	}

	/**
	 * 教师-虚拟机-关闭
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param vmUuid
	 * @return
	 */
	@Override
	@RequestMapping(value = UrlConstant.TEACHER_VM_SHUTDOWN, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String vmShutDown(HttpServletRequest request,
			HttpServletResponse response, ModelMap model, String vmUuid) {
		return super.vmShutDown(request, response, model, vmUuid);
	}

}
