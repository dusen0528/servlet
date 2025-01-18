package com.nhnacademy.study.listener;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.StandardCharsets;

@Slf4j
public class ServletContextListener implements jakarta.servlet.ServletContextListener {



    // 웹 어플리케이션 시작시 실행되는 메소드
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // 1. ServletContext 객체 가져오기 (웹앱 정보를 담고 있는 객체)
        ServletContext servletContext = sce.getServletContext();

        // 2. web.xml에서 설정한 counter 파일 가져오기
        String counterFileName = servletContext.getInitParameter("counterFileName");

        // 3. 실제 파일 경로 생성
        String counterFilePath = "/WEB-INF/classes/" + counterFileName;

        // 4. 서버의 실제 파일시스템 경로로 변환
        String realFilePath = servletContext.getRealPath(counterFilePath);
        log.error("path:{}", realFilePath);

        // 5. 파일 객체 생성
        File target = new File(realFilePath);

        // 6. 파일이 존재하는 경우에만 처리
        if(target.exists()){
            // 7. 파일 읽기 위한 스트림 설정
            try(
                    FileInputStream fileInputStream = new FileInputStream(target);
                    InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    ){
                // 8. 파일에서 숫자 읽고 long 변환
                long c = Long.parseLong(bufferedReader.readLine());

                // 9. 읽은 값을 Servlet Context에 속성으로 저장
                servletContext.setAttribute("counter", c);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        log.error("init counter : {}", servletContext.getAttribute("counter"));
    }


    // 웹 어플리케이션 종료시 실행되는 메소드
    @Override
    public void contextDestroyed(ServletContextEvent sce) {

        // 1. ServletContext 객체 가져오기
        ServletContext servletContext = sce.getServletContext();

        // 2. web.xml에서 counter 파일명 가져오기
        String counterFileName = servletContext.getInitParameter("counterFileName");

        // 3. 파일 경로 생성
        String counterFilePath = "/WEB-INF/classes/" + counterFileName;
        String realFilePath = servletContext.getRealPath(counterFilePath);

        // 4. 파일에 counter 값 쓰기
        try(
                FileOutputStream fileOutputStream = new FileOutputStream(realFilePath);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream , StandardCharsets.UTF_8);
                BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                ){
                    // 5. ServletContext에서 counter 값을 가져온 후 문자열 변환 -> 파일에 쓰기
                    bufferedWriter.write(String.valueOf(servletContext.getAttribute("counter")));

        }catch (IOException e){
            e.printStackTrace();
        }

        // 6, 종료시 최종 counter 값 출력
        System.out.println("destroy-counter:" + servletContext.getAttribute("counter"));
    }
}
