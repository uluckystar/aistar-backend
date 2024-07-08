package com.localaihub.aistar.util;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

/**
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/7/5 11:49
 */
public class SecurityContextSseEmitter extends SseEmitter {
    private final SecurityContext securityContext;

    public SecurityContextSseEmitter(long timeout) {
        super(timeout);
        this.securityContext = SecurityContextHolder.getContext();
    }

    @Override
    public void send(Object object) {
        SecurityContextHolder.setContext(securityContext);
        try {
            super.send(object);
        } catch (IOException e) {
            // 处理异常
            e.printStackTrace();
        }
    }

    @Override
    public void send(SseEmitter.SseEventBuilder event) {
        SecurityContextHolder.setContext(securityContext);
        try {
            super.send(event);
        } catch (IOException e) {
            // 处理异常
            e.printStackTrace();
        }
    }
}