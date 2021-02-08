package com.she.said.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 小作坊王老板
 * @date 2021-02-08 18:50:29
 * @description to do
 */

@RestController
@RequestMapping
public class Test {
    @GetMapping("/login")
    public String test(){
        return "hello";
    }
}
