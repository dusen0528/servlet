package com.nhnacademy.study.filter;

/**
 * 필터 체인 패턴의 동작을 테스트하는 메인 클래스.
 * 다양한 요청 시나리오를 실행하여 필터 체인의 작동을 검증합니다.
 *
 * <p>테스트하는 시나리오:</p>
 * <ul>
 *     <li>일반 사용자의 마이페이지 접근</li>
 *     <li>관리자의 관리자 페이지 접근</li>
 * </ul>
 */
public class ChainMain {
    /**
     * 메인 메소드. 필터 체인의 동작을 테스트합니다.
     *
     * <p>실행 순서:</p>
     * <ol>
     *     <li>마이페이지 요청 생성 및 테스트
     *         <ul>
     *             <li>일반 사용자 계정으로 요청</li>
     *             <li>MyPageFilter를 통과해야 함</li>
     *         </ul>
     *     </li>
     *     <li>관리자 페이지 요청 생성 및 테스트
     *         <ul>
     *             <li>관리자 계정으로 요청</li>
     *             <li>AdminPageFilter를 통과해야 함</li>
     *         </ul>
     *     </li>
     * </ol>
     *
     */
    public static void main(String[] args) {
        // 기존 테스트
        Request myPageRequest = new Request("/mypage");
        myPageRequest.put("member", Member.createUser("inho", "인호", "1234"));

        Request adminPageRequest = new Request("/admin");
        adminPageRequest.put("member", Member.createAdmin("admin", "관리자", "1234"));

        // 1. 주문 페이지 테스트 - 다양한 권한으로 테스트
        System.out.println("\n############ ADMIN의 /order 요청 ############");
        Request orderRequestAdmin = new Request("/order");
        orderRequestAdmin.put("member", Member.createAdmin("admin", "관리자", "1234"));
        new HttpRequest().doRequest(orderRequestAdmin);

        System.out.println("\n############ USER의 /order 요청 ############");
        Request orderRequestUser = new Request("/order");
        orderRequestUser.put("member", Member.createUser("user", "사용자", "1234"));
        new HttpRequest().doRequest(orderRequestUser);

        System.out.println("\n############ MANAGER의 /order 요청 ############");
        Request orderRequestManager = new Request("/order");
        orderRequestManager.put("member", Member.createManager("manager", "매니저", "1234"));
        new HttpRequest().doRequest(orderRequestManager);

        System.out.println("\n############ NONE의 /order 요청 (접근 불가) ############");
        Request orderRequestNone = new Request("/order");
        orderRequestNone.put("member", Member.createUncertifiedMember("none", "미인증", "1234"));
        new HttpRequest().doRequest(orderRequestNone);

        // 2. 존재하지 않는 페이지 테스트
        System.out.println("\n############ /main 요청 (없는 페이지) ############");
        Request mainRequest = new Request("/main");
        mainRequest.put("member", Member.createUser("user", "사용자", "1234"));
        new HttpRequest().doRequest(mainRequest);
    }
}