package com.example.project.exception;

import com.alibaba.fastjson.JSON;
import com.example.project.dto.ResponseResult;
import com.example.project.enums.AppHttpCodeEnum;
import com.example.project.util.ServletUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ResponseResult errorResult = ResponseResult.errorResult(AppHttpCodeEnum.AUTHENTICATION_ERROR);
        String jsonString = JSON.toJSONString(errorResult);
        ServletUtils.renderString(response,jsonString);
    }
}
