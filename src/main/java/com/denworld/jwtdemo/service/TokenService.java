package com.denworld.jwtdemo.service;

import com.denworld.jwtdemo.entity.Token;
import com.denworld.jwtdemo.entity.User;

import java.util.UUID;

/**
 * @author ywang Email:denworld@gmail.com
 * @since 2023/03/08 14:56
 */
public interface TokenService {

    public Token saveToken(User user);

    public User getLoginUser(String jwtToken);

}
