package com.example.project.service;

import com.example.project.dto.LoginUserDto;
import com.example.project.dto.ResponseResult;

public interface LoginUserService {
    public ResponseResult login(LoginUserDto dto);
    public ResponseResult logout();
}
