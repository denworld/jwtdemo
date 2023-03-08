package com.denworld.jwtdemo.service.impl;

import com.denworld.jwtdemo.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author ywang Email:denworld@gmail.com
 * @since 2023/03/08 13:10
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = null;
        if ("admin".equals(username)) {
            user = new User();
            user.setUsername("admin");
            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
        } else {
            user = null;
        }
        return user;
    }
}
