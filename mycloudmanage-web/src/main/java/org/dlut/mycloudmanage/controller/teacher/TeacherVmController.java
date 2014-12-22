/*
 * Copyright 2014 etao.com All right reserved. This software is the
 * confidential and proprietary information of etao.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with etao.com .
 */
package org.dlut.mycloudmanage.controller.teacher;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.dlut.mycloudmanage.biz.ImageBiz;
import org.dlut.mycloudmanage.biz.VmBiz;
import org.dlut.mycloudmanage.common.constant.MenuEnum;
import org.dlut.mycloudmanage.common.constant.UrlConstant;
import org.dlut.mycloudmanage.common.utils.MemUnitEnum;
import org.dlut.mycloudmanage.common.utils.MemUtil;
import org.dlut.mycloudmanage.common.utils.MyJsonUtils;
import org.dlut.mycloudmanage.common.utils.MyStringUtils;
import org.dlut.mycloudmanage.controller.common.BaseVmController;
import org.dlut.mycloudserver.client.common.storemanage.ImageDTO;
import org.dlut.mycloudserver.client.common.storemanage.QueryImageCondition;
import org.dlut.mycloudserver.client.common.usermanage.RoleEnum;
import org.dlut.mycloudserver.client.common.usermanage.UserDTO;
import org.dlut.mycloudserver.client.common.vmmanage.QueryVmCondition;
import org.dlut.mycloudserver.client.common.vmmanage.ShowTypeEnum;
import org.dlut.mycloudserver.client.common.vmmanage.VmDTO;
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

	@Resource(name="imageBiz")
	private ImageBiz imageBiz;

	@Resource(name = "vmBiz")
	private VmBiz vmBiz;

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

	@RequestMapping(value = UrlConstant.TEACHER_VM_ADD_FORM)
	public String addVmForm(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
//		QueryImageCondition queryImageCondition = new QueryImageCondition();
//		queryImageCondition.setIsTemplate(true);
//		queryImageCondition.setOffset(0);
//		queryImageCondition.setLimit(100);
//		List<ImageDTO> images = this.imageBiz.query(queryImageCondition)
//				.getList();
		QueryVmCondition queryVmCondition= new QueryVmCondition();
		queryVmCondition.setIsTemplateVm(true);
		queryVmCondition.setOffset(0);
		queryVmCondition.setLimit(1000);
		List<VmDTO> vms=this.vmBiz.query(queryVmCondition).getList();
		model.put("vmList",vms);
		this.setShowMenuList(RoleEnum.TEACHER, MenuEnum.TEACHER_MENU_VM, model);
		model.put("screen", "teacher/vm_add_form");
		model.put("js", "teacher/vm_list");
		return "default";
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param vmName
	 * @param vmVcpu
	 * @param vmMemory
	 * @param imageUuid
	 * @param showType
	 * @param password
	 * @param vmDesc
	 *            允许为空
	 * @return
	 */
	@RequestMapping(value = UrlConstant.TEACHER_VM_ADD, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String addVm(HttpServletRequest request,
			HttpServletResponse response, ModelMap model, String vmName,
			String vmVcpu, String vmMemory, String srcVmUuid, String showType,
			String password, String vmDesc) {
		String errorDesc = setDefaultEnv(request, response, model);
		if (errorDesc != null) {
			return goErrorPage(errorDesc);
		}
		UserDTO userDTO = (UserDTO) model.get("loginUser");

		JSONObject json = new JSONObject();
		if (StringUtils.isBlank(vmName)) {
			return MyJsonUtils.getFailJsonString(json, "虚拟机名字不能为空");
		}
		if (StringUtils.isBlank(password)) {
			return MyJsonUtils.getFailJsonString(json, "密码格式不能为空");
		}
		if (!MyStringUtils.isInteger(vmVcpu)) {
			return MyJsonUtils.getFailJsonString(json, "核心数需要填写一个数字");
		}
		if (!MyStringUtils.isInteger(vmMemory)) {
			return MyJsonUtils.getFailJsonString(json, "内存格式需要填写一个数字");
		}
		if (!MyStringUtils.isInteger(showType)) {
			return MyJsonUtils.getFailJsonString(json, "显示类型格式不正确");
		}
		// 获取作为模板的虚拟机的信息
		
		VmDTO srcVm = this.vmBiz.getVmByUuid(srcVmUuid);
		
		if (srcVm == null) {
			return MyJsonUtils.getFailJsonString(json, "模板虚拟机不存在");
		}
	    ImageDTO destImage=this.imageBiz.cloneImage(srcVm.getImageUuid(),srcVm.getVmName(), false);
		VmDTO vmDTO = new VmDTO();
		if (vmDesc == null)
			vmDTO.setDesc("");
		else
			vmDTO.setDesc(vmDesc);
		vmDTO.setIsTemplateVm(false);
		vmDTO.setParentVmUuid(srcVmUuid);
		vmDTO.setShowPassword(password);
		vmDTO.setImageUuid(destImage.getImageUuid());
		if (Integer.parseInt(showType) == 1)
			vmDTO.setShowType(ShowTypeEnum.SPICE);
		else if (Integer.parseInt(showType) == 2)
			vmDTO.setShowType(ShowTypeEnum.VNC);
		else
			return MyJsonUtils.getFailJsonString(json, "显示类型格式不正确");

		vmDTO.setVmMemory(MemUtil.getMem(Integer.parseInt(vmMemory),
				MemUnitEnum.MB));
		vmDTO.setVmVcpu(Integer.parseInt(vmVcpu));
		vmDTO.setClassId(0);// 在没有绑定课程的情况下，默认为0
		vmDTO.setVmName(vmName);
		vmDTO.setUserAccount(userDTO.getAccount());

		if (this.vmBiz.createVm(vmDTO) == null) {
			this.imageBiz.deleteImageByUuid(destImage.getImageUuid());
			return MyJsonUtils.getFailJsonString(json, "虚拟机创建失败");
		}
		return MyJsonUtils.getSuccessJsonString(json, "虚拟机创建成功");
	}
}
