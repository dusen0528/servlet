package com.nhnacademy.study;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class NowServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        CounterUtils.increaseCounter(getServletContext());

        resp.setCharacterEncoding("UTF-8");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime localDateTime = LocalDateTime.now();
        String nowDateTimeString = localDateTime.format(dateTimeFormatter);

        try(PrintWriter writer = resp.getWriter()){
            writer.println("<!DOCTYPE html>");
            writer.println("<html>");
            writer.println("<head>");
            writer.println("<meta charset='utf-8'>");
            writer.println("</head>");
            writer.println("<body>");
            writer.println("<h1>현재 시간</h1>");
            writer.println("<h1>"+nowDateTimeString+"</h1>");
            writer.println("<h1> counter : " + getServletContext().getAttribute("counter") + "<h1>");

            writer.println("</body>");
            writer.println("</html>");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        log.info("befor init!");
        super.init(config);
    }
}
