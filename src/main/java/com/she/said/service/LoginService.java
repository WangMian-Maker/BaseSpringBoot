package com.she.said.service;

import com.she.said.entity.Message;
import org.springframework.stereotype.Service;

/**
 * @author 小作坊王老板
 * @date 2021-02-09 16:52:27
 * @description to do
 */
@Service
public interface LoginService {
    public Message findLoginInfo(String username,String password,boolean isRemember);
    public Message logout(String username);
}
