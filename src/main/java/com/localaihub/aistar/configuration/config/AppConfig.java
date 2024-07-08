//package com.localaihub.aistar.config;
//
//import org.springframework.ai.chat.client.ChatClient;
//import org.springframework.ai.embedding.EmbeddingModel;
//import org.springframework.ai.ollama.OllamaEmbeddingModel;
//import org.springframework.ai.ollama.api.OllamaApi;
//import org.springframework.ai.ollama.api.OllamaOptions;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * @author Jiaxing Jiang
// * @version 0.1.0-SNAPSHOT
// * @date 2024/7/1 07:36
// */
//@Configuration
//class Config {
//
//    @Bean
//    ChatClient chatClient(ChatClient.Builder builder) {
//        return builder.defaultSystem("用中文交流，你是一个万能的聊天机器人，用详细的解释回答问题")
//                .build();
//    }
//
//    @Bean
//    public OllamaOptions ollamaOptions() {
//        return new OllamaOptions()
//                .withTemperature(0.8f)
//                .withFormat("json")
//                .withModel("qwen2");
//
//    }
////    @Bean
////    public OllamaEmbeddingModel embeddingModel(OllamaApi ollamaApi){
////        return new OllamaEmbeddingModel(ollamaApi);
////    }
//
//}