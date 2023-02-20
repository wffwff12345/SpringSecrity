package com.example.project.controller;

import com.example.project.util.JwtTokenUtil;
import org.springframework.web.bind.annotation.*;


@RestController
public class accountController {

    @PostMapping("/account/login")
    public  String login(){
        // 如果认证没通过，提示
        // 认证通过返回jwt
        String jwt = JwtTokenUtil.createToken("admin","admin");
        System.out.println("JWT--------"+jwt);
        //放入请求头
        return jwt;

    }

    @GetMapping("/test")
    public String test1(){
        return "200";
    }

    @GetMapping("/test2")
    public String test2(){
        return "200";
    }

}


