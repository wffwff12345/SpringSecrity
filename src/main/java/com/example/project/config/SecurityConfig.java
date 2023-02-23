package com.example.project.config;

import com.example.project.exception.AccessDeniedHandlerImpl;
import com.example.project.filter.ExceptionHandlerFilter;
import com.example.project.filter.JwtRequestFilter;
import com.example.project.service.serviceImpl.UserDetailsServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {
    @Resource
    UserDetailsService userDetailsService;
    @Resource
    private AuthenticationConfiguration authenticationConfiguration;
    @Resource
    AuthenticationEntryPoint authenticationEntryPoint;
    @Resource
    AccessDeniedHandlerImpl accessDeniedHandler;
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public JwtRequestFilter authenticationJwtTokenFilter() {
        return new JwtRequestFilter();
    }
    @Bean
    public ExceptionHandlerFilter exceptionHandlerFilter() {
        return new ExceptionHandlerFilter();
    }

    /**
     * 加密方式
     */
    @Bean
    public static PasswordEncoder PasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 认证管理器，登录的时候参数会传给 authenticationManager
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //关闭csrf
        http.csrf().disable()
                // 允许跨域（也可以不允许，看具体需求）
                .cors().and()
                //不通过Session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // 配置路径是否需要认证
                .authorizeHttpRequests()
                // 对于登录接口 允许匿名访问
                .requestMatchers("/user/login").anonymous()
                // 配置权限
                .requestMatchers("/test")
                .permitAll()
                //.requestMatchers("/hello").hasAuthority("test")
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest()
                .authenticated()
                .and()
                .authenticationManager(authenticationManager(authenticationConfiguration))
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                //此处为添加jwt过滤
                .addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        ;
        http.headers().frameOptions().disable();
        http.addFilterBefore(exceptionHandlerFilter(), CorsFilter.class);
        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler).authenticationEntryPoint(authenticationEntryPoint);
        return http.build();

    }


    /**
     * 跨域资源配置
     */
    /*@Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();

        //此处发现如果不加入自己的项目地址，会被拦截。
        configuration.setAllowedOriginPatterns(List.of("http://localhost:9001"));
        configuration.setAllowedMethods(List.of("GET", "POST", "OPTIONS", "DELETE", "PUT", "PATCH"));
        configuration.setAllowedHeaders(List.of("Access-Control-Allow-Origin", "X-Requested-With", "Origin", "Content-Type", "Accept", "Authorization"));
        configuration.setAllowCredentials(true);
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }*/
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    public static void main(String[] args) {
        String encode = PasswordEncoder().encode( "123");
        System.out.println(encode);
    }
}

