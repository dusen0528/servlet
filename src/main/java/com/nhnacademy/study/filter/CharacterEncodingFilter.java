package com.nhnacademy.study.filter;


import jakarta.servlet.ServletException;

import java.io.IOException;

public class CharacterEncodingFilter implements Filter {
    private String encoding = "UTF-8";


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.encoding = filterConfig.getInitParameter("encoding");
    }

    @Override
    public void doFilter(Request request, FilterChain filterChain) {
        request.setCharacterEncoding(this.encoding);
        System.out.println("###########encoding success!!!!!!");
        filterChain.doFilter(request);
    }


}