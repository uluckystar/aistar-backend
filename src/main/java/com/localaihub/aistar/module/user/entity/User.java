package com.localaihub.aistar.module.user.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

/**
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/7/9 05:48
 */
@Data
@Entity
@TableName("users") // 对应数据库中的表名
public class User {
    @Id
    @TableId
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
}