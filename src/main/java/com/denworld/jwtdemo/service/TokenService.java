package com.denworld.jwtdemo.service;

import com.denworld.jwtdemo.entity.Token;
import com.denworld.jwtdemo.entity.LoginUser;


/**
 * @author ywang Email:denworld@gmail.com
 * @since 2023/03/08 14:56
 */
public interface TokenService {

    Token saveToken(LoginUser loginUser);

    LoginUser getLoginUser(String jwtToken);

}
