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
import org.dlut.mycloudmanage.biz.ClassBiz;
import org.dlut.mycloudmanage.biz.ImageBiz;
import org.dlut.mycloudmanage.biz.VmBiz;
import org.dlut.mycloudmanage.common.constant.MenuEnum;
import org.dlut.mycloudmanage.common.constant.UrlConstant;
import org.dlut.mycloudmanage.common.utils.MemUnitEnum;
import org.dlut.mycloudmanage.common.utils.MemUtil;
import org.dlut.mycloudmanage.common.utils.MyJsonUtils;
import org.dlut.mycloudmanage.common.utils.MyStringUtils;
import org.dlut.mycloudmanage.controller.common.BaseVmController;
import org.dlut.mycloudserver.client.common.Pagination;
import org.dlut.mycloudserver.client.common.usermanage.RoleEnum;
import org.dlut.mycloudserver.client.common.usermanage.UserDTO;
import org.dlut.mycloudserver.client.common.vmmanage.NetworkTypeEnum;
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
 * @author xuyizhen 2014年12月24日 下午8:40:36
 */
@Controller
public class TeacherVmController extends BaseVmController {

    @Resource(name = "imageBiz")
    private ImageBiz imageBiz;

    @Resource(name = "vmBiz")
    private VmBiz    vmBiz;

    @Resource(name = "classBiz")
    private ClassBiz classBiz;

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
    public String vmList(HttpServletRequest request, HttpServletResponse response, ModelMap model, Integer currentPage) {

        String result = super.vmList(request, response, model, currentPage);
        if (!result.equals("default")) {
            return result;
        }

        this.setShowMenuList(RoleEnum.TEACHER, MenuEnum.TEACHER_VM_LIST, model);
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
    public String editForm(HttpServletRequest request, HttpServletResponse response, ModelMap model, String vmUuid) {
        String result = super.editForm(request, response, model, vmUuid);
        if (!result.equals("default"))
            return result;
        this.setShowMenuList(RoleEnum.TEACHER, MenuEnum.TEACHER_VM_LIST, model);
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
    public String vmEdit(HttpServletRequest request, HttpServletResponse response, ModelMap model, String vmUuid,
                         String vmName, String showType, String vmDesc, String showPassword, String vmVcpu,
                         String vmMemory, String vmNetworkType) {

        return super.vmEdit(request, response, model, vmUuid, vmName, showType, vmDesc, showPassword, vmVcpu, vmMemory,
                vmNetworkType);
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
    public String vmStart(HttpServletRequest request, HttpServletResponse response, ModelMap model, String vmUuid) {
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
    public String vmShutDown(HttpServletRequest request, HttpServletResponse response, ModelMap model, String vmUuid) {
        return super.vmShutDown(request, response, model, vmUuid);
    }

    @RequestMapping(value = UrlConstant.TEACHER_VM_ADD_FORM)
    public String addVmForm(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        String errorDesc = setDefaultEnv(request, response, model);
        if (errorDesc != null) {
            return goErrorPage(errorDesc);
        }
        UserDTO userDTO = (UserDTO) model.get("loginUser");
        //系统级别母板
        QueryVmCondition queryVmCondition = new QueryVmCondition();
        queryVmCondition.setIsTemplateVm(true);
        queryVmCondition.setIsPublicTemplate(true);
        //包括IsPublicTemplateVm为true和false  的情况，true为管理员创建的模板虚拟机，所有用户可见，false为当前用户创建，仅自己可见
        queryVmCondition.setOffset(0);
        queryVmCondition.setLimit(1000);
        Pagination<VmDTO> pagination=this.vmBiz.query(queryVmCondition);
        if(pagination==null)
        	return this.goErrorPage("查询模板列表失败");
        List<VmDTO> vms = pagination.getList();
      //当前用户级别母板
        QueryVmCondition queryVmCondition1=new QueryVmCondition();
        queryVmCondition1.setIsTemplateVm(true);
        queryVmCondition1.setIsPublicTemplate(false);
        queryVmCondition1.setUserAccount(userDTO.getAccount());
        queryVmCondition1.setOffset(0);
        queryVmCondition1.setLimit(1000);
        Pagination<VmDTO> pagination1=this.vmBiz.query(queryVmCondition1);
        if(pagination1==null)
        	return this.goErrorPage("查询模板列表失败");
        vms.addAll(pagination1.getList());
        
        model.put("vmList", vms);
        this.setShowMenuList(RoleEnum.TEACHER, MenuEnum.TEACHER_VM_LIST, model);
        model.put("screen", "teacher/vm_add_form");
        model.put("js", "teacher/vm_list");
        return "default";
    }

    /**
     * @param request
     * @param response
     * @param model
     * @param vmName
     * @param vmVcpu
     * @param vmMemory
     * @param imageUuid
     * @param showType
     * @param password
     * @param vmDesc 允许为空
     * @return
     */
    @RequestMapping(value = UrlConstant.TEACHER_VM_ADD, produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public String addVm(HttpServletRequest request, HttpServletResponse response, ModelMap model, String vmName,
                        String vmVcpu, String vmMemory, String srcVmUuid, String showType, String showPassword,
                        String vmDesc, String vmNetworkType) {
        String errorDesc = setDefaultEnv(request, response, model);
        if (errorDesc != null) {
            return goErrorPage(errorDesc);
        }
        UserDTO userDTO = (UserDTO) model.get("loginUser");

        JSONObject json = new JSONObject();
        if (StringUtils.isBlank(vmName)) {
            return MyJsonUtils.getFailJsonString(json, "虚拟机名字不能为空");
        }
        if (StringUtils.isBlank(showPassword)) {
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
        if (!MyStringUtils.isInteger(vmNetworkType)) {
            return MyJsonUtils.getFailJsonString(json, "网络格式不正确");
        }
        // 获取作为模板的虚拟机的信息

        VmDTO srcVm = this.vmBiz.getVmByUuid(srcVmUuid);

        if (srcVm == null) {
            return MyJsonUtils.getFailJsonString(json, "模板虚拟机不存在");
        }

        //设置目标虚拟机的配置
        VmDTO destVm = new VmDTO();
        destVm.setVmName(vmName);
        destVm.setUserAccount(userDTO.getAccount());
        destVm.setVmMemory(MemUtil.getMem(Integer.parseInt(vmMemory), MemUnitEnum.MB));
        destVm.setVmVcpu(Integer.parseInt(vmVcpu));

        if (ShowTypeEnum.getShowTypeByValue(Integer.parseInt(showType)) == null)
            MyJsonUtils.getSuccessJsonString(json, "显示类型不存在");
        destVm.setShowType(ShowTypeEnum.getShowTypeByValue(Integer.parseInt(showType)));

        if (Integer.parseInt(vmNetworkType) == 1)
            destVm.setVmNetworkType(NetworkTypeEnum.NAT);
        else if (Integer.parseInt(vmNetworkType) == 2)
            destVm.setVmNetworkType(NetworkTypeEnum.BRIDGE);
        else
            return MyJsonUtils.getFailJsonString(json, "网络格式不正确");

        destVm.setClassId(0);// 在没有绑定课程的情况下，默认为0
        if (StringUtils.isBlank(vmDesc))
            destVm.setDesc("老师" + userDTO.getAccount() + "创建");
        else
            destVm.setDesc(vmDesc);
        destVm.setIsTemplateVm(false);
        destVm.setIsPublicTemplate(false);
        destVm.setShowPassword(showPassword);
        destVm.setMasterDiskBusType(srcVm.getMasterDiskBusType());
        destVm.setInterfaceType(srcVm.getInterfaceType());
        if (this.vmBiz.cloneVm(destVm, srcVmUuid) == null) {
            return MyJsonUtils.getFailJsonString(json, "虚拟机创建失败");
        }
        return MyJsonUtils.getSuccessJsonString(json, "虚拟机创建成功");
    }

    @RequestMapping(value = UrlConstant.TEACHER_VM_CONVERT, produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public String convert(HttpServletRequest request, HttpServletResponse response, ModelMap model, String vmUuid) {
        JSONObject json = new JSONObject();

        // 检查vmUuid是否存在
        QueryVmCondition queryVmCondition = new QueryVmCondition();
        queryVmCondition.setVmUuid(vmUuid);
        Pagination<VmDTO> page = this.vmBiz.query(queryVmCondition);
        if (page.getTotalCount() <= 0) {
            return MyJsonUtils.getFailJsonString(json, "要操作的虚拟机不存在");
        }

        if (this.vmBiz.changeToTemplateVm(vmUuid)) {
            return MyJsonUtils.getSuccessJsonString(json, "转换成功");
        }
        return MyJsonUtils.getFailJsonString(json, "转换失败");
    }

    @RequestMapping(value = UrlConstant.TEACHER_VM_REMOVE, produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public String removeVm(HttpServletRequest request, HttpServletResponse response, ModelMap model, String vmUuid) {
        JSONObject json = new JSONObject();

        // 检查vmUuid是否存在
        QueryVmCondition queryVmCondition = new QueryVmCondition();
        queryVmCondition.setVmUuid(vmUuid);
        Pagination<VmDTO> page = this.vmBiz.query(queryVmCondition);
        if (page.getTotalCount() <= 0) {
            return MyJsonUtils.getFailJsonString(json, "要操作的虚拟机不存在");
        }
        if (this.vmBiz.deleteVm(vmUuid)) {
            return MyJsonUtils.getSuccessJsonString(json, "删除成功");
        }
        return MyJsonUtils.getFailJsonString(json, "删除失败");
    }

}
