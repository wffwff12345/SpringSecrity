package com.example.project.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@MapperScan("com.example.project.mapper")
public class InitConfig {
}
