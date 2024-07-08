package com.localaihub.aistar.module.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.localaihub.aistar.module.user.mapper.UserMapper;
import com.localaihub.aistar.module.user.repository.UserRepository;
import com.localaihub.aistar.module.user.service.UserService;
import com.localaihub.aistar.module.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/7/9 05:49
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return userMapper.selectById(id);
    }

    @Override
    public User getUserByUsernameWithJPA(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User getUserByUsernameWithMyBatis(String username) {
        return userMapper.selectOne(new QueryWrapper<User>().eq("username", username));
    }
}