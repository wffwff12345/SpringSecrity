package com.example.project.service.serviceImpl;

import com.example.project.dto.LoginUser;
import com.example.project.dto.LoginUserDto;
import com.example.project.dto.ResponseResult;
import com.example.project.service.LoginUserService;
import com.example.project.util.AppJwtUtil;
import com.example.project.util.RedisCache;
import jakarta.annotation.Resource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;

@Service
public class LoginUserServiceImpl implements LoginUserService {

    @Resource
    AuthenticationManager authenticationManager;
    @Resource
    RedisCache redisCache;

    @Override
    public ResponseResult login(LoginUserDto dto) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(dto.getUserName(), dto.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("登录失败!");
        }
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String token = AppJwtUtil.getToken(loginUser.getUser().getId());
        redisCache.setCacheObject("login" + loginUser.getUser().getId(), loginUser);
        HashMap<String, String> map = new HashMap<>();
        map.put("token", token);
        return ResponseResult.okResult(map);
    }

    @Override
    public ResponseResult logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long id = loginUser.getUser().getId();
        redisCache.deleteObject("login"+id);
        return ResponseResult.okResult("退出成功");
    }

}
