package com.nhnacademy.study;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

@Slf4j
@WebServlet(
        name = "requestServlet",
        urlPatterns = "/req"
)

public class RequestServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /* TODO#1 buffer Size 설정
            1024 byte = 1kb
             - 1KB 이하의 작은 양의 데이터 전송 시 출력 buffer가 꽉 차지 않아도 바로 전송.
             - 1KB 이상의 큰 데이터양을 전송할 때는 출력 버퍼가 가득 차기 전까지 데이터를 쌓은 다음에 한 번에 전송.
             - default bufferSize : 8192 byte = 8KB
         */
        log.info("default buffer size : {}", resp.getBufferSize());
        resp.setBufferSize(1024);
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");

        try(PrintWriter out = resp.getWriter()){
            out.println("locale=" + req.getLocale());
            out.println("parameter name=" + req.getParameter("name"));

            // TODO#2 flush 버퍼링 된 출력 바이트를 즉시 쓰도록(소켓을 통해서 내보냄) 강제함. clinet와 연결이 종료됨.
//            out.flush();
//            out.close();

            String userId = req.getParameter("userId");
            log.info("userId:{}",userId);
            if (userId == null || userId.isEmpty()) {
                // TODO#3 response를 초기화.
                resp.reset();
                // TODO#4 error 코드를 500 설정함
                resp.setStatus(500);
                // TODO #5  error 코드를 500으로 설정하고 error message  'name is empty' 설정함.
                resp.sendError(500, "name is empty");
                return;
            }

            //TODO#6 redirect
            String redirect = req.getParameter("redirect");
            if(Objects.nonNull(redirect)){
                resp.sendRedirect(redirect);
                return;
            }

            out.println("method=" + req.getMethod());
            out.println("request uri=" + req.getRequestURI());

            //TODO#7 reset buffer - response 객체에 담겨있던 모든 buffer 초기화
            resp.resetBuffer();

            out.println("User-Agent header=" + req.getHeader("User-Agent"));
        }catch (Exception e){
            log.error("/req : {}",e.getMessage(),e);
        }
    }
}