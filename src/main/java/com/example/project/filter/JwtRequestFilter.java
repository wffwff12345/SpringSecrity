package com.example.project.filter;

import com.example.project.dto.LoginUser;
import com.example.project.service.LoginUserService;
import com.example.project.util.AppJwtUtil;
import com.example.project.util.RedisCache;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

public class JwtRequestFilter extends OncePerRequestFilter {

    @Resource
    RedisCache redisCache;
    @Resource
    private LoginUserService userService;


    /**
     * 从 Authorization 标头中，提取令牌
     *
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("token");
        Integer userId = null;
        if(!StringUtils.hasText(token)){
            filterChain.doFilter(request,response);
            return;
        }
        Claims claims = AppJwtUtil.getClaimsBody(token);
        int result = AppJwtUtil.verifyToken(claims);
        if (result == -1 || result ==0) {
            userId =(Integer) claims.get("id");
        }
        LoginUser loginUser = redisCache.getCacheObject("login" + userId);
        if(Objects.isNull(loginUser)){
            throw new RuntimeException("用户已退出登录");
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request,response);
    }
}



