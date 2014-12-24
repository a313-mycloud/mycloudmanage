/*
 * Copyright 2014 etao.com All right reserved. This software is the
 * confidential and proprietary information of etao.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with etao.com .
 */
package org.dlut.mycloudmanage.controller.common;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dlut.mycloudmanage.biz.ClassBiz;
import org.dlut.mycloudmanage.biz.HostBiz;
import org.dlut.mycloudmanage.biz.VmBiz;
import org.dlut.mycloudmanage.common.constant.UrlConstant;
import org.dlut.mycloudmanage.common.obj.VmVO;
import org.dlut.mycloudmanage.common.utils.MemUnitEnum;
import org.dlut.mycloudmanage.common.utils.MemUtil;
import org.dlut.mycloudmanage.common.utils.MyStringUtils;
import org.dlut.mycloudserver.client.common.Pagination;
import org.dlut.mycloudserver.client.common.usermanage.UserDTO;
import org.dlut.mycloudserver.client.common.vmmanage.QueryVmCondition;
import org.dlut.mycloudserver.client.common.vmmanage.ShowTypeEnum;
import org.dlut.mycloudserver.client.common.vmmanage.VmDTO;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

/**
 * 类VMController.java的实现描述：TODO 类实现描述
 * 
 * @author xuyizhen Dec 16, 2014 6:51:22 PM
 */
public class BaseVmController extends BaseController {
    public static final int PAGESIZE = 5;

    @Resource(name = "vmBiz")
    private VmBiz           vmBiz;

    @Resource(name = "classBiz")
    private ClassBiz        classBiz;

    @Resource(name = "hostBiz")
    private HostBiz         hostBiz;

    public String vmList(HttpServletRequest request, HttpServletResponse response, ModelMap model, Integer currentPage) {

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
        queryVmCondition.setIsTemplateVm(false);
        Pagination<VmDTO> pageVmDTO = this.vmBiz.query(queryVmCondition);
        if (pageVmDTO.getTotalPage() >= 1 && (currentPage < 1 || currentPage > pageVmDTO.getTotalPage())) {

            return this.goErrorPage("该页面不存在");
        }
        List<VmDTO> vmDTOList = pageVmDTO.getList();
        List<VmVO> vmList = new ArrayList<VmVO>();
        for (VmDTO vmDTO : vmDTOList) {
            VmVO vmVO = new VmVO();
            if (vmDTO.getClassId() == 0)
                vmVO.setVmClass("--");
            else
                vmVO.setVmClass(this.classBiz.getClassById(vmDTO.getClassId()).getClassName());
            if (vmDTO.getVmStatus().getStatus() == 0) {
                vmVO.setHostIp("--");
                vmVO.setShowPort("--");
            } else {
                vmVO.setHostIp(this.hostBiz.getHostById(vmDTO.getHostId()).getHostIp());
                vmVO.setShowPort(vmDTO.getShowPort() + "");
            }
            vmVO.setVmStatus(vmDTO.getVmStatus());
            vmVO.setVmName(vmDTO.getVmName());
            vmVO.setVmUuid(vmDTO.getVmUuid());
            vmVO.setShowType(vmDTO.getShowType());
            vmVO.setVmVcpu(vmDTO.getVmVcpu());
            vmVO.setVmMemory(MemUtil.getMem(vmDTO.getVmMemory(), MemUnitEnum.MB));
            if (vmDTO.getDesc() == null)
                vmVO.setVmDesc("--");
            else
                vmVO.setVmDesc(vmDTO.getDesc());
            vmList.add(vmVO);
        }
        model.put("vmList", vmList);
        model.put("currentPage", currentPage);
        model.put("totalPage", pageVmDTO.getTotalPage());
        return "default";

    }

    /**
     * @param request
     * @param response
     * @param model
     * @param vmUuid
     * @return
     */
    public String editForm(HttpServletRequest request, HttpServletResponse response, ModelMap model, String vmUuid) {
        String errorDesc = setDefaultEnv(request, response, model);
        if (errorDesc != null) {
            return goErrorPage(errorDesc);
        }
        UserDTO userDTO = (UserDTO) model.get("loginUser");
        // 查询要修改的内容，以显示在编辑表单中
        QueryVmCondition queryVmCondition = new QueryVmCondition();
        queryVmCondition.setVmUuid(vmUuid);
        VmDTO vmDTO = this.vmBiz.query(queryVmCondition).getList().get(0);
        VmVO vm = new VmVO();
        vm.setVmName(vmDTO.getVmName());
        vm.setVmDesc(vmDTO.getDesc());
        vm.setVmVcpu(vmDTO.getVmVcpu());
        vm.setVmMemory(MemUtil.getMem(vmDTO.getVmMemory(), MemUnitEnum.MB));
        vm.setShowType(vmDTO.getShowType());
        vm.setVmPass(vmDTO.getShowPassword());
        vm.setVmUuid(vmDTO.getVmUuid());
        model.put("vm", vm);

        return "default";

    }

    /**
     * @param request
     * @param response
     * @param model
     * @param vmUuid
     * @param vmName
     * @param showType
     * @param vmDesc
     * @param showPassword
     * @param vmVcpu
     * @param vmMemory MB
     * @return
     */

    public String vmEdit(HttpServletRequest request, HttpServletResponse response, ModelMap model, String vmUuid,
                         String vmName, String showType, String vmDesc, String showPassword, String vmVcpu,
                         String vmMemory) {
        String errorDesc = setDefaultEnv(request, response, model);
        if (errorDesc != null) {
            return goErrorPage(errorDesc);
        }

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
        if (!MyStringUtils.isInteger(vmVcpu)) {
            json.put("isSuccess", false);
            json.put("message", "核心数格式不正确");
            return json.toString();
        }
        if (!MyStringUtils.isInteger(vmMemory)) {
            json.put("isSuccess", false);
            json.put("message", "内存格式不正确");
            return json.toString();
        }
        VmDTO vmDTO = new VmDTO();
        vmDTO.setVmUuid(vmUuid);
        vmDTO.setVmName(vmName);
        vmDTO.setDesc(vmDesc);
        vmDTO.setShowPassword(showPassword);
        vmDTO.setVmVcpu(Integer.parseInt(vmVcpu));
        vmDTO.setVmMemory(MemUtil.getMem(Integer.parseInt(vmMemory), MemUnitEnum.MB));
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

    /*
     * @param request
     * @param response
     * @param model
     * @param vmUuid
     * @return
     */
    @RequestMapping(value = { UrlConstant.STUDENT_VM_START, UrlConstant.TEACHER_VM_START }, produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public String vmStart(HttpServletRequest request, HttpServletResponse response, ModelMap model, String vmUuid) {
        String errorDesc = setDefaultEnv(request, response, model);
        if (errorDesc != null) {
            return goErrorPage(errorDesc);
        }

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
     * @param request
     * @param response
     * @param model
     * @param vmUuid
     * @return
     */
    public String vmShutDown(HttpServletRequest request, HttpServletResponse response, ModelMap model, String vmUuid) {
        String errorDesc = setDefaultEnv(request, response, model);
        if (errorDesc != null) {
            return goErrorPage(errorDesc);
        }

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
