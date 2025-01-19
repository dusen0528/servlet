# 리스너(Listener) 쉽게 이해하기

## 리스너란?
웹 애플리케이션에서 발생하는 다양한 이벤트를 감지하고 반응하는 객체입니다.

### 실생활 비유
- 카페의 호출벨 시스템: 고객이 벨을 누르면(이벤트) 직원이 반응
- 매장 오픈/클로징 체크리스트: 매장 상태 변화 감지
- 고객 입장/퇴장 카운터: 고객 수 변화 감지

## 주요 리스너 종류와 역할

### 1. ServletContextListener (웹 애플리케이션 생명주기 감지)
- 웹 앱 시작할 때: 초기화 작업 수행
- 데이터베이스 연결
- 초기 설정 로드
- 필요한 리소스 준비
- 웹 앱 종료할 때: 정리 작업 수행
- 리소스 정리
- 연결 종료
- 임시 파일 삭제

### 2. HttpSessionListener (사용자 세션 감지)
- 새로운 사용자 접속 시: 세션 생성 감지
- 접속자 수 증가
- 사용자 초기화
- 사용자 접속 종료 시: 세션 소멸 감지
- 접속자 수 감소
- 사용자 정리

## 실제 활용 사례

### 1. 접속자 수 관리
- 실시간 접속자 수 카운팅
- 동시 접속자 제한
- 접속 통계 수집

### 2. 시스템 초기화
- 서버 시작 시 필요한 데이터 로드
- 캐시 초기화
- 설정 파일 로드

### 3. 모니터링
- 리소스 사용량 감시
- 비정상 접속 감지
- 성능 측정

## 리스너 사용의 장점
1. 자동화된 이벤트 처리
2. 시스템 상태 모니터링 용이
3. 분리된 로직으로 유지보수 편리
4. 실시간 대응 가능

## 주의사항
1. 너무 많은 리스너는 성능 저하 유발
2. 리스너 내 무거운 작업은 피하기
3. 예외 처리 철저히 하기
4. 리소스 관리 주의

---
# 방문자 카운터 재구현
- 방문수를 저장할 파일 위치는 web.xml에 지정

```
<context-param>
    <param-name>counterFileName</param-name>
    <param-value>counter.dat</param-value>
</context-param>
```

## 1. ServletContextListener 구현 (초기화)
- 웹 애플리케이션 시작 시 동작
- counter.dat 파일에서 방문자 수 읽어오기
- 위치: /WEB-INF/classes/counter.dat
- BufferedReader 사용 (UTF-8)
- 읽어온 값을 ServletContext에 저장

## 2. CounterFilter 구현 (카운터 증가)
- 모든 서블릿 요청에 대해 동작
- ServletContext에서 현재 카운터 값 읽기
- 카운터 1 증가
- 증가된 값을 다시 ServletContext에 저장

## 3. ServletContextListener 구현 (종료 처리)
- 웹 애플리케이션 종료 시 동작
- ServletContext에서 최종 카운터 값 읽기
- counter.dat 파일에 값 저장
- BufferedWriter 사용 (UTF-8)

## 동작 과정
1. 웹 앱 시작
- counter.dat 읽기
- ServletContext에 저장
2. 요청 발생
- 카운터 증가
- ServletContext 업데이트
3. 웹 앱 종료
- 최종 값 파일 저장

## 테스트 방법
1. 웹 앱 시작
2. 여러 페이지 접속
3. 웹 앱 종료
4. 1-3 과정 반복하여 카운터 연속 증가 확인

## 주의사항
- 파일 I/O 예외 처리
- 문자 인코딩 (UTF-8) 주의
- ServletContext 동시성 고려
- 파일 경로 정확히 지정

```java
package com.nhnacademy.study.filter;

import com.nhnacademy.study.CounterUtils;
import jakarta.servlet.*;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class CounterFilter implements Filter {  // jakarta.servlet.Filter 구현
    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        // 여기서는 ServletRequest의 getServletContext() 메소드 사용 가능
        CounterUtils.increaseCounter(servletRequest.getServletContext());
        filterChain.doFilter(servletRequest, servletResponse);
        log.error("counter:{}", servletRequest.getServletContext().getAttribute("counter"));
    }
}
```

Session 통계
현재 서버에 생성된 세션의 갯수를 로그로 출력

HttpSessionListener interface 구현
세션수 : java.util.concurrent.atomic.AtomicInteger 활용

sessionCreated() : 세션수++

sessionDestroyed() : 세션수–

AtomicInteger 란?
원자성을 보장하는 Interger를 의미합니다.

Multi-Thread 환경에서의 동기화 문제를 별도의 synchronized 키워드없이 사용할 수 있다.

```java
package com.nhnacademy.study.listener;

import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class SessionListener implements HttpSessionListener {
    private final AtomicInteger atomicInteger = new AtomicInteger();

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSessionListener.super.sessionCreated(se);
        int sessionCounter = atomicInteger.incrementAndGet();
        log.error("session-counter:{}", sessionCounter);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSessionListener.super.sessionDestroyed(se);
        int sessionCounter = atomicInteger.decrementAndGet();
        log.error("session-counter:{}", sessionCounter);
    }

}
```