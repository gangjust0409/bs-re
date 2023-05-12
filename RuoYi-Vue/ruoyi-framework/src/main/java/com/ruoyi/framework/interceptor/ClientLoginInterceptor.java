package com.ruoyi.framework.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 客户端防未登录的状态访问已经登录的操作
 */
@Component
public abstract class ClientLoginInterceptor implements HandlerInterceptor {

    protected Set<String> urlSet = new HashSet<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return initUrlInterceptors(request, response);
    }

    //加载需要登录才能访问的路径
    public abstract boolean initUrlInterceptors(HttpServletRequest request, HttpServletResponse response);
}
