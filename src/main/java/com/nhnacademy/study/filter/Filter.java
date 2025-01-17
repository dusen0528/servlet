package com.nhnacademy.study.filter;


public interface Filter {
    void doFilter(Request request, FilterChain filterChain);
}
