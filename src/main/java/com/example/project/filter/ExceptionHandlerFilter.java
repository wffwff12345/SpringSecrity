package com.example.project.filter;

import com.alibaba.fastjson.JSON;
import com.example.project.dto.ResponseResult;
import com.example.project.enums.AppHttpCodeEnum;
import com.example.project.util.ServletUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class ExceptionHandlerFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    try {
        filterChain.doFilter(request,response);
    }catch (Exception e){
        ResponseResult errorResult = ResponseResult.errorResult(AppHttpCodeEnum.USER_LOGOUT);
        String jsonString = JSON.toJSONString(errorResult);
        ServletUtils.renderString(response,jsonString);
    }
    }
}
