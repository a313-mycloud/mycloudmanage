package org.dlut.mycloudmanage.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.dlut.mycloudmanage.common.UrlUtil;
import org.dlut.mycloudmanage.common.constant.SessionConstant;
import org.dlut.mycloudmanage.common.constant.UrlConstant;
import org.dlut.mycloudserver.client.service.usermanage.IUserManageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSONObject;

/**
 * 拦截器类，检验是否登陆和是否有权限 对于客户端请求，错误时调到errorPage 对于ajax请求，返回json
 * 
 * 类LoginInterceptor.java的实现描述：TODO 类实现描述
 * 
 * @author xuyizhen Dec 10, 2014 7:32:22 PM
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

	private static Logger log = LoggerFactory.getLogger(LoginInterceptor.class);

	@Resource(name = "userManageService")
	private IUserManageService userManageService;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		String uri = request.getRequestURI();
		// 得到用户帐号
		String userAccount = (String) request.getSession().getAttribute(
				SessionConstant.USER_ACCOUNT);

		// 如果ajax跳转
		if (uri.endsWith(".do") || uri.endsWith(".do/")) {
			JSONObject json = new JSONObject();
			if (StringUtils.isBlank(userAccount)) {
				json.put("isLogin", false);
				response.getWriter().write(json.toString());
				return false;
			}
			if (userManageService.getUserByAccount(userAccount).getModel()
					.getRole().getStatus() != getApplyAuth(uri)) {
				json.put("isLogin", true);
				json.put("isAuth", false);
				response.getWriter().write(json.toString());
				return false;
			}
		} else {// 客户端跳转
			if (StringUtils.isBlank(userAccount)) {
				response.sendRedirect(UrlConstant.LOGIN_URL + "?redirect="
						+ UrlUtil.getCurUrl(request));
				return false;
			}
			if (userManageService.getUserByAccount(userAccount).getModel()
					.getRole().getStatus() != getApplyAuth(uri)) {
				response.sendRedirect(UrlConstant.ERROR_URL
						+ "?errorDesc=您没有权限");
				return false;
			}
		}
		return super.preHandle(request, response, handler);
	}

	// 根据才uri返回申请的内容属于v的权限
	private int getApplyAuth(String uri) {
		int endIndex = uri.indexOf("/", 1);
		String applyAuth = uri.substring(1, endIndex);
		int applyAuthStatus = 1;
		if (applyAuth.equals("teacher"))
			applyAuthStatus = 2;
		else if (applyAuth.equals("admin"))
			applyAuthStatus = 3;
		return applyAuthStatus;
	}
}
