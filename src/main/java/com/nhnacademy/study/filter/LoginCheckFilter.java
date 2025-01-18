package com.nhnacademy.study.filter;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.util.Arrays;
import java.util.List;

public class LoginCheckFilter implements jakarta.servlet.Filter{
    // 제외할 URL 리스트
    private final List<String> excludeUrls = Arrays.asList(
            "/login",
            "/logout",
            "/login.html"
    );


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, jakarta.servlet.FilterChain filterChain) throws IOException, ServletException {
        String requestUrl = ((HttpServletRequest)servletRequest).getRequestURI();
        if(excludeUrls.contains(requestUrl)){
            HttpSession httpSession = ((HttpServletRequest)servletRequest).getSession();
            System.out.println("제외된 URL:" + requestUrl + " -> 필터 통과");
            return;
        }
        else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
