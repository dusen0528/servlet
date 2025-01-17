package com.nhnacademy.study.filter;



import java.util.Objects;

public class AdminPageFilter implements Filter{
    /*
    관리자 페이지 접근을 제어하는 필터
     */
    @Override
    public void doFilter(Request request, FilterChain filterChain) {
        // 1. /admin 경로이면
        if(request.getPath().equals("/admin")){
            // 2. request에서 멤버 정보를 가져오고
            Member member = (Member) request.get("member");
            // 3. member가 null이 아니면
            if(Objects.nonNull(member)){
                // 4. member가 ADMIN 권한인지 확인한 후
                if(member.hasRole(Member.Role.ADMIN)){
                    // 5. ADMIN이면 다음 필터로 진행
                    System.out.println("path:"+request.getPath()+" : has ADMIN");
                    filterChain.doFilter(request);
                }else{
                    // 6. ADMIN이 아니면 접근 거부 메세지
                    System.out.println("path:" + request.getPath() + " : has NOT ADMIN");
                }
            }
        }
        // 7. /admin 경로가 아닌 경우 다음 필터에 넘겨서 요청을 처리
        else{
            System.out.println("MyPageCheckFilter : 다음 필터로 넘김 !");

            filterChain.doFilter(request);
        }
        /*
        // /mypage 요청이 왔을 때
            request.path = "/mypage"

            AdminPageFilter 동작:
            1. if("/admin"과 일치?) -> 일치하지 않음
            2. else로 이동
            3. "다음 필터로 넘김" 출력
            4. filterChain.doFilter(request) 호출 -> MyPageFilter로 넘어감

            MyPageFilter가 /mypage 요청을 처리...

         */
    }
}
