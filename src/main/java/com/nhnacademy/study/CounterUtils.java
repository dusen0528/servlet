package com.nhnacademy.study;

import jakarta.servlet.ServletContext;

import java.util.Optional;

public class CounterUtils {
    private CounterUtils(){
        throw new IllegalStateException("Utility Class");
    }


    public static void increaseCounter(ServletContext servletContext ){
        Long counter = Optional.ofNullable((Long)servletContext.getAttribute("counter")).orElse(0L);
        counter+=1;
        servletContext.setAttribute("counter", counter);

    }
}
