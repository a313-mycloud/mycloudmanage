/*
 * Copyright 2014 etao.com All right reserved. This software is the
 * confidential and proprietary information of etao.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with etao.com .
 */
package org.dlut.mycloudmanage.controller.student;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dlut.mycloudmanage.biz.ClassBiz;
import org.dlut.mycloudmanage.biz.HostBiz;
import org.dlut.mycloudmanage.biz.VmBiz;
import org.dlut.mycloudmanage.common.constant.MenuEnum;
import org.dlut.mycloudmanage.common.constant.UrlConstant;
import org.dlut.mycloudmanage.common.obj.VmVO;
import org.dlut.mycloudmanage.controller.BaseController;
import org.dlut.mycloudserver.client.common.Pagination;
import org.dlut.mycloudserver.client.common.usermanage.RoleEnum;
import org.dlut.mycloudserver.client.common.usermanage.UserDTO;
import org.dlut.mycloudserver.client.common.vmmanage.QueryVmCondition;
import org.dlut.mycloudserver.client.common.vmmanage.VmDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * 类VMController.java的实现描述：TODO 类实现描述
 * 
 * @author xuyizhen Dec 12, 2014 3:14:14 AM
 */
@Controller
public class VMController extends BaseController {

	@Resource(name = "vmBiz")
	private VmBiz vmBiz;

	@Resource(name = "classBiz")
	private ClassBiz classBiz;

	@Resource(name = "hostBiz")
	private HostBiz hostBiz;

	private static final int PAGESIZE = 5;

	@RequestMapping(value = UrlConstant.STUDENT_VM_LIST)
	public String vmList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model, Integer currentPage) {

		String errorDesc = setDefaultEnv(request, response, model);
		if (errorDesc != null) {
			return goErrorPage(errorDesc);
		}

		UserDTO userDTO = (UserDTO) model.get("loginUser");

		if (currentPage == null)
			currentPage = 1;
		QueryVmCondition queryVmCondition = new QueryVmCondition();
		queryVmCondition.setUserAccount(userDTO.getAccount());
		queryVmCondition.setLimit(PAGESIZE);
		queryVmCondition.setOffset((currentPage - 1) * PAGESIZE);
		Pagination<VmDTO> pageVmDTO = this.vmBiz.query(queryVmCondition);
		if (pageVmDTO.getTotalPage() >= 1
				&& (currentPage < 1 || currentPage > pageVmDTO.getTotalPage()))
			return this.goErrorPage("该页面不存在");
		List<VmDTO> vmDTOList = pageVmDTO.getList();
		List<VmVO> vmList = new ArrayList<VmVO>();
		for (VmDTO vmDTO : vmDTOList) {
			VmVO vmVO = new VmVO();
			if (vmDTO.getClassId() == null)
				vmVO.setVmClass("-");
			else
				vmVO.setVmClass(this.classBiz.getClassById(vmDTO.getClassId())
						.getClassName());
			if (vmDTO.getVmStatus().getStatus() == 0) {
				vmVO.setHostIp("--");
				vmVO.setShowPort("--");
			} else {
				vmVO.setHostIp(this.hostBiz.getHostById(vmDTO.getHostId())
						.getHostIp());
				vmVO.setShowPort(vmDTO.getShowPort() + "");
			}
			vmVO.setVmStatus(vmDTO.getVmStatus().getDesc());
			vmVO.setVmName("默认");
			vmVO.setVmUuid(vmDTO.getVmUuid());
			vmVO.setShowType(vmDTO.getShowType().getDesc());
			vmList.add(vmVO);
		}
		model.put("vmList", vmList);
		model.put("currentPage", currentPage);
		model.put("totalPage", pageVmDTO.getTotalPage());

		this.setShowMenuList(RoleEnum.STUDENT, MenuEnum.STUDENT_MENU_VM, model);
		model.put("screen", "student/vm_list");
		model.put("js", "student/vm_list");
		return "default";
	}

	@RequestMapping(value = UrlConstant.STUDENT_VM_EDIT_FORM)
	public String editForm(HttpServletRequest request,
			HttpServletResponse response, ModelMap model, String vmUuid) {
		// 查询要修改的内容，以显示在编辑表单中
		QueryVmCondition queryVmCondition = new QueryVmCondition();
		queryVmCondition.setVmUuid(vmUuid);
		VmDTO vmDTO = this.vmBiz.query(queryVmCondition).getList().get(0);
		model.put("vm", vmDTO);
		this.setShowMenuList(RoleEnum.STUDENT, MenuEnum.STUDENT_MENU_VM, model);
		model.put("screen", "student/vm_edit_form");
		model.put("js", "student/vm_list");
		return "default";

	}

}
