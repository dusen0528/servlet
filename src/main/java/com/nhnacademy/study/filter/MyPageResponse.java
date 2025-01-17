package com.nhnacademy.study.filter;

/**
 * 마이페이지의 응답을 처리하는 클래스.
 * Response 인터페이스를 구현하여 일반 사용자의 마이페이지 내용을 출력합니다.
 *
 * <p>이 클래스는 다음과 같은 정보를 출력합니다:</p>
 * <ul>
 *     <li>사용자 아이디</li>
 *     <li>사용자 이름</li>
 *     <li>사용자 등급 (USER)</li>
 *     <li>사용자 주소</li>
 * </ul>
 *
 * <p>사용 예시:</p>
 * <pre>
 * Request request = new Request("/mypage");
 * request.put("member", Member.createUser("user1", "사용자1", "1234"));
 * new MyPageResponse().doResponse(request);
 * </pre>
 */
public class MyPageResponse implements Response {

    /**
     * 마이페이지 응답을 생성하고 출력하는 메소드.
     * Request 객체에서 member 정보를 추출하여 사용자 정보를 출력합니다.
     *
     * @param request 처리할 요청 객체. member 속성이 포함되어 있어야 합니다.
     * @throws ClassCastException member 속성이 Member 타입이 아닌 경우
     * @throws NullPointerException member 속성이 null인 경우
     */
    @Override
    public void doResponse(Request request) {
        System.out.println("###### response:MyPageResponse #####");
        Member member = (Member) request.get("member");
        System.out.println("아이디:" + member.getId());
        System.out.println("이름:" + member.getName());
        System.out.println("등급:" + Member.Role.USER);
        System.out.println("주소:" + "경남 김해시 내외동 정우빌딩 5층 NHN아카데미");
        System.out.println("do something ... USER ...");
    }
}