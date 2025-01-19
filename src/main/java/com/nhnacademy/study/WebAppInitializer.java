package com.nhnacademy.study;

import jakarta.servlet.ServletContainerInitializer;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.HandlesTypes;

import java.util.Set;

@HandlesTypes(value = {
        jakarta.servlet.http.HttpServlet.class,
        jakarta.servlet.Filter.class,
        jakarta.servlet.ServletContextListener.class,
        jakarta.servlet.http.HttpSessionListener.class
})

public class WebAppInitializer implements ServletContainerInitializer {
    @Override
    public void onStartup(Set<Class<?>> set, ServletContext servletContext) throws ServletException {
        servletContext.setInitParameter("url","https://nhnacademy.com");
        servletContext.setInitParameter("counterFileName","counter.dat");


    }
}
