package com.nhnacademy.study.filter;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


public class FilterChain {

    // 1. 필터를 순서대로 저장할 LinkedList
    private List<Filter> filters = new LinkedList<>();
    // 2. 필터 순차적으로 접근하기 위한 Iterator
    private Iterator iterator;

    // 3. 새로운 필터 추가 메소드
    public void addFilter(Filter filter){
        // 필터 리스트에 새 필터 추가
        this.filters.add(filter);
        // Iterator 초기화(처음부터 다시 시작하도록)
        iterator = filters.iterator();
    }

    // 4. 실제 필터 체인 실행 메소드
    public void doFilter(Request request){
        // 아직 실행할 필터가 남아있다면 다음 필터 가져오기
        if(iterator.hasNext()){
            Filter nextFiilter = (Filter) iterator.next();
            // 해당 필터 실행
            nextFiilter.doFilter(request,this);
        }else{ // 5. 모든 필터 실행이 끝나면
            // 6. 경로에 따른 최종 응답 생성하기
            if(request.getPath().equals("/mypage")){
                new MyPageResponse().doResponse(request);
            }else if(request.getPath().equals("/admin")){
                new AdminPageResponse().doResponse(request);
            }else if(request.getPath().equals("/order")){
                new OrderResponse().doResponse(request);
            }
            else{
                new NotFoundResponse().doResponse(request);
            }
        }
    }

}
/**
 * 필터 체인 동작 원리 설명
 *
 * 영화관 입장 절차를 예로 들면:
 * 1. 티켓 검사
 * 2. 음식물 검사
 * 3. 연령 확인
 *
 * Iterator는 "다음 검사로 이동"하는 역할을 합니다.
 *
 * <pre>
 * // Iterator 없이 구현한다면:
 * for(Filter filter : filters) {
 *     filter.doFilter(request, this);
 * }
 * </pre>
 * 이렇게 하면 모든 필터가 한 번에 다 실행됩니다.
 * 마치 영화관에서 모든 검사를 동시에 하는 것과 같습니다.
 *
 * <pre>
 * // Iterator를 사용하면:
 * if(iterator.hasNext()) {
 *     Filter nextFilter = iterator.next();
 *     nextFilter.doFilter(request, this);
 * }
 * </pre>
 * 이렇게 하면 한 번에 하나의 필터만 실행하고,
 * 각 필터가 다음 필터로 진행할지 말지를 결정할 수 있습니다.
 *
 * 예시:
 * - 티켓이 없으면 → 더 이상 진행 X
 * - 티켓이 있으면 → 음식물 검사로 이동
 * - 음식물 있으면 → 더 이상 진행 X
 * - 음식물 없으면 → 연령 확인으로 이동
 *
 * 이런 식으로 각 단계에서 "다음으로 진행할지 말지"를 결정할 수 있게 됩니다.
 */