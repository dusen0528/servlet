package com.nhnacademy.study;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.io.PrintWriter;


@Slf4j
public class BeautifyServlet extends HttpServlet {



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CounterUtils.increaseCounter(getServletContext());

        req.setCharacterEncoding("utf-8");
        String html = req.getParameter("html");
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("utf-8");

        try(PrintWriter out = resp.getWriter()){
            out.println(Jsoup.parse(html));
        }catch (Exception ex){
            log.info(ex.getMessage());
        }
    }
}