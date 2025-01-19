package com.nhnacademy.study;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;



@Slf4j
@WebServlet(
        name="fileDownloadServlet",
        urlPatterns = "/file/fileDownload"
)



public class FileDownloadServlet extends HttpServlet {

    private static final String UPLOAD_DIR = "/Users/inho/adminpage/web/study/src/main/upload";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // URL 파라미터에서 다운로드할 파일 이름을 가져옴
        String fileName = req.getParameter("fileName");

        // 파일 이름이 없으면 잘못된 요청 400에러 반환
        if(Objects.isNull(fileName)|| fileName.isEmpty()){
            resp.sendError(400, "FileName parameter is needed");
        }


        // 전체 파일 경로 설정(디렉토리 경로 + 파일이름)
        String fiilePath = UPLOAD_DIR + File.separator + fileName;
        Path path = Path.of(fiilePath);

        // 파일이 실제로 존재하지 않으면 404 에러
        if(!Files.exists(path)){
            log.error("File not found : "+ fileName);
            resp.sendError(404, "File not Found:"+ fileName);
            return;
        }


        // 파일 다운로드를 위한 응답 헤더 설정
        resp.setContentType("application/octet-stream"); // 모든 종류의 이진 데이터 의미
        // attachment : 브라우저가 파일을 다운로드하도록 지시
        resp.setHeader("Content-Disposition", "attachment; filename=" + fileName);


        // 파일 읽고 쓰기
        try(
                // 파일에서 데이터 읽기 위한 스트림
                InputStream is = Files.newInputStream(path);
                // 클라이언트에게 데이터를 보내기 위한 출력 스트림
                OutputStream os = resp.getOutputStream()
                ){

            // 4kb 크기의 버퍼 생성
            byte[] buffer = new byte[4096];

            int n;

            // 파일 내용을 버퍼를 사용해 조금씩 읽어서 클라이언트에게 전송
            while(-1 != (n= is.read(buffer))){ // 파일의 끝 -1 에 도달할 때까지 읽기
                os.write(buffer, 0, n); // 읽은만큼만 쓰기
            }
        }catch (IOException e){
            log.error("", e);
        }

    }
}
