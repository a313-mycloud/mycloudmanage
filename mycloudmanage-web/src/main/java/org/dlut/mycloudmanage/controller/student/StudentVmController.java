package org.dlut.mycloudmanage.controller.student;

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

/**
 * 类StudentVMController.java的实现描述：TODO 类实现描述
 * 
 * @author xuyizhen Dec 16, 2014 7:10:53 PM
 */
@Controller
public class StudentVmController extends BaseVmController {
    /**
     * 学生-虚拟机-显示列表
     * 
     * @param request
     * @param response
     * @param model
     * @param currentPage
     * @return
     */
    @Override
    @RequestMapping(value = UrlConstant.STUDENT_VM_LIST)
    public String vmList(HttpServletRequest request, HttpServletResponse response, ModelMap model, Integer currentPage) {

        String result = super.vmList(request, response, model, currentPage);
        if (!result.equals("default")) {
            return result;
        }

        this.setShowMenuList(RoleEnum.STUDENT, MenuEnum.STUDENT_VM_LIST, model);
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
    @Override
    @RequestMapping(value = UrlConstant.STUDENT_VM_EDIT_FORM)
    public String editForm(HttpServletRequest request, HttpServletResponse response, ModelMap model, String vmUuid) {
        String result = super.editForm(request, response, model, vmUuid);
        if (!result.equals("default"))
            return result;
        this.setShowMenuList(RoleEnum.STUDENT, MenuEnum.STUDENT_VM_LIST, model);
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

    @Override
    @RequestMapping(value = UrlConstant.STUDENT_VM_EDIT, produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public String vmEdit(HttpServletRequest request, HttpServletResponse response, ModelMap model, String vmUuid,
                         String vmName, String showType, String vmDesc, String showPassword, String vmVcpu,
                         String vmMemory, String vmNetworkType) {

        return super.vmEdit(request, response, model, vmUuid, vmName, showType, vmDesc, showPassword, vmVcpu, vmMemory,
                vmNetworkType);
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
    @Override
    @RequestMapping(value = UrlConstant.STUDENT_VM_START, produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public String vmStart(HttpServletRequest request, HttpServletResponse response, ModelMap model, String vmUuid) {
        return super.vmStart(request, response, model, vmUuid);
    }

    /**
     * 学生-虚拟机-关闭
     * 
     * @param request
     * @param response
     * @param model
     * @param vmUuid
     * @return
     */
    @Override
    @RequestMapping(value = UrlConstant.STUDENT_VM_SHUTDOWN, produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public String vmShutDown(HttpServletRequest request, HttpServletResponse response, ModelMap model, String vmUuid) {
        return super.vmShutDown(request, response, model, vmUuid);
    }

}
