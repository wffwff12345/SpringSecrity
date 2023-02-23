package com.example.project.controller;

import com.example.project.dto.LoginUser;
import com.example.project.dto.LoginUserDto;
import com.example.project.dto.ResponseResult;
import com.example.project.service.LoginUserService;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
public class accountController {
    @Resource
    LoginUserService loginUserService;

    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody LoginUserDto dto) {

        return loginUserService.login(dto);
    }

    @GetMapping("/test")
    public String test1() {
        return "200";
    }

    @GetMapping("/test2")
    public String test2() {
        return "200";
    }

    @GetMapping("/hello")
    @PreAuthorize("hasAuthority('system:users:list')")
    public ResponseResult hello() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser principal = (LoginUser) authentication.getPrincipal();
        System.out.println(principal);
        return ResponseResult.okResult("hello");
    }

    @GetMapping("/user/logout")
    public ResponseResult logout(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser principal = (LoginUser) authentication.getPrincipal();
        System.out.println(principal);
        return loginUserService.logout();
    }
}
