package com.localaihub.aistar.module.user.service;

import com.localaihub.aistar.module.user.entity.User;

/**
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/7/9 05:52
 */
public interface UserService {
    User createUser(User user);
    User getUserById(Long id);
    User getUserByUsernameWithJPA(String username);
    User getUserByUsernameWithMyBatis(String username);
}