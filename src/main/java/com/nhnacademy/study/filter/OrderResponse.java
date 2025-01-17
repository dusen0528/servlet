package com.nhnacademy.study.filter;

public class OrderResponse implements  Response{
    @Override
    public void doResponse(Request request) {
        System.out.println("###### response:OrderResponse #####");
        Member member = (Member) request.get("member");
        System.out.println("아이디: " + member.getId());
        System.out.println("이름: " + member.getName());
        System.out.println("\n===== 주문 내역 =====");
        System.out.println("주문번호: ORDER-2024-001");
        System.out.println("주문 상품 목록:");
        System.out.println("1. Java 프로그래밍 기초 - 35,000원");
        System.out.println("2. 스프링 부트 마스터 - 45,000원");
        System.out.println("3. 클린 코드 가이드 - 30,000원");
        System.out.println("------------------------");
        System.out.println("총 주문금액: 110,000원");
        System.out.println("배송지: 경남 김해시 내외동 정우빌딩 5층");
        System.out.println("배송 상태: 배송 준비중");
        System.out.println("===================");

    }
}
