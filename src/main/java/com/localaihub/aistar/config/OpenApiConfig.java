//package com.localaihub.aistar.config;
//
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import io.swagger.v3.oas.models.OpenAPI;
//import io.swagger.v3.oas.models.info.Info;
//import io.swagger.v3.oas.models.servers.Server;
//
///**
// * @author Jiaxing Jiang
// * @version 0.1.0-SNAPSHOT
// * @date 2024/7/4 23:40
// */
//@Configuration
//public class OpenApiConfig {
//    @Bean
//    public OpenAPI customOpenAPI() {
//        return new OpenAPI()
//                .info(new Info()
//                        .title("Chat API")
//                        .version("1.0.0")
//                        .description("This is a sample Chat API"))
//                .addServersItem(new Server().url("http://localhost:8080"));
//    }
//}