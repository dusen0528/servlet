package com.nhnacademy.study;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;

// MultipartConfig 어노테이션: 파일 업로드 처리를 위한 설정
@MultipartConfig(
        fileSizeThreshold   = 1024 * 1024 * 1,  // 1 MB - 메모리에서 파일을 처리하는 크기 임계값
        maxFileSize         = 1024 * 1024 * 10, // 10 MB - 단일 파일의 최대 허용 크기
        maxRequestSize      = 1024 * 1024 * 100, // 100 MB 전체 multipart/form-data 요청의 최대 허용 크기
        // 임시 파일이 저장될 디렉토리 위치
        location = "/Users/inho/adminpage/web/study/temp"
)
@Slf4j
public class FileUploadServlet extends HttpServlet {
    // Content-Disposition 헤더의 상수 정의
    // HTTP Header의 한 종류
    /*
    예시
    CopyContent-Disposition: form-data; name="myFile"; filename="cat.jpg"
    이걸 분석해보면:

    form-data: 이건 폼에서 전송된 데이터라는 뜻
    name="myFile": HTML 폼에서 정의한 필드 이름
    filename="cat.jpg": 실제 업로드하는 파일의 이름
     */
    private static final String CONTENT_DISPOSITION = "Content-Disposition";

    // 실제 파일이 저장될 디렉토리 경로
    private static final String UPLOAD_DIR = "/Users/inho/adminpage/web/study/src/main/upload";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 요청으로부터 모든 Part(폼 데이터의 각 부분) 순회
        for(Part part : req.getParts()){
            // 각 Part의 Content-Disposition 헤더를 가져옴
            String contentDisposition = part.getHeader(CONTENT_DISPOSITION);

            // filename이 포함되어 있다면 파일 업로드 Part
            if (contentDisposition.contains("filename=")) {
                // 파일명 추출
                String fileName = extractFileName(contentDisposition);

                // 파일 크기가 0보다 크다면 (실제 파일이 업로드 된 경우)
                if (part.getSize() > 0) {
                    // 지정된 경로에 파일 저장
                    part.write(UPLOAD_DIR + File.separator + fileName);
                    // 임시 파일 삭제
                    part.delete();
                }
                // 일반 폼 데이터인 경우
            } else {
                String formValue = req.getParameter(part.getName());
                // 폼데이터의 이름과 값을 로그로 출력
                log.error("{}={}", part.getName(), formValue);
            }
        }

        // 모든 처리가 완료되면 루트경로로 리다이렉션
        resp.sendRedirect("/");
    }


    /*
        Content-Disposition 헤더에서 파일명을 추출하는 메소드
     */
    private String extractFileName(String contentDisposition) {
        // 헤더 값 로그 출력
        log.error("contentDisposition:{}",contentDisposition);
        // 세미 콜론으로 분리하여 각 부분 확인
        /*
        예시
        ["form-data", " name="fileInput"", " filename="cat.jpg""]
         */
        for (String token : contentDisposition.split(";")) {
            // 파일명으로 시작하는 부분 찾아서
            if (token.trim().startsWith("filename")) {
                /*
                for 반복문으로 각 부분을 확인하면서:

                form-data -> filename으로 시작 안함, 다음으로
                name="fileInput" -> filename으로 시작 안함, 다음으로
                filename="cat.jpg" -> filename으로 시작함! 여기서 처리

                 */
                // = 다음부터 마지막 따옴푯 전까지의 문자열(실제 파일명) 반환
                return token.substring(token.indexOf("=")+2, token.length()-1);
            }
        }
        // 파일 못찾으면 null 반환
        return null;
    }
}