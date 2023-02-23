package com.example.project.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.project.entity.Menu;
import com.example.project.mapper.MenuMapper;
import com.example.project.service.MenuService;
import org.springframework.stereotype.Service;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
}
