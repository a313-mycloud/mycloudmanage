/*
 * Copyright 2014 etao.com All right reserved. This software is the
 * confidential and proprietary information of etao.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with etao.com .
 */
package org.dlut.mycloudmanage.controller.admin;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.dlut.mycloudmanage.biz.VmBiz;
import org.dlut.mycloudmanage.common.constant.MenuEnum;
import org.dlut.mycloudmanage.common.constant.UrlConstant;
import org.dlut.mycloudmanage.common.property.utils.MyPropertiesUtil;
import org.dlut.mycloudmanage.common.utils.MemUnitEnum;
import org.dlut.mycloudmanage.common.utils.MemUtil;
import org.dlut.mycloudmanage.common.utils.MyJsonUtils;
import org.dlut.mycloudmanage.controller.common.BaseController;
import org.dlut.mycloudserver.client.common.Pagination;
import org.dlut.mycloudserver.client.common.storemanage.StoreFormat;
import org.dlut.mycloudserver.client.common.usermanage.RoleEnum;
import org.dlut.mycloudserver.client.common.usermanage.UserDTO;
import org.dlut.mycloudserver.client.common.vmmanage.NetworkTypeEnum;
import org.dlut.mycloudserver.client.common.vmmanage.QueryVmCondition;
import org.dlut.mycloudserver.client.common.vmmanage.ShowTypeEnum;
import org.dlut.mycloudserver.client.common.vmmanage.VmDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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

    /**
     * 响应客户端请求，跳转到添加表单
     * 
     * @param request
     * @param response
     * @param model
     * @return
     */
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

    @RequestMapping(value = UrlConstant.ADMIN_IMAGE_ADD, method = RequestMethod.POST)
    public String addImage(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                           MultipartFile imageFile) {
        String errorDesc = this.setDefaultEnv(request, response, model);
        if (errorDesc != null) {
            return this.goErrorPage(errorDesc);
        }
        //获取当前用户帐号
        UserDTO userDTO = (UserDTO) model.get("loginUser");

        //如果只是上传一个文件，则只需要MultipartFile类型接收文件即可，而且无需显式指定@RequestParam注解
        //如果想上传多个文件，那么这里就要用MultipartFile[]类型来接收文件，并且还要指定@RequestParam注解
        //并且上传多个文件时，前台表单中的所有<input type="file"/>的name都应该是myfiles，否则参数里的myfiles无法获取到所有上传的文件
        //需要在pom.xml中配置apache-common
        if (imageFile.isEmpty()) {
            log.error("上传文件为空");
            return this.goErrorPage("上传文件为空");
        }
        //判断文件类型是否符合要求
        log.info("上传文件长度: " + imageFile.getSize());
        log.info("上传文件类型: " + imageFile.getContentType());
        log.info("上传文件原名: " + imageFile.getOriginalFilename());
        String uploadFilePath = MyPropertiesUtil.getValue("uploadDir");
        String uploadFileName = UUID.randomUUID().toString();
        try {
            //这里不必处理IO流关闭的问题，因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉，我是看它的源码才知道的
            FileUtils.copyInputStreamToFile(imageFile.getInputStream(), new File(uploadFilePath, uploadFileName));
        } catch (IOException e) {
            log.error(e.getMessage());
            return this.goErrorPage("上传镜像文件失败");
        }
        log.info("上传文件" + uploadFileName + "到" + uploadFilePath + "成功");
        StoreFormat uploadFileFormat = this.vmBiz.getImageFormat(uploadFilePath + uploadFileName);
        if (uploadFileFormat == null) {
            log.error("上传的文件格式不正确");
            return this.goErrorPage("上传的文件格式不正确");
        }
        log.info(uploadFileFormat.getDesc());

        File fromFileDir = new File(uploadFilePath + uploadFileName);
        File toFileDir = new File(MyPropertiesUtil.getValue("imageDir") + uploadFileName);
        if (!fromFileDir.renameTo(toFileDir)) {
            log.error("镜像" + uploadFileName + "移动到images失败");
            return this.goErrorPage("上传镜像文件失败");
        }
        VmDTO vmDTO = new VmDTO();
        vmDTO.setDesc("原始");
        vmDTO.setImageFormat(uploadFileFormat);
        vmDTO.setImageTotalSize(imageFile.getSize());
        vmDTO.setImageUuid(uploadFileName);
        vmDTO.setIsPublicTemplate(true);
        vmDTO.setIsTemplateVm(true);
        vmDTO.setShowPassword(MyPropertiesUtil.getValue("initialPassword"));
        vmDTO.setUserAccount(userDTO.getAccount());
        vmDTO.setVmName(imageFile.getOriginalFilename());
        vmDTO.setVmMemory(MemUtil.getMem(2048, MemUnitEnum.MB));
        vmDTO.setVmVcpu(2);
        vmDTO.setClassId(0);
        vmDTO.setParentVmUuid("");
        vmDTO.setShowType(ShowTypeEnum.SPICE);
        vmDTO.setVmNetworkType(NetworkTypeEnum.NAT);
        if (StringUtils.isBlank(this.vmBiz.createVm(vmDTO)))
            return this.goErrorPage("上传镜像文件失败");
        return this.imageList(request, response, model, 1);
    }
}
