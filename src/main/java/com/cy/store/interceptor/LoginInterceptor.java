package com.cy.store.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** define a interceptor */

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        //check if logged in
        Object obj = request.getSession().getAttribute("uid");
        if(obj == null) {
            response.sendRedirect("/web/login.html");
            //end following

            return false;
        }
        return true;
    }
}
