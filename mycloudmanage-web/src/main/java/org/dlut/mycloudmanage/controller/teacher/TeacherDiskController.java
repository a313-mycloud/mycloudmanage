/*
 * Copyright 2014 etao.com All right reserved. This software is the
 * confidential and proprietary information of etao.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with etao.com .
 */
package org.dlut.mycloudmanage.controller.teacher;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.dlut.mycloudmanage.biz.DiskBiz;
import org.dlut.mycloudmanage.biz.VmBiz;
import org.dlut.mycloudmanage.common.constant.MenuEnum;
import org.dlut.mycloudmanage.common.constant.UrlConstant;
import org.dlut.mycloudmanage.common.obj.DiskVO;
import org.dlut.mycloudmanage.common.obj.VmVO;
import org.dlut.mycloudmanage.common.property.utils.MyPropertiesUtil;
import org.dlut.mycloudmanage.common.utils.MemUnitEnum;
import org.dlut.mycloudmanage.common.utils.MemUtil;
import org.dlut.mycloudmanage.common.utils.MyJsonUtils;
import org.dlut.mycloudmanage.common.utils.MyStringUtils;
import org.dlut.mycloudmanage.controller.common.BaseVmController;
import org.dlut.mycloudserver.client.common.Pagination;
import org.dlut.mycloudserver.client.common.storemanage.DiskDTO;
import org.dlut.mycloudserver.client.common.storemanage.QueryDiskCondition;
import org.dlut.mycloudserver.client.common.usermanage.RoleEnum;
import org.dlut.mycloudserver.client.common.usermanage.UserDTO;
import org.dlut.mycloudserver.client.common.vmmanage.QueryVmCondition;
import org.dlut.mycloudserver.client.common.vmmanage.VmDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class TeacherDiskController extends BaseVmController {
    private static Logger log = LoggerFactory.getLogger(TeacherDiskController.class);
    @Resource(name = "diskBiz")
    private DiskBiz       diskBiz;

    @Resource(name = "vmBiz")
    private VmBiz         vmBiz;

    /**
     * 教师-虚拟硬盘-显示列表
     * 
     * @param request
     * @param response
     * @param model
     * @param currentPage
     * @return
     */
    @RequestMapping(value = UrlConstant.TEACHER_DISK_LIST)
    public String diskList(HttpServletRequest request, HttpServletResponse response, ModelMap model, Integer currentPage) {

        String errorDesc = this.setDefaultEnv(request, response, model);
        if (errorDesc != null) {
            return this.goErrorPage(errorDesc);
        }
        //获取当前用户帐号
        UserDTO userDTO = (UserDTO) model.get("loginUser");
        //默认显示第一页
        if (currentPage == null)
            currentPage = 1;
        int PAGESIZE = Integer.parseInt(MyPropertiesUtil.getValue("pagesize"));
        QueryDiskCondition queryDiskCondition = new QueryDiskCondition();//不同的页面对应不同的查询类
        queryDiskCondition.setLimit(PAGESIZE);
        queryDiskCondition.setOffset((currentPage - 1) * PAGESIZE);
        queryDiskCondition.setUserAccount(userDTO.getAccount());

        Pagination<DiskDTO> pageDiskDTO = this.diskBiz.query(queryDiskCondition);
        if (pageDiskDTO == null)
            return this.goErrorPage("调用 this.diskBiz.query()出错");
        if (pageDiskDTO.getTotalPage() >= 1 && (currentPage < 1 || currentPage > pageDiskDTO.getTotalPage()))
            return this.goErrorPage("该页面不存在");

        List<DiskVO> disks = new ArrayList<DiskVO>();

        for (DiskDTO diskDTO : pageDiskDTO.getList()) {
            DiskVO diskVO = new DiskVO();
            String attachVmUuid = diskDTO.getAttachVmUuid();

            if (StringUtils.isBlank(attachVmUuid)) {
                diskVO.setVmName("--");
            } else {
                String vmName = this.vmBiz.getVmByUuid(attachVmUuid).getVmName();
                diskVO.setVmName(vmName);
            }

            diskVO.setDiskFormat(diskDTO.getDiskFormat());
            diskVO.setDiskName(diskDTO.getDiskName());
            diskVO.setDiskPath(diskDTO.getDiskPath());
            diskVO.setDiskTotalSize(MemUtil.getMem(diskDTO.getDiskTotalSize(), MemUnitEnum.MB));
            diskVO.setDiskUsedSize(MemUtil.getMem(diskDTO.getDiskUsedSize(), MemUnitEnum.MB));
            diskVO.setDiskUuid(diskDTO.getDiskUuid());
            String diskDesc = diskDTO.getDesc();
            if (StringUtils.isBlank(attachVmUuid))
                diskVO.setDiskDesc("-");
            else
                diskVO.setDiskDesc(diskDesc);
            disks.add(diskVO);
        }
        model.put("disks", disks);//不同的查询页面对应不同的查询列表
        model.put("totalPage", pageDiskDTO.getTotalPage());//不同的查询页面对应不同的总页数
        model.put("currentPage", currentPage);
        this.setShowMenuList(RoleEnum.TEACHER, MenuEnum.TEACHER_DISK_LIST, model);
        model.put("screen", "teacher/disk_list");
        model.put("js", "teacher/disk_list");
        return "default";
    }

    /**
     * 教师-虚拟硬盘-删除 处理异步请求，返回JSON
     */
    @RequestMapping(value = UrlConstant.TEACHER_DISK_REMOVE, produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public String removeDisk(String diskUuid) {

        JSONObject json = new JSONObject();
        if (this.diskBiz.getDiskByUuid(diskUuid) == null) {
            return MyJsonUtils.getFailJsonString(json, "要删除的虚拟硬盘不存在");
        }
        if (this.diskBiz.deleteDiskByUuid(diskUuid)) {
            log.info("删除" + diskUuid + "成功");
            return MyJsonUtils.getSuccessJsonString(json, "删除成功");
        }
        log.info("删除" + diskUuid + "失败");
        return MyJsonUtils.getFailJsonString(json, "删除失败");
    }

    /**
     * @param request
     * @param response
     * @param model
     * @param diskUuid
     * @return
     */
    @RequestMapping(value = UrlConstant.TEACHER_DISK_ATTACH_FORM)
    public String attachForm(HttpServletRequest request, HttpServletResponse response, ModelMap model, String diskUuid) {
        String errorDesc = this.setDefaultEnv(request, response, model);
        if (errorDesc != null) {
            return this.goErrorPage(errorDesc);
        }
        //获取当前用户帐号
        UserDTO userDTO = (UserDTO) model.get("loginUser");
        if (this.diskBiz.getDiskByUuid(diskUuid) == null) {
            return this.goErrorPage("要挂载的虚拟硬盘不存在");
        }
        QueryVmCondition queryVmCondition = new QueryVmCondition();
        queryVmCondition.setOffset(0);
        queryVmCondition.setLimit(1000);
        queryVmCondition.setUserAccount(userDTO.getAccount());
        queryVmCondition.setIsTemplateVm(false);

        List<VmVO> vms = new ArrayList<VmVO>();
        List<VmDTO> vmDTOs = this.vmBiz.query(queryVmCondition).getList();
        if (vmDTOs.size() <= 0) {
            return this.goErrorPage("您的账户当前没有可以挂载的虚拟机");
        }
        for (VmDTO vmDTO : vmDTOs) {
            VmVO vmVO = new VmVO();
            vmVO.setVmName(vmDTO.getVmName());
            vmVO.setVmDesc(vmDTO.getDesc());
            vmVO.setVmUuid(vmDTO.getVmUuid());
            vms.add(vmVO);
        }
        model.put("vms", vms);
        model.put("diskDTO", this.diskBiz.getDiskByUuid(diskUuid));
        this.setShowMenuList(RoleEnum.TEACHER, MenuEnum.TEACHER_DISK_LIST, model);
        model.put("screen", "teacher/disk_attach_form");
        model.put("js", "teacher/disk_list");
        return "default";

    }

    /**
     * @param request
     * @param response
     * @param model
     * @param diskUuid
     * @param vmUuid
     * @return
     */
    @RequestMapping(value = UrlConstant.TEACHER_DISK_ATTACH, produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public String attach(HttpServletRequest request, HttpServletResponse response, ModelMap model, String diskUuid,
                         String vmUuid) {
        String errorDesc = this.setDefaultEnv(request, response, model);
        if (errorDesc != null) {
            return this.goErrorPage(errorDesc);
        }
        //获取当前用户帐号
        UserDTO userDTO = (UserDTO) model.get("loginUser");
        JSONObject json = new JSONObject();
        if (this.diskBiz.getDiskByUuid(diskUuid) == null) {
            return MyJsonUtils.getFailJsonString(json, "要操作的虚拟硬盘不存在");
        }
        if (this.vmBiz.getVmByUuid(vmUuid) == null) {
            return MyJsonUtils.getFailJsonString(json, "要操作的虚拟机不存在");
        }
        if (!this.vmBiz.attachDisk(vmUuid, diskUuid))
            return MyJsonUtils.getFailJsonString(json, "挂载失败");
        return MyJsonUtils.getSuccessJsonString(json, "操作成功");
    }

    @RequestMapping(value = UrlConstant.TEACHER_DISK_UNLOAD, produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public String unload(HttpServletRequest request, HttpServletResponse response, ModelMap model, String diskUuid) {

        JSONObject json = new JSONObject();
        if (this.diskBiz.getDiskByUuid(diskUuid) == null) {
            return MyJsonUtils.getFailJsonString(json, "要操作的虚拟硬盘不存在");
        }
        if (!this.vmBiz.detachDisk(diskUuid))
            return MyJsonUtils.getFailJsonString(json, "卸载失败");
        return MyJsonUtils.getSuccessJsonString(json, "操作成功");
    }

    @RequestMapping(value = UrlConstant.TEACHER_DISK_ADD_FORM)
    public String addForm(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        String errorDesc = this.setDefaultEnv(request, response, model);
        if (errorDesc != null) {
            return this.goErrorPage(errorDesc);
        }
        //获取当前用户帐号
        UserDTO userDTO = (UserDTO) model.get("loginUser");

        this.setShowMenuList(RoleEnum.TEACHER, MenuEnum.TEACHER_DISK_LIST, model);
        model.put("screen", "teacher/disk_add_form");
        model.put("js", "teacher/disk_list");
        return "default";

    }

    @RequestMapping(value = UrlConstant.TEACHER_DISK_ADD, produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public String add(HttpServletRequest request, HttpServletResponse response, ModelMap model, String diskName,
                      String diskSize, String dizkDesc) {
        String errorDesc = this.setDefaultEnv(request, response, model);
        if (errorDesc != null) {
            return this.goErrorPage(errorDesc);
        }
        //获取当前用户帐号
        UserDTO userDTO = (UserDTO) model.get("loginUser");

        JSONObject json = new JSONObject();
        if (StringUtils.isBlank(diskName))
            return MyJsonUtils.getFailJsonString(json, "硬盘名不能为空");
        if (StringUtils.isBlank(diskSize))
            return MyJsonUtils.getFailJsonString(json, "硬盘大小不能为空");
        if (!MyStringUtils.isInteger(diskSize))
            return MyJsonUtils.getFailJsonString(json, "硬盘大小必须为数字");
        DiskDTO diskDTO = new DiskDTO();
        if (StringUtils.isBlank(dizkDesc))
            diskDTO.setDesc("");
        else
            diskDTO.setDesc(dizkDesc);
        diskDTO.setDiskTotalSize(MemUtil.getMem(Integer.parseInt(diskSize), MemUnitEnum.MB));
        diskDTO.setDiskName(diskName);
        diskDTO.setUserAccount(userDTO.getAccount());
        if (this.diskBiz.createDisk(diskDTO) != null)
            return MyJsonUtils.getSuccessJsonString(json, "");
        return MyJsonUtils.getFailJsonString(json, "创建失败");
    }
}
