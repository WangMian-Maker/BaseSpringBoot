package com.she.said.service;

import com.she.said.entity.User;
import org.springframework.stereotype.Service;

/**
 * @author 小作坊王老板
 * @date 2021-02-08 18:05:45
 * @description to do
 */
@Service
public interface UserService {
    public User findByUserName(String username);
}
