package com.nhnacademy.study.filter;

public class MyPageFiliter implements  Filter{
    @Override
    public void doFilter(Request request, FilterChain filterChain) {

        // 1. 경로가 mypage이면
        if(request.getPath().equals("/mypage")){
            // 2. 멤버의 정보를 가져옴
            Member member = (Member) request.get("member");
            // 3. 멤버의 권한이 유저라면
            if(member.hasRole(Member.Role.USER)){
                // 4. 다음 필터로 진행
                System.out.println("path :" + request.getPath()+ "member rolr has USER : True");
                filterChain.doFilter(request);
            }else{
                System.out.println("MyPageCheckFilter : 다음 필터로 넘김");
            }

            // 5. 경로가 mypage가 아니면 다음 필터로 넘김
        }else{
            System.out.println("MyPageCheckFilter : 다음 필터로 넘김 !");

            filterChain.doFilter(request);
        }
    }
}
