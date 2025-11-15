package com.example.springforum.common;

import com.example.springforum.common.constants.Constant;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class Interceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);

        if (session != null && session.getAttribute(Constant.USER_SESSION_KEY) != null) {
            return true;
        }
        response.sendRedirect("/sign-in.html");
//        System.out.println(session.getAttribute(Constant.USER_SESSION_KEY));
//        log.info("session is null");
        return true;
    }
}
