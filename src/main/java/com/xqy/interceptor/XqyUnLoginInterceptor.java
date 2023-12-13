package com.xqy.interceptor;

import com.xqy.exceptions.XqyUnLoginException;
import com.xqy.service.XqyUserService;
import com.xqy.utils.XqyLoginUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author xu
 * @Date 2023/9/30 13:12
 * @Version 1.0
 * 登录状态拦截器
 */
public class XqyUnLoginInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    XqyUserService userService;

    public XqyUnLoginInterceptor() {
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Integer userId = XqyLoginUserUtil.releaseUserIdFromCookie(request);
        if (userId != 0 && this.userService.getById(userId) != null) {
            return super.preHandle(request, response, handler);
        } else {
            throw new XqyUnLoginException();
        }
    }
}
