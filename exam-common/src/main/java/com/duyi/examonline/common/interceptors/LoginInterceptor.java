package com.duyi.examonline.common.interceptors;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object teacher = request.getSession().getAttribute("loginTeacher");
        if (teacher == null){
            request.getRequestDispatcher("/common/timeout.html").forward(request, response);
            return false;
        }
        return true;
    }
}
