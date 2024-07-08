package com.localaihub.aistar.configuration.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/7/9 05:46
 */
@Configuration
@MapperScan("com.localaihub.aistar.mapper") // 替换为您的 MyBatis-Plus Mapper 包路径
public class MyBatisPlusConfig {
    // 其他配置
}
