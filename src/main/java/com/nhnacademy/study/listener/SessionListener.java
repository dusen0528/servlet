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