package com.localaihub.aistar.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/7/1 06:40
 */
@RestController
class MyController {

    private final ChatClient chatClient;

    public MyController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    /**
     * Java记录（Record）是Java 16中引入的一种新的数据载体，它简化了不可变数据传输对象的创建。
     * 记录是一种不可变的类，它们自动拥有一个构造函数、访问器方法、equals、hashCode和toString方法。
     * 记录非常适合用于存储数据，尤其是当你想要创建一个简单的数据容器时。
     * @param actor
     * @param movies
     */
    record ActorFilms(String actor, List<String> movies) {
    }

    @GetMapping("/ai")
    ChatResponse generation(String userInput) {

        //返回ChatResponse
        ChatResponse chatResponse = chatClient.prompt()
                .user("写一个二分算法")
                .call()
                .chatResponse();

        //返回String
//        String chatResponseString = chatClient.prompt()
//                .user(userInput)
//                .call()
//                .content();

        //返回实体
//        List<ActorFilms> actorFilms = chatClient.prompt()
//                .user("Generate the filmography of 5 movies for Tom Hanks and Bill Murray.")
//                .call()
//                .entity(new ParameterizedTypeReference<List<ActorFilms>>() {
//                });
//        ActorFilms actorFilms2 = chatClient.prompt()
//                .user("Generate the filmography for a random actor.")
//                .call()
//                .entity(ActorFilms.class);

        //返回流式响应
//        Flux<String> output = chatClient.prompt()
//                .user("Tell me a joke")
//                .stream()
//                .content();

        //使用反应式 stream() 方法返回 Java 实体
//        var converter = new BeanOutputConverter<>(new ParameterizedTypeReference<List<ActorFilms>>() {
//        });
//        Flux<String> flux = this.chatClient.prompt()
//                .user(u -> u.text("""
//                            Generate the filmography for a random actor.
//                            {format}
//                          """)
//                        .param("format", converter.getFormat()))
//                .stream()
//                .content();
//        String content = flux.collectList().block().stream().collect(Collectors.joining());
//        List<ActorFilms> actorFilms3 = converter.convert(content);

        return chatResponse;
    }

    @GetMapping("/ai/simple")
    public Map<String, String> completion(@RequestParam(value = "message", defaultValue = "请说-王子请提问-") String message) {
        return Map.of("completion", chatClient.prompt().user(message).call().content());
    }
}
