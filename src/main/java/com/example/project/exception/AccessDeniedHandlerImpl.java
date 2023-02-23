package com.example.project.exception;

import com.alibaba.fastjson.JSON;
import com.example.project.dto.ResponseResult;
import com.example.project.enums.AppHttpCodeEnum;
import com.example.project.util.ServletUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ResponseResult errorResult = ResponseResult.errorResult(AppHttpCodeEnum.ACCESS_ERROR);
        String jsonString = JSON.toJSONString(errorResult);
        ServletUtils.renderString(response,jsonString);
    }
}
