package com.localaihub.aistar.module.chat.controller;

import com.localaihub.aistar.common.enums.JwtError;
import com.localaihub.aistar.common.util.JwtTokenUtil;
import com.localaihub.aistar.common.util.SecurityContextSseEmitter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ai.chat.messages.Media;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.OllamaEmbeddingModel;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Ollama API Controller
 * 处理Ollama聊天和嵌入请求
 *
 * @version 0.1.0-SNAPSHOT
 * @since 2024/7/1 08:05
 */
@RestController
@RequestMapping("/ollama")
@CrossOrigin(origins = "http://localhost:3000")
public class OllamaController {
    private static final Logger logger = LoggerFactory.getLogger(OllamaController.class);

    @Autowired
    private OllamaChatModel ollamaChatModel;
    @Autowired
    private OllamaEmbeddingModel ollamaEmbeddingModel;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    /**
     * 生成5个著名科幻著作的名字
     * @return ChatResponse 包含生成的名字列表
     */
    @GetMapping("/llama3")
    public Mono<ChatResponse> generation_llama3() {
        return Mono.fromCallable(() -> ollamaChatModel
                .call(new Prompt("生成 5 个著名科幻著作的名字。")));
    }

    /**
     * 流式生成响应
     * @param message 用户输入的消息
     * @return SseEmitter 流式响应
     */
    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter generateStream(@RequestParam(value = "message", defaultValue = "生成 5 个著名科幻著作的名字。") String message, HttpServletRequest request) {

        // 检查JWT异常
        JwtError jwtError = (JwtError) request.getAttribute("jwtError");
        if (jwtError != null) {
            return createErrorEmitter(jwtError.getMessage());
        }

        // 获取当前安全上下文
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        // 检查用户是否经过认证
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken);

        if (!isAuthenticated) {
            return createErrorEmitter("用户未认证");
        }

        // 用户已认证，继续处理请求
        SecurityContextSseEmitter emitter = new SecurityContextSseEmitter(180_000L);
        System.out.println(securityContext);

        Prompt prompt = new Prompt(new UserMessage(message));
        Flux<ChatResponse> responseStream = ollamaChatModel.stream(prompt)
                .concatWith(Flux.create(emitterFlux -> {
                    Generation endGeneration = new Generation("");
                    ChatResponse endResponse = new ChatResponse(List.of(endGeneration));
                    emitterFlux.next(endResponse); // 发送结束事件
                    emitterFlux.complete();
                }));

        responseStream.subscribe(
                response -> {
                    SecurityContextHolder.setContext(securityContext);
                    emitter.send(SseEmitter.event().data(response));
                },
                error -> emitter.completeWithError(error),
                () -> emitter.complete()
        );
        return emitter;
    }

    private SseEmitter createErrorEmitter(String errorMessage) {
        SseEmitter emitter = new SseEmitter();
        try {
            emitter.send(SseEmitter.event().data("{\"error\": \"" + errorMessage + "\"}"));
            emitter.complete();
        } catch (IOException e) {
            emitter.completeWithError(e);
        }
        return emitter;
    }


    @GetMapping("/stream2")
    public ChatResponse generateStream2(@RequestParam(value = "message", defaultValue = "生成 5 个著名科幻著作的名字。") String message) {
        Prompt prompt = new Prompt(new UserMessage(message));
        return ollamaChatModel.call(prompt);
    }

    /**
     * 解释图片内容
     * @return Flux<ChatResponse> 解释响应
     * @throws IOException 读取图片数据时可能抛出的异常
     */
    @GetMapping("/llava")
    public Flux<ChatResponse> generation_llava() throws IOException {
        byte[] imageData = new ClassPathResource("/static/image/test.jpg").getContentAsByteArray();

        var userMessage = new UserMessage("解释图片",
                List.of(new Media(MimeTypeUtils.IMAGE_PNG, imageData)));

        ChatResponse response = ollamaChatModel.call(
                new Prompt(List.of(userMessage), OllamaOptions.create().withModel("llava")));

        logger.info(response.getResult().getOutput().getContent());
        return Flux.just(response);
    }

    /**
     * 获取文本的嵌入向量
     * @param message 用户输入的文本
     * @return Map 包含嵌入向量
     */
    @GetMapping("/embedding")
    public Mono<Map<String, Object>> embed(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        return Mono.fromCallable(() -> {
            EmbeddingResponse embeddingResponse = this.ollamaEmbeddingModel.embedForResponse(List.of("Hello World", "World is big and salvation is near"));
            return Map.of("embedding", embeddingResponse);
        });
    }
}
