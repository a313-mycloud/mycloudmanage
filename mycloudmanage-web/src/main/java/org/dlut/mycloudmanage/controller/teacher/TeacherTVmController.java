///*
// * Copyright 2014 etao.com All right reserved. This software is the
// * confidential and proprietary information of etao.com ("Confidential
// * Information"). You shall not disclose such Confidential Information and shall
// * use it only in accordance with the terms of the license agreement you entered
// * into with etao.com .
// */
//package org.dlut.mycloudmanage.controller.teacher;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.dlut.mycloudmanage.biz.ClassBiz;
//import org.dlut.mycloudmanage.biz.VmBiz;
//import org.dlut.mycloudmanage.common.constant.MenuEnum;
//import org.dlut.mycloudmanage.common.constant.UrlConstant;
//import org.dlut.mycloudmanage.common.obj.VmVO;
//import org.dlut.mycloudmanage.common.property.utils.MyPropertiesUtil;
//import org.dlut.mycloudmanage.common.utils.MemUnitEnum;
//import org.dlut.mycloudmanage.common.utils.MemUtil;
//import org.dlut.mycloudmanage.common.utils.MyJsonUtils;
//import org.dlut.mycloudmanage.controller.common.BaseController;
//import org.dlut.mycloudserver.client.common.Pagination;
//import org.dlut.mycloudserver.client.common.usermanage.RoleEnum;
//import org.dlut.mycloudserver.client.common.usermanage.UserDTO;
//import org.dlut.mycloudserver.client.common.vmmanage.QueryVmCondition;
//import org.dlut.mycloudserver.client.common.vmmanage.ShowTypeEnum;
//import org.dlut.mycloudserver.client.common.vmmanage.VmDTO;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.alibaba.fastjson.JSONObject;
//
///**
// * 类TeacherVmController.java的实现描述：TODO 类实现描述
// * 
// * @author xuyizhen Dec 16, 2014 7:11:45 PM
// */
//@Controller
//public class TeacherTVmController extends BaseController {
//    private static Logger log = LoggerFactory.getLogger(TeacherTVmController.class);
//    @Resource(name = "vmBiz")
//    private VmBiz         vmBiz;
//    @Resource(name = "classBiz")
//    private ClassBiz      classBiz;
//
//    /**
//     * 教师-模板虚拟机-列表
//     * 
//     * @param request
//     * @param response
//     * @param model
//     * @param currentPage
//     * @return
//     */
//
//    @RequestMapping(value = UrlConstant.TEACHER_TVM_LIST)
//    public String tvmList(HttpServletRequest request, HttpServletResponse response, ModelMap model, Integer currentPage) {
//
//        String errorDesc = setDefaultEnv(request, response, model);
//        if (errorDesc != null) {
//            return goErrorPage(errorDesc);
//        }
//        int PAGESIZE = Integer.parseInt(MyPropertiesUtil.getValue("pagesize"));
//        UserDTO userDTO = (UserDTO) model.get("loginUser");
//        if (currentPage == null)
//            currentPage = 1;
//        QueryVmCondition queryVmCondition = new QueryVmCondition();
//        queryVmCondition.setUserAccount(userDTO.getAccount());
//        queryVmCondition.setLimit(PAGESIZE);
//        queryVmCondition.setOffset((currentPage - 1) * PAGESIZE);
//        queryVmCondition.setIsTemplateVm(true);
//        queryVmCondition.setIsPublicTemplate(false);//仅仅自己创建的模板虚拟机可见
//        Pagination<VmDTO> pageVmDTO = this.vmBiz.query(queryVmCondition);
//        if (pageVmDTO.getTotalPage() >= 1 && (currentPage < 1 || currentPage > pageVmDTO.getTotalPage()))
//            return this.goErrorPage("该页面不存在");
//        List<VmDTO> vmDTOList = pageVmDTO.getList();
//        List<VmVO> vmList = new ArrayList<VmVO>();
//        for (VmDTO vmDTO : vmDTOList) {
//            VmVO vmVO = new VmVO();
//            if (vmDTO.getClassId() == 0)
//                vmVO.setVmClass("--");
//            else
//                vmVO.setVmClass(this.classBiz.getClassById(vmDTO.getClassId()).getClassName());
//            vmVO.setVmName(vmDTO.getVmName());
//            vmVO.setVmUuid(vmDTO.getVmUuid());
//            vmVO.setShowType(vmDTO.getShowType());
//            vmVO.setVmVcpu(vmDTO.getVmVcpu());
//            vmVO.setVmMemory(MemUtil.getMem(vmDTO.getVmMemory(), MemUnitEnum.MB));
//            if (vmDTO.getDesc() == null)
//                vmVO.setVmDesc("--");
//            else
//                vmVO.setVmDesc(vmDTO.getDesc());
//            vmList.add(vmVO);
//        }
//        model.put("tvms", vmList);
//        model.put("currentPage", currentPage);
//        model.put("totalPage", pageVmDTO.getTotalPage());
//        this.setShowMenuList(RoleEnum.TEACHER, MenuEnum.TEACHER_TVM_LIST, model);
//        model.put("screen", "teacher/tvm_list");
//        model.put("js", "teacher/tvm_list");
//        return "default";
//    }
//
//    @RequestMapping(value = UrlConstant.TEACHER_TVM_EDIT_FORM)
//    public String editForm(HttpServletRequest request, HttpServletResponse response, ModelMap model, String vmUuid) {
//        String errorDesc = setDefaultEnv(request, response, model);
//        if (errorDesc != null) {
//            return goErrorPage(errorDesc);
//        }
//        UserDTO userDTO = (UserDTO) model.get("loginUser");
//        // 查询要修改的内容，以显示在编辑表单中
//        QueryVmCondition queryVmCondition = new QueryVmCondition();
//        queryVmCondition.setVmUuid(vmUuid);
//        VmDTO vmDTO = this.vmBiz.query(queryVmCondition).getList().get(0);
//        VmVO vm = new VmVO();
//        vm.setVmName(vmDTO.getVmName());
//        vm.setVmDesc(vmDTO.getDesc());
//        vm.setVmVcpu(vmDTO.getVmVcpu());
//        vm.setVmMemory(MemUtil.getMem(vmDTO.getVmMemory(), MemUnitEnum.MB));
//        vm.setShowType(vmDTO.getShowType());
//        vm.setVmPass(vmDTO.getShowPassword());
//        vm.setVmUuid(vmDTO.getVmUuid());
//        model.put("vm", vm);
//
//        this.setShowMenuList(RoleEnum.TEACHER, MenuEnum.TEACHER_TVM_LIST, model);
//        model.put("screen", "teacher/tvm_edit_form");
//        model.put("js", "teacher/tvm_list");
//        return "default";
//    }
//
//    @RequestMapping(value = UrlConstant.TEACHER_TVM_EDIT, produces = { "application/json;charset=UTF-8" })
//    @ResponseBody
//    public String vmEdit(HttpServletRequest request, HttpServletResponse response, ModelMap model, String vmUuid,
//                         String vmName, String showType, String vmDesc, String showPassword, String vmVcpu,
//                         String vmMemory) {
//
//        String errorDesc = setDefaultEnv(request, response, model);
//        if (errorDesc != null) {
//            return goErrorPage(errorDesc);
//        }
//        JSONObject json = new JSONObject();
//
//        // 检查vmUuid是否存在
//        QueryVmCondition queryVmCondition = new QueryVmCondition();
//        queryVmCondition.setVmUuid(vmUuid);
//        Pagination<VmDTO> page = this.vmBiz.query(queryVmCondition);
//        if (page.getTotalCount() <= 0) {
//            return MyJsonUtils.getFailJsonString(json, "要编辑的虚拟机不存在");
//        }
//
//        VmDTO vmDTO = new VmDTO();
//        vmDTO.setVmUuid(vmUuid);
//        vmDTO.setVmName(vmName);
//        vmDTO.setDesc(vmDesc);
//        vmDTO.setShowPassword(showPassword);
//        vmDTO.setVmVcpu(Integer.parseInt(vmVcpu));
//        vmDTO.setVmMemory(MemUtil.getMem(Integer.parseInt(vmMemory), MemUnitEnum.MB));
//        if (Integer.parseInt(showType) == 1)
//            vmDTO.setShowType(ShowTypeEnum.SPICE);
//        else
//            vmDTO.setShowType(ShowTypeEnum.VNC);
//        if (this.vmBiz.updateVm(vmDTO)) {
//            return MyJsonUtils.getSuccessJsonString(json, "编辑成功");
//
//        }
//        return MyJsonUtils.getFailJsonString(json, "编辑失败");
//
//    }
//
//    @RequestMapping(value = UrlConstant.TEACHER_TVM_CONVERT, produces = { "application/json;charset=UTF-8" })
//    @ResponseBody
//    public String convert(HttpServletRequest request, HttpServletResponse response, ModelMap model, String vmUuid) {
//        String errorDesc = setDefaultEnv(request, response, model);
//        if (errorDesc != null) {
//            return goErrorPage(errorDesc);
//        }
//        JSONObject json = new JSONObject();
//
//        // 检查vmUuid是否存在
//        QueryVmCondition queryVmCondition = new QueryVmCondition();
//        queryVmCondition.setVmUuid(vmUuid);
//        Pagination<VmDTO> page = this.vmBiz.query(queryVmCondition);
//        if (page.getTotalCount() <= 0) {
//            return MyJsonUtils.getFailJsonString(json, "要操作的虚拟机不存在");
//        }
//        /***** 以下理论上应该使用事务 ****/
//        //模板虚拟机转换为私有虚拟机
//        //必须先处理tvm_class表,因为该表只允许删除与模板虚拟机有关的关联
//        this.classBiz.deleteAllClassWithTemplateVm(vmUuid);
//        if (!this.vmBiz.changeToNonTempalteVm(vmUuid)) {
//            return MyJsonUtils.getFailJsonString(json, "转换失败");
//        }
//
//        /***** 以上理论上应该使用事务 ****/
//
//        return MyJsonUtils.getSuccessJsonString(json, "转换成功");
//
//    }
//
//}
