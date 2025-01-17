package com.nhnacademy.study;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

@Slf4j
public class DomainCookieServlet extends HttpServlet {

    /*
    a.com은 우리집
    b.com은 이웃집
    1.a.com과 2.a.com은 우리집 안의 다른 방이에요
    쿠키를 우리집(a.com)에 두면, 우리집 안의 다른 방(1.a.com, 2.a.com)에서도 볼 수 있지만
    이웃집(b.com)에서는 볼 수 없어요!
     */

    /*
    주소 끝이 /read면 쿠키를 읽고
    주소 끝이 /wrtie면 새 쿠키를 만들고
    그것도 아니라면 첫화면을 보여줌
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestUri = req.getRequestURI();
        log.error("requestUri:{}",requestUri);
        if (requestUri.endsWith("/read")) {
            readCookie(req, resp);
        } else if(requestUri.endsWith("/write")) {
            writeCookie(req, resp);
        } else {
            show(req, resp);
        }
    }

    // 첫 화면 보여주기 (메뉴판처럼 여러 링크 보여주기)
    private void show(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("utf-8");

        try(PrintWriter out = resp.getWriter()) {
            out.println("<html>");
            out.println("<head><title>cookie test</title></head>");
            out.println("<body>");
            out.println("<ul>");
            out.println("<li><a href='http://a.com:8080/domain-cookie/write?domain=a.com'>set cookie: domain=a.com, path=/</a></li>");
            out.println("<li><a href='http://a.com:8080/domain-cookie/read'>get cookie: domain=a.com</a><br /></li>");
            out.println("<li><a href='http://b.com:8080/domain-cookie/read'>get cookie: domain=b.com</a><br /></li>");
            out.println("<li><a href='http://1.a.com:8080/domain-cookie/read'>get cookie: domain=1.a.com</a><br /></li>");
            out.println("<li><a href='http://2.a.com:8080/domain-cookie/read'>get cookie: domain=2.a.com</a><br /></li>");
            out.println("<li><a href='http://1.a.com:8080/domain-cookie/more/read'>get cookie: domain=1.a.com, path=/domain-cookie/more/read</a><br /></li>");
            out.println("</ul>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    private static final String COOKIE_NAME = "cook2";

    // 쿠기 읽기 저장된 쿠키를 찾아서 보여줌, 쿠카기 았다면 쿠기 이름과 내용을 화면에 출력
    private void readCookie(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(Objects.isNull(req.getCookies())){
            return;
        }
        try(PrintWriter out = resp.getWriter()){
            for(int i=0; i<req.getCookies().length; i++){
                Cookie cookie = req.getCookies()[i];
                out.println(cookie.getName() + ":" + cookie.getValue());
            }
        }catch (Exception e){
            log.error("read-cookie error : {}",e.getMessage(),e);
        }
    }

    private static final String MORE_PATH = "/domain-cookie/more/write";

    // 새로운 쿠키를 만들어 특정 웹사이트에 저장하며 쿠키에는 이름(final String COOKIE_NAME)을 붙이고 어느 웹사이트에서 사용할 수 있는지 설정
    private void writeCookie(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String domain = req.getParameter("domain");
        String path = MORE_PATH.equals(req.getRequestURI())
                ? MORE_PATH.replace("/write", "") : "/";

        Cookie newCookie = new Cookie(COOKIE_NAME, req.getRequestURL().append("?").append(req.getQueryString()).toString());
        newCookie.setDomain(domain);
        newCookie.setPath(path);

        resp.addCookie(newCookie);

        log.error("cookieName : {}", newCookie.getName());
        log.error("cookieValue : {}", newCookie.getValue());
        log.error("cookieDomain : {}", newCookie.getDomain());

        show(req, resp);
    }

}