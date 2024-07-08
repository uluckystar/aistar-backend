package com.localaihub.aistar.module.user.controller;

import com.localaihub.aistar.module.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.localaihub.aistar.module.user.entity.User;

/**
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/7/9 05:59
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/jpa/{username}")
    public User getUserByUsernameWithJPA(@PathVariable String username) {
        return userService.getUserByUsernameWithJPA(username);
    }

    @GetMapping("/mybatis/{username}")
    public User getUserByUsernameWithMyBatis(@PathVariable String username) {
        return userService.getUserByUsernameWithMyBatis(username);
    }
}
