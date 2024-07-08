package com.localaihub.aistar.module.user.repository;

import com.localaihub.aistar.module.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/7/9 05:56
 */

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}