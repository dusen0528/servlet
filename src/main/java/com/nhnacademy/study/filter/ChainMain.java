//package com.nhnacademy.study.filter;
//
///**
// * 필터 체인 패턴의 동작을 테스트하는 메인 클래스.
// * 다양한 요청 시나리오를 실행하여 필터 체인의 작동을 검증합니다.
// *
// * <p>테스트하는 시나리오:</p>
// * <ul>
// *     <li>일반 사용자의 마이페이지 접근</li>
// *     <li>관리자의 관리자 페이지 접근</li>
// * </ul>
// */
//public class ChainMain {
//    /**
//     * 메인 메소드. 필터 체인의 동작을 테스트합니다.
//     *
//     * <p>실행 순서:</p>
//     * <ol>
//     *     <li>마이페이지 요청 생성 및 테스트
//     *         <ul>
//     *             <li>일반 사용자 계정으로 요청</li>
//     *             <li>MyPageFilter를 통과해야 함</li>
//     *         </ul>
//     *     </li>
//     *     <li>관리자 페이지 요청 생성 및 테스트
//     *         <ul>
//     *             <li>관리자 계정으로 요청</li>
//     *             <li>AdminPageFilter를 통과해야 함</li>
//     *         </ul>
//     *     </li>
//     * </ol>
//     *
//     */
//    public static void main(String[] args) {
//        // 세션이 없는 요청
//        Request noSessionRequest = new Request("/mypage");
//        System.out.println("\n############ 세션 없는 요청 ############");
//        new HttpRequest().doRequest(noSessionRequest);
//
//        // 세션이 있는 요청
//        Request withSessionRequest = new Request("/mypage");
//        withSessionRequest.setSession("USER_SESSION");  // 임의의 세션 설정
//        withSessionRequest.put("member", Member.createUser("user1", "사용자1", "1234"));
//        System.out.println("\n############ 세션 있는 요청 ############");
//        new HttpRequest().doRequest(withSessionRequest);
//    }
//}