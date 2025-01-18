package com.nhnacademy.study;

import jakarta.servlet.ServletContext;

import java.util.Optional;

/**
 * 웹 애플리케이션의 방문자 카운터를 관리하는 유틸리티 클래스입니다.
 * ServletContext의 counter 속성을 증가시키는 기능을 제공합니다.
 */
public class CounterUtils {

    /**
     * 유틸리티 클래스는 인스턴스화할 수 없도록 private 생성자를 가집니다.
     *
     * @throws IllegalStateException 클래스 인스턴스화 시도시 발생
     */
    private CounterUtils() {
        throw new IllegalStateException("Utility Class");
    }

    /**
     * ServletContext에 저장된 counter 값을 1 증가시킵니다.
     * counter 속성이 없는 경우 0부터 시작합니다.
     *
     * <p>사용 예:</p>
     * <pre>
     * ServletContext context = request.getServletContext();
     * CounterUtils.increaseCounter(context);
     * </pre>
     *
     * @param servletContext counter 속성을 포함하고 있는 ServletContext 객체
     */
    public static void increaseCounter(ServletContext servletContext) {
        // counter 값이 없으면 0L을 기본값으로 사용
        Long counter = Optional.ofNullable((Long)servletContext.getAttribute("counter"))
                .orElse(0L);
        // 카운터 1 증가
        counter += 1;
        // 증가된 값을 다시 ServletContext에 저장
        servletContext.setAttribute("counter", counter);
    }
}