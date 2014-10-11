package org.dlut.mycloudmanage.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.dlut.mycloudmanage.common.UrlUtil;
import org.dlut.mycloudmanage.common.constant.SessionConstant;
import org.dlut.mycloudmanage.common.constant.UrlConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 拦截未登陆用户
 * 
 * @author luojie 2014年9月21日 下午10:31:19
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

    private static Logger log = LoggerFactory.getLogger(LoginInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String url = request.getRequestURI();
        log.info("MyInterceptor.preHandle()" + url);

        String userAccount = (String) request.getSession().getAttribute(SessionConstant.USER_ACCOUNT);
        if (StringUtils.isBlank(userAccount)) {
            response.sendRedirect(UrlConstant.LOGIN_URL + "?redirect=" + UrlUtil.getCurUrl(request));
            return false;
        }

        return super.preHandle(request, response, handler);
    }
}
