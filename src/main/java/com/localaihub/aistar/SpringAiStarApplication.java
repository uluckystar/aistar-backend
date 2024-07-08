package com.localaihub.aistar;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.localaihub.aistar.module.*.mapper")
public class SpringAiStarApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringAiStarApplication.class, args);
    }

}
