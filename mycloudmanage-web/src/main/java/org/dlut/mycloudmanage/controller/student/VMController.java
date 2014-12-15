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
import org.dlut.mycloudmanage.common.utils.MemUnitEnum;
import org.dlut.mycloudmanage.common.utils.MemUtil;
import org.dlut.mycloudmanage.controller.BaseController;
import org.dlut.mycloudserver.client.common.Pagination;
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

	/**
	 * 学生-虚拟机-显示列表
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param currentPage
	 * @return
	 */
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
				vmVO.setVmClass("--");
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
			vmVO.setVmStatus(vmDTO.getVmStatus());
			vmVO.setVmName(vmDTO.getVmName());
			vmVO.setVmUuid(vmDTO.getVmUuid());
			vmVO.setShowType(vmDTO.getShowType());
			vmVO.setVmVcpu(vmDTO.getVmVcpu());
			vmVO.setVmMemory(MemUtil.getMem(vmDTO.getVmMemory(), MemUnitEnum.GB));
			if (vmDTO.getDesc() == null)
				vmVO.setVmDesc("--");
			else
				vmVO.setVmDesc(vmDTO.getDesc());
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

	/**
	 * 学生-虚拟机-编辑表单
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param vmUuid
	 * @return
	 */
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

	/**
	 * 学生-虚拟机-编辑
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param vmUuid
	 * @param vmName
	 * @param showType
	 * @return
	 */
	@RequestMapping(value = UrlConstant.STUDENT_VM_EDIT, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String vmEdit(HttpServletRequest request,
			HttpServletResponse response, ModelMap model, String vmUuid,
			String vmName, String showType, String vmDesc, String showPassword) {

		JSONObject json = new JSONObject();
		json.put("isLogin", true);
		json.put("isAuth", true);
		// 检查vmUuid是否存在
		QueryVmCondition queryVmCondition = new QueryVmCondition();
		queryVmCondition.setVmUuid(vmUuid);
		Pagination<VmDTO> page = this.vmBiz.query(queryVmCondition);
		if (page.getTotalCount() <= 0) {
			json.put("isSuccess", false);
			json.put("message", "要编辑的虚拟机不存在");
			return json.toString();
		}

		VmDTO vmDTO = new VmDTO();
		vmDTO.setVmUuid(vmUuid);
		vmDTO.setVmName(vmName);
		vmDTO.setDesc(vmDesc);
		vmDTO.setShowPassword(showPassword);
		if (Integer.parseInt(showType) == 1)
			vmDTO.setShowType(ShowTypeEnum.SPICE);
		else
			vmDTO.setShowType(ShowTypeEnum.VNC);
		if (this.vmBiz.updateVm(vmDTO)) {
			json.put("isSuccess", true);
			json.put("message", "更新成功");
			return json.toString();
		}
		json.put("isSuccess", false);
		json.put("message", "更新失败");
		return json.toString();
	}

	/**
	 * 学生-虚拟机-开启
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param vmUuid
	 * @return
	 */
	@RequestMapping(value = UrlConstant.STUDENT_VM_START, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String vmStart(HttpServletRequest request,
			HttpServletResponse response, ModelMap model, String vmUuid) {
		JSONObject json = new JSONObject();
		json.put("isLogin", true);
		json.put("isAuth", true);
		// 检查vmUuid是否存在
		QueryVmCondition queryVmCondition = new QueryVmCondition();
		queryVmCondition.setVmUuid(vmUuid);
		Pagination<VmDTO> page = this.vmBiz.query(queryVmCondition);
		if (page.getTotalCount() <= 0) {
			json.put("isSuccess", false);
			json.put("message", "要启动的虚拟机不存在");
			return json.toString();
		}

		if (this.vmBiz.startVm(vmUuid)) {
			json.put("isSuccess", true);
			json.put("message", "虚拟机启动成功");
			return json.toString();
		}
		json.put("isSuccess", false);
		json.put("message", "虚拟机启动失败");
		return json.toString();
	}

	/**
	 * 学生-虚拟机-关闭 返回json
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param vmUuid
	 * @return
	 */
	@RequestMapping(value = UrlConstant.STUDENT_VM_SHUTDOWN, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String vmShutDown(HttpServletRequest request,
			HttpServletResponse response, ModelMap model, String vmUuid) {
		JSONObject json = new JSONObject();
		json.put("isLogin", true);
		json.put("isAuth", true);
		// 检查vmUuid是否存在
		QueryVmCondition queryVmCondition = new QueryVmCondition();
		queryVmCondition.setVmUuid(vmUuid);
		Pagination<VmDTO> page = this.vmBiz.query(queryVmCondition);
		if (page.getTotalCount() <= 0) {
			json.put("isSuccess", false);
			json.put("message", "要关闭的虚拟机不存在");
			return json.toString();
		}

		if (this.vmBiz.forceShutDownVm(vmUuid)) {
			json.put("isSuccess", true);
			json.put("message", "虚拟机关闭成功");
			return json.toString();
		}
		json.put("isSuccess", false);
		json.put("message", "虚拟机关闭失败");
		return json.toString();
	}

}
