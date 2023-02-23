package com.example.project.service.serviceImpl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.project.dto.LoginUser;
import com.example.project.entity.User;
import com.example.project.mapper.MenuMapper;
import com.example.project.service.MenuService;
import com.example.project.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Resource
    UserService userService;
    @Resource
    MenuMapper menuMapper;
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        LambdaQueryWrapper<User> query = new LambdaQueryWrapper<User>();
        query.eq(User::getUserName,userName);
        User user = userService.getOne(query);
        if(Objects.isNull(user)){
            throw new RuntimeException("用户名错误");
        }
        List<String> permsList = menuMapper.selectPermsByUserId(user.getId());
//        ArrayList<String> list = new ArrayList<>(Arrays.asList("test"));
        return new LoginUser(user,permsList);
    }
}

