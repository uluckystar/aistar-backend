package com.localaihub.aistar.module.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.localaihub.aistar.module.user.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/7/9 05:47
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    // 可以添加自定义方法
    User selectUserByUsername(@Param("username") String username);
}