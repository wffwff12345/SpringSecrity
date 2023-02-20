package com.example.project.util;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.*;

@Configuration
@Component
@Slf4j
public class JwtTokenUtil {

    /**
     * token的头key
     */
    public static final String TOKEN_HEADER = "Authorization";
    /**
     * token前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * token 过期时间 30分钟
     */
    public static final long EXPIRATION = 1000 * 60 * 30;

    /**
     * 加密的key
     */
    private static final String APP_SECRET_KEY = "MDk4ZjZiY2Q0NjIxZDM3M2NhZGU0ZTgzMjYyN2I0ZjY55";

    /**
     * 权限的声明key
     */
    private static final String ROLE_CLAIMS = "role";


    /**
     * 生成token
     *
     * @param username 用户名
     * @param role 用户角色
     * @return token
     */
    public static String createToken(String username, String role) {

        Map<String, Object> map = new HashMap<>();
        map.put(ROLE_CLAIMS, role);
        String token = Jwts
                .builder()
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))  //签发时间
                .setSubject(username)
                .setSubject("system")  //说明
                .setIssuer("cl") //签发者信息
                .setAudience("app")  //接收用户
                .setIssuedAt(new Date())
                .compressWith(CompressionCodecs.GZIP)  //数据压缩方式
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .setClaims(map)
                .claim("username", username)
                .signWith(SignatureAlgorithm.HS512, generalKey())
                .compact();
        return TOKEN_PREFIX +token;
    }


    /**
     * 获取当前登录用户用户名
     *
     * @param token
     * @return
     */
    public static String getUsername(String token) {
        Claims claims = Jwts.parser().setSigningKey(generalKey()).parseClaimsJws(token).getBody();
        return claims.get("username").toString();
    }

    /**
     * 获取当前登录用户角色
     *
     * @param token
     * @return
     */
    public static String getUserRole(String token) {
        Claims claims = Jwts.parser().setSigningKey(generalKey()).parseClaimsJws(token).getBody();
        return claims.get("rol").toString();
    }


    /**
     * 检查token是否过期
     *
     * @param  token token
     * @return boolean
     */
    public static boolean isExpiration(String token) {
        Claims claims = Jwts.parser().setSigningKey(generalKey()).parseClaimsJws(token).getBody();
        return claims.getExpiration().before(new Date());
    }


    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(generalKey()).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }
    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.getEncoder().encode(APP_SECRET_KEY.getBytes());
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }
    public String getUsernameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(generalKey()).parseClaimsJws(token).getBody().getSubject();
    }

}

