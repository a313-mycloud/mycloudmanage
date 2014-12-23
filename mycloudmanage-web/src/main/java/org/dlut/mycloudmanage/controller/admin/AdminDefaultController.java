package org.dlut.mycloudmanage.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dlut.mycloudmanage.common.constant.UrlConstant;
import org.dlut.mycloudmanage.controller.common.BaseDefaultController;
import org.dlut.mycloudserver.client.common.usermanage.RoleEnum;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 类StudentVMController.java的实现描述：TODO 类实现描述
 * 
 * @author xuyizhen Dec 16, 2014 7:10:53 PM
 */
@Controller
public class AdminDefaultController extends BaseDefaultController {

    @RequestMapping(value = UrlConstant.ADMIN_DEFAULT_URL)
    public String adminDefaultPage(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        return this.defaultPage(request, response, model, RoleEnum.ADMIN);
    }
}
