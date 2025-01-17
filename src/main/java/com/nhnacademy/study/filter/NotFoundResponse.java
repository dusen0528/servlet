package com.nhnacademy.study.filter;

public class NotFoundResponse implements Response{
    @Override
    public void doResponse(Request request) {
        System.out.println("###### response:NotFoundResponse #####");
        System.out.println("----------------------------------------");
        System.out.println("Error 404: 존재하지 않는 페이지!");
        System.out.println("요청한 경로: " + request.getPath());
        System.out.println("----------------------------------------");
        System.out.println("올바른 경로인지 확인해주세요.");
        System.out.println("사용 가능한 경로: /admin, /mypage, /order");
    }
}
