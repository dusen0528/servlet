package com.nhnacademy.study.filter;

import java.util.Arrays;
import java.util.List;

public class LoginCheckFilter implements Filter{
    // 제외할 URL 리스트
    private final List<String> excludeUrls = Arrays.asList(
            "/login",
            "/logout",
            "/login.html"
    );


    @Override
    public void doFilter(Request request, FilterChain filterChain) {
        if(excludeUrls.contains(request.getPath())){
            System.out.println("제외된 URL:" + request.getPath() + " -> 필터 통과");
            filterChain.doFilter(request);
            return;
        }

        // Session Check
        Object session = request.getSession();
        if(session==null){
            System.out.println("세션이 없어 로그인 페이지로 이동합니다");
            System.out.println("Redirect to /login.html");
        }


    }
}
