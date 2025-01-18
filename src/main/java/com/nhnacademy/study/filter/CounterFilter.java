package com.nhnacademy.study.filter;

import com.nhnacademy.study.CounterUtils;
import jakarta.servlet.*;  // jakarta.servlet 패키지의 모든 기본 인터페이스
import jakarta.servlet.Filter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class CounterFilter implements jakarta.servlet.Filter {  // jakarta.servlet.Filter 구현


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, jakarta.servlet.FilterChain filterChain) throws IOException, ServletException {
        // 방문자 카운터 증가
        CounterUtils.increaseCounter(servletRequest.getServletContext());

        // 다음 필터로 요청 전달
        filterChain.doFilter(servletRequest, servletResponse);

        // 카운터 값 로그 출력
        log.error("counter:{}", servletRequest.getServletContext().getAttribute("counter"));
    }
}