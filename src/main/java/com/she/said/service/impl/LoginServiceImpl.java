package com.she.said.service.impl;

import com.she.said.config.JwtUtil;
import com.she.said.entity.LoginInfo;
import com.she.said.entity.Message;
import com.she.said.entity.Role;
import com.she.said.entity.User;
import com.she.said.service.LoginService;
import com.she.said.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author 小作坊王老板
 * @date 2021-02-09 16:55:26
 * @description to do
 */
@Service
@Slf4j
public class LoginServiceImpl implements LoginService {
    private final PasswordEncoder passwordEncoder;
    private final RedisTemplate redisTemplate;
    private final UserService userService;

    public LoginServiceImpl(PasswordEncoder passwordEncoder, RedisTemplate redisTemplate, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.redisTemplate = redisTemplate;
        this.userService = userService;
    }

    @Override
    public Message findLoginInfo(String username,String password,boolean isRemember) {
        Message message=new Message();
        try{
            if(redisTemplate.hasKey(username)){
                JwtUtil.logout(username);
            }
            User user=userService.findByUserName(username);
            if(user==null){
                message.setStatus(400);
                message.setInfo("不存在此用户");
                message.setContent(null);
                return message;
            }
            String encodedPassword=user.getPassword();
            if(!passwordEncoder.matches(password,encodedPassword)){
                message.setStatus(400);
                message.setInfo("密码错误");
                message.setContent(null);
                return message;
            }
            Set<Role> roleSet=user.getRoles();
            List<String> roles=new ArrayList<>();
            for(Role role:roleSet){
                roles.add(role.getRoleName());
            }
            String token=JwtUtil.createToken(username,roles,isRemember);
            LoginInfo loginInfo=new LoginInfo();
            loginInfo.setToken(token);
            message.setStatus(200);
            message.setInfo("登陆成功");
            message.setContent(loginInfo);
            return message;
        }
        catch (Exception e){
            message.setStatus(500);
            message.setInfo(e.getMessage());
            message.setContent(null);
            return message;
        }
    }

    @Override
    public Message logout(String username) {
        Message message=new Message();
        try{
            JwtUtil.logout(username);
            message.setStatus(200);
            message.setInfo("登出成功");
            message.setContent(null);
            return message;
        }catch (Exception e){
            message.setStatus(500);
            message.setInfo(e.getMessage());
            message.setContent(null);
            return message;
        }
    }
}
