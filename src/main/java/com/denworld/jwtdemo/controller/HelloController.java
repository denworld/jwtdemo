package com.denworld.jwtdemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ywang Email:denworld@gmail.com
 * @since 2023/03/08 12:42
 */
@RestController
public class HelloController {
    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    @GetMapping("/authHello")
    public String authHello(){
        return "authHello";
    }
}
