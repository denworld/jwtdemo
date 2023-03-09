package com.denworld.jwtdemo.config;

import cn.hutool.core.util.StrUtil;
import com.denworld.jwtdemo.entity.LoginUser;
import com.denworld.jwtdemo.service.TokenService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Token过滤器
 */

@Component
public class TokenFilter extends OncePerRequestFilter {

    public static final String TOKEN_KEY = "token";

    @Resource
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = getToken(request);
        if (StrUtil.isNotBlank(token)) {
            //通过JWT解析请求数据中的Token，获取用户数据
            LoginUser loginUser = tokenService.getLoginUser(token);
            if (loginUser != null) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(loginUser,
                        null, loginUser.getAuthorities());
                SecurityContextHolder.getContext()
                                     .setAuthentication(authentication);
            }else{
                SecurityContextHolder.getContext().setAuthentication(null);
            }
        }

        filterChain.doFilter(request, response);
    }

    /**
     * 根据参数或者header获取token
     */
    public static String getToken(HttpServletRequest request) {
        String token = request.getParameter(TOKEN_KEY);
        if (StrUtil.isBlank(token)) {
            token = request.getHeader(TOKEN_KEY);
        }
        return token;
    }

}