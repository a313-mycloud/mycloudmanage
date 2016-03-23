/*
 * Copyright 2014 etao.com All right reserved. This software is the
 * confidential and proprietary information of etao.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with etao.com .
 */
package org.dlut.mycloudmanage.controller.admin;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.dlut.mycloudmanage.biz.VmBiz;
import org.dlut.mycloudmanage.common.constant.MenuEnum;
import org.dlut.mycloudmanage.common.constant.UrlConstant;
import org.dlut.mycloudmanage.common.property.utils.MyPropertiesUtil;
import org.dlut.mycloudmanage.common.utils.MemUnitEnum;
import org.dlut.mycloudmanage.common.utils.MemUtil;
import org.dlut.mycloudmanage.common.utils.MyJsonUtils;
import org.dlut.mycloudmanage.common.utils.MyStringUtils;
import org.dlut.mycloudmanage.controller.common.BaseController;
import org.dlut.mycloudserver.client.common.Pagination;
import org.dlut.mycloudserver.client.common.storemanage.StoreFormat;
import org.dlut.mycloudserver.client.common.usermanage.RoleEnum;
import org.dlut.mycloudserver.client.common.usermanage.UserDTO;
import org.dlut.mycloudserver.client.common.vmmanage.InterfaceTypeEnum;
import org.dlut.mycloudserver.client.common.vmmanage.MasterDiskBusTypeEnum;
import org.dlut.mycloudserver.client.common.vmmanage.NetworkTypeEnum;
import org.dlut.mycloudserver.client.common.vmmanage.QueryVmCondition;
import org.dlut.mycloudserver.client.common.vmmanage.ShowTypeEnum;
import org.dlut.mycloudserver.client.common.vmmanage.SystemTypeEnum;
import org.dlut.mycloudserver.client.common.vmmanage.VmDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

/**
 * 类HostController.java的实现描述：TODO 类实现描述
 * 
 * @author xuyizhen Dec 7, 2014 10:19:01 AM
 */
@Controller
public class ImageController extends BaseController {

    private static Logger log = LoggerFactory.getLogger(ImageController.class);

    @Resource(name = "vmBiz")
    private VmBiz         vmBiz;

    /**
     * 管理员-镜像-显示列表
     * 
     * @param request
     * @param response
     * @param model
     * @param currentPage
     * @param perPage
     * @return
     */

    @RequestMapping(value = UrlConstant.ADMIN_IMAGE_LIST)
    public String imageList(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                            Integer currentPage) {

        String errorDesc = this.setDefaultEnv(request, response, model);
        if (errorDesc != null) {
            return this.goErrorPage(errorDesc);
        }
        if (currentPage == null)
            currentPage = 1;
        int PAGESIZE = Integer.parseInt(MyPropertiesUtil.getValue("pagesize"));
        QueryVmCondition queryVmCondition = new QueryVmCondition();
        queryVmCondition.setLimit(PAGESIZE);
        queryVmCondition.setOffset((currentPage - 1) * PAGESIZE);
        queryVmCondition.setIsTemplateVm(true);
        queryVmCondition.setIsPublicTemplate(true);
        Pagination<VmDTO> pageVmDTO = this.vmBiz.query(queryVmCondition);
        if (pageVmDTO.getTotalPage() >= 1 && (currentPage < 1 || currentPage > pageVmDTO.getTotalPage()))
            return this.goErrorPage("该页面不存在");
        List<VmDTO> vmList = pageVmDTO.getList();
        model.put("imageList", vmList);
        model.put("currentPage", currentPage);
        model.put("totalPage", pageVmDTO.getTotalPage());

        this.setShowMenuList(RoleEnum.ADMIN, MenuEnum.ADMIN_IMAGE_LIST, model);
        model.put("screen", "admin/image_list");
        model.put("js", "admin/image_list");
        return "default";
    }

    /**
     * 管理员-镜像-删除 处理异步请求，返回JSON
     * 
     * @param hostId
     * @return
     */
    @RequestMapping(value = UrlConstant.ADMIN_IMAGE_REMOVE, produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public String removeImage(String vmUuid) {

        JSONObject json = new JSONObject();
        if (StringUtils.isBlank(vmUuid))
            return MyJsonUtils.getFailJsonString(json, "vmUuid不能为空");
        QueryVmCondition queryVmCondition = new QueryVmCondition();
        queryVmCondition.setVmUuid(vmUuid);
        if (this.vmBiz.countQuery(queryVmCondition) <= 0)
            return MyJsonUtils.getFailJsonString(json, "该镜像不存在");
        if (!this.vmBiz.deleteVm(vmUuid)) {
            log.error("删除镜像" + vmUuid + "失败");
            return MyJsonUtils.getFailJsonString(json, "删除镜像失败");
        }
        log.info("删除" + vmUuid + "成功");
        return MyJsonUtils.getSuccessJsonString(json, "");
    }

    //    @RequestMapping(value = UrlConstant.ADMIN_IMAGE_ADD_FORM)
    @RequestMapping(value = UrlConstant.ADMIN_IMAGE_ADD_FORM)
    public String addImageForm(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        String errorDesc = this.setDefaultEnv(request, response, model);
        if (errorDesc != null) {
            return this.goErrorPage(errorDesc);
        }

        this.setShowMenuList(RoleEnum.ADMIN, MenuEnum.ADMIN_IMAGE_LIST, model);
        model.put("screen", "admin/image_add_form");
        model.put("js", "admin/image_list");
        return "default";
    }

    /**
     * 添加已经上传的文件为模板
     * 
     * @param request
     * @param response
     * @param model
     * @param fileName
     * @return
     */
    @RequestMapping(value = UrlConstant.ADMIN_IMAGE_ADD, produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public String addImage(HttpServletRequest request, HttpServletResponse response, ModelMap model, String fileName,
                           String vmVcpu, String vmMemory, String vmSize, String diskBusType, String interfaceType,
                           String systemType) {
        String errorDesc = this.setDefaultEnv(request, response, model);
        if (errorDesc != null) {
            return this.goErrorPage(errorDesc);
        }
        //获取当前用户帐号
        UserDTO userDTO = (UserDTO) model.get("loginUser");

        JSONObject json = new JSONObject();

        if (StringUtils.isBlank(fileName)) {
            return MyJsonUtils.getFailJsonString(json, "虚拟机名字不能为空");
        }
        if (!MyStringUtils.isInteger(vmVcpu)) {
            return MyJsonUtils.getFailJsonString(json, "核心数需要填写一个数字");
        }
        if (!MyStringUtils.isInteger(vmMemory)) {
            return MyJsonUtils.getFailJsonString(json, "内存需要填写一个数字");
        }
        if (!MyStringUtils.isLong(vmSize)) {
            return MyJsonUtils.getFailJsonString(json, "vmSize需要填写一个数字");
        }
        if (!MyStringUtils.isInteger(diskBusType))
            return MyJsonUtils.getFailJsonString(json, "总线类型格式不正确");
        if (!MyStringUtils.isInteger(interfaceType))
            return MyJsonUtils.getFailJsonString(json, "接口类型格式不正确");
        if (!MyStringUtils.isInteger(systemType))
            return MyJsonUtils.getFailJsonString(json, "系统类型格式不正确");

        InterfaceTypeEnum interfaceTypeEnum = InterfaceTypeEnum
                .getInterfaceTypeByValue(Integer.parseInt(interfaceType));
        MasterDiskBusTypeEnum masterDiskBusTypeEnum = MasterDiskBusTypeEnum.getMasterDiskBusTypeByValue(Integer
                .parseInt(diskBusType));
        SystemTypeEnum systemTypeEnum = SystemTypeEnum.getSystemTypeByValue(Integer.parseInt(systemType));
        if (interfaceTypeEnum == null)
            return MyJsonUtils.getFailJsonString(json, "接口类型不存在");
        if (masterDiskBusTypeEnum == null)
            return MyJsonUtils.getFailJsonString(json, "总线类型不存在");
        if (systemTypeEnum == null)
            return MyJsonUtils.getFailJsonString(json, "系统类型不存在");
        //将文件重命名，并且从upload移动到every host machine's images中
        String fileUuid = UUID.randomUUID().toString();
        //        File toFile = new File(MyPropertiesUtil.getValue("imageDir") + fileUuid);
        //        if (!file.renameTo(toFile)) {
        //            log.error("镜像" + fileName + "移动到images失败");
        //            return MyJsonUtils.getFailJsonString(json, "添加镜像文件失败");
        //        }
        VmDTO vmDTO = new VmDTO();
        vmDTO.setDesc("原始");
        vmDTO.setImageFormat(StoreFormat.QCOW2);
        vmDTO.setImageTotalSize(Long.parseLong(vmSize));
        vmDTO.setImageUuid(fileUuid);
        vmDTO.setIsPublicTemplate(true);
        vmDTO.setIsTemplateVm(true);
        vmDTO.setShowPassword(MyPropertiesUtil.getValue("initialPassword"));
        vmDTO.setUserAccount(userDTO.getAccount());
        vmDTO.setVmName(fileName);
        vmDTO.setVmMemory(MemUtil.getMem(Integer.parseInt(vmMemory), MemUnitEnum.MB));
        vmDTO.setVmVcpu(Integer.parseInt(vmVcpu));
        vmDTO.setClassId(0);
        vmDTO.setParentVmUuid("");
        vmDTO.setShowType(ShowTypeEnum.SPICE);
        vmDTO.setVmNetworkType(NetworkTypeEnum.BRIDGE);
        vmDTO.setInterfaceType(interfaceTypeEnum);
        vmDTO.setMasterDiskBusType(masterDiskBusTypeEnum);
        vmDTO.setSystemType(systemTypeEnum);
        if (StringUtils.isBlank(this.vmBiz.createImage(vmDTO))) {
            log.error("添加镜像文件失败");
            return MyJsonUtils.getFailJsonString(json, "添加镜像文件失败");
        }
        log.info("添加镜像文件成功");
        return MyJsonUtils.getSuccessJsonString(json, "添加镜像成功");
    }

}
