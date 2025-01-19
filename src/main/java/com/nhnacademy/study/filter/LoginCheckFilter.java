package com.nhnacademy.study.filter;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class LoginCheckFilter implements jakarta.servlet.Filter{
    // 제외할 URL 리스트
    private final List<String> excludeUrls = Arrays.asList(
            "/login",
            "/logout",
            "/login.html"
    );


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, jakarta.servlet.FilterChain filterChain) throws IOException, ServletException {
        String requestUrl = ((HttpServletRequest) servletRequest).getRequestURI();
        if (!excludeUrls.contains(requestUrl)) {
            HttpSession session = ((HttpServletRequest) servletRequest).getSession(false);
            if (Objects.isNull(session)) {
                ((HttpServletResponse) servletResponse).sendRedirect("/login.html");
            }

        }
        filterChain.doFilter(servletRequest, servletResponse);


    }
}
