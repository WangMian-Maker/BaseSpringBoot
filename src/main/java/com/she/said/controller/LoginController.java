package com.she.said.controller;

import com.she.said.entity.Message;
import com.she.said.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 小作坊王老板
 * @date 2021-02-09 17:12:37
 * @description to do
 */
@RestController
public class LoginController {
    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @RequestMapping(value = "/login",method = {RequestMethod.POST,RequestMethod.GET})
    public Message login(@RequestParam("username") String username, @RequestParam("pwd") String password, @RequestParam("isRemember") Boolean isRemember){
        if (isRemember==null) isRemember=false;
        return loginService.findLoginInfo(username,password,isRemember);
    }

    @RequestMapping(value = "/logout",method = {RequestMethod.POST,RequestMethod.GET})
    public Message logout(@RequestParam("username") String username){
        return loginService.logout(username);
    }
}
