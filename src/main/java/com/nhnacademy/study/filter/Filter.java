package com.nhnacademy.study.filter;


import jakarta.servlet.ServletException;

public interface Filter {

    default public void init(FilterConfig filterConfig) throws ServletException {}

    void doFilter(Request request, FilterChain filterChain);
}
