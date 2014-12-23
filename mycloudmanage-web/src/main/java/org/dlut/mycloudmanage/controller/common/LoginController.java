package org.dlut.mycloudmanage.controller.common;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.dlut.mycloudmanage.biz.UserBiz;
import org.dlut.mycloudmanage.common.constant.SessionConstant;
import org.dlut.mycloudmanage.common.constant.UrlConstant;
import org.dlut.mycloudmanage.common.obj.LoginReqDTO;
import org.dlut.mycloudserver.client.common.usermanage.RoleEnum;
import org.dlut.mycloudserver.client.common.usermanage.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 类LoginController.java的实现描述：TODO 类实现描述
 * 
 * @author xuyizhen 2014年12月23日 下午10:15:25
 */
@Controller
public class LoginController extends BaseController {

    private static Logger log = LoggerFactory.getLogger(LoginController.class);

    @Resource(name = "userBiz")
    private UserBiz       userBiz;

    @RequestMapping(value = UrlConstant.LOGIN_URL, method = { RequestMethod.POST, RequestMethod.GET })
    public String login(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                        LoginReqDTO loginReqDTO, String redirect) {
        //表单默认post方式，get方式就重新登陆
        if (request.getMethod().equals("GET")) {
            return UrlConstant.LOGIN_URL;
        }
        model.put("loginReqDTO", loginReqDTO);//留作错误返回时的重填
        String errorDesc = check(loginReqDTO);
        if (!StringUtils.isBlank(errorDesc)) {
            model.put("errorDesc", errorDesc);
            return UrlConstant.LOGIN_URL;
        }
        UserDTO userDTO = this.userBiz.verifyAndGetUser(loginReqDTO.getAccount(), loginReqDTO.getPassword(),
                RoleEnum.getRoleByStatus(loginReqDTO.getRole()));
        if (userDTO == null) {
            model.put("errorDesc", "用户名或密码错误");
            return UrlConstant.LOGIN_URL;
        }
        //用户登陆成功，设置用户的session
        request.getSession(true).setAttribute(SessionConstant.USER_ACCOUNT, userDTO.getAccount());
        //如果用户没有请求具体的页面，重定向到默认页面
        if (StringUtils.isBlank(redirect)) {
            return goDefaultPage(userDTO.getRole());
        }
        return "redirect:" + redirect;
    }

    /**
     * 验证表单是否合法
     * 
     * @param loginReqDTO
     * @return
     */
    private String check(LoginReqDTO loginReqDTO) {
        String errorDesc = null;
        if (loginReqDTO == null) {
            errorDesc = "表单不能为空";
            return errorDesc;
        }
        if (StringUtils.isBlank(loginReqDTO.getAccount())) {
            errorDesc = "账号不能为空";
            return errorDesc;
        }
        if (StringUtils.isBlank(loginReqDTO.getPassword())) {
            errorDesc = "密码不能为空";
            return errorDesc;
        }
        RoleEnum role = RoleEnum.getRoleByStatus(loginReqDTO.getRole());
        if (role == null) {
            errorDesc = "请选择登陆的角色";
            return errorDesc;
        }
        return errorDesc;
    }

    /**
     * 返回一个角色的默认页的重定向地址
     * 
     * @param role
     * @return
     */
    private String goDefaultPage(RoleEnum role) {
        StringBuilder page = new StringBuilder("redirect:");
        if (role == RoleEnum.ADMIN) {
            page.append(UrlConstant.ADMIN_DEFAULT_URL);
        } else if (role == RoleEnum.STUDENT) {
            page.append(UrlConstant.STUDENT_DEFAULT_URL);
        } else if (role == RoleEnum.TEACHER) {
            page.append(UrlConstant.TEACHER_DEFAULT_URL);
        }
        return page.toString();
    }
}
