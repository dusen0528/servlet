package com.nhnacademy.study.filter;

/**
 * HTTP 요청을 처리하고 필터 체인을 초기화하는 클래스
 *
 * <p>이 클래스는 다음과 같은 역할을 수행합니다:</p>
 * <ul>
 *     <li>필터 체인 초기화</li>
 *     <li>필터들의 등록</li>
 *     <li>요청 처리 시작점 제공</li>
 * </ul>
 *
 * <p>실제 동작 예시:</p>
 * <pre>
 *     Request request = new Request("/admin");
 *     HttpRequest httpRequest = new HttpRequest();
 *     httpRequest.doRequest(request);  // 필터 체인 시작
 * </pre>
 */
public class HttpRequest {
    /** 요청 처리를 위한 필터 체인 */
    private final FilterChain filterChain = new FilterChain();

    /**
     * 생성자.
     * 객체 생성 시 필터들을 초기화합니다.
     */
    public HttpRequest(){
        initFilter();
    }

    /**
     * 실제 요청 처리를 시작하는 메소드.
     * 필터 체인을 통해 요청을 순차적으로 처리합니다.
     *
     * @param request 처리할 요청 객체
     */
    public void doRequest(Request request){
        filterChain.doFilter(request);
    }

    /**
     * 필터들을 초기화하고 등록하는 private 메소드.
     * 현재는 MyPageFilter와 AdminPageFilter를 등록합니다.
     */
    private void initFilter(){
        filterChain.addFilter(new MyPageFiliter());
        filterChain.addFilter(new AdminPageFilter());
        filterChain.addFilter(new OrderPageFilter());
    }
}