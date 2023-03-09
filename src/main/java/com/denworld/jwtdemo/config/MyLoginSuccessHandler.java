package com.denworld.jwtdemo.config;

import com.denworld.jwtdemo.entity.LoginUser;
import com.denworld.jwtdemo.entity.Token;
import com.denworld.jwtdemo.service.TokenService;
import com.denworld.jwtdemo.utils.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ywang Email:denworld@gmail.com
 * @since 2023/03/08 13:57
 */

@Component
public class MyLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Resource
    private TokenService tokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        //使用jwt生成Token
        Token token = tokenService.saveToken(loginUser);
        ResponseUtil.responseJson(response, HttpStatus.OK.value(), token);
    }
}
