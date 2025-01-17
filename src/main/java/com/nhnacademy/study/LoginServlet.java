package com.nhnacademy.study;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;


@Slf4j

public class LoginServlet extends HttpServlet {

    private String initParamId;
    private String initParamPwd;


    // 서버가 시작될 때 미리 설정된 아이디와 비밀번호를 가져와 저장해둠
    @Override
    public void init(ServletConfig config) throws ServletException {
        initParamId = config.getInitParameter("id");
        initParamPwd = config.getInitParameter("pwd");
    }


    // 로그인 페이지 보여주기
    // 사용자가 /login 페이지 접속시 실행
    // 로그인 안되어있으면 login.html , 로그인 되어 있으면 로그인 성공 메세지와 로그아웃 링크 보여줌
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if(Objects.isNull(session)|| Objects.isNull(session.getAttribute("id"))){
            resp.sendRedirect("/login.html");
        }else{
            resp.setContentType("text/html");
            resp.setCharacterEncoding("utf-8");

            try(PrintWriter out = resp.getWriter()){
                out.println("<!DOCTYPE html");
                out.println("<html>");
                out.println("<head>");
                out.println("<meta charset='utf-8'>");
                out.println("</head>");
                out.println("<body>");
                out.println("login success : id =" + session.getAttribute("id")+"<br/>");
                out.println("<a href='/logout'>logout</a>");
                out.println("</body>");
                out.println("</html>");
            }
        }

    }


    /*
    로그인 처리하기
    사용자가 로그인 폼에서 아이디/비번 입력후 제출시 실행
    입력받은 아이디 비번 체크 후
        1. 일치 시 -> 로그인 성공처리(세션에 저장) 후 /login 페이지 이동
        2. 불일치 시 -> 다시 login.html로 이동
     */

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String pwd = req.getParameter("pwd");

        if(initParamId.equals(id)&& initParamPwd.equals(pwd)){
            HttpSession session = req.getSession();
            session.setAttribute("id",id);
            resp.sendRedirect("/login");
        }
        else {
            log.error("ID/Password 일치하지 않습니다");
            resp.sendRedirect("/login.html");
        }

    }
}
