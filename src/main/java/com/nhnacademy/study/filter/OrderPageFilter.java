package com.nhnacademy.study.filter;

public class OrderPageFilter implements Filter{
    @Override

    // 주문페이지는 회원은 모두 접근할 수 있습니다. ( NONE ) 제외한 모든 회원은 접근가능 합니다.
    public void doFilter(Request request, FilterChain filterChain) {
        // 1. /order 페이지 요청이라면
        if(request.getPath().equals("/order")){
            // 2. 멤버 정보를 받아오고
            Member member = (Member) request.get("member");
            // 3. None이 아니라면
            if(!member.hasRole(Member.Role.NONE)){
                // 4. 다음 필터로 넘김
                System.out.println("path:" + request.getPath() + " : has ADMIN");
                filterChain.doFilter(request);
            }else{
                System.out.println("path:" + request.getPath() + " : None 등급은 접근 불가능");
            }
        }else{
            System.out.println("OrderPageFilter : 다음 필터로 넘김");
            filterChain.doFilter(request);
        }

    }
}
