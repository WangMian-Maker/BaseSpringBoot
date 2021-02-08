package com.she.said.service.impl;

import com.she.said.entity.User;
import com.she.said.repository.UserRepository;
import com.she.said.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 小作坊王老板
 * @date 2021-02-08 18:06:32
 * @description to do
 */
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User findByUserName(String username) {
        return repository.findByUsername(username);
    }
}
