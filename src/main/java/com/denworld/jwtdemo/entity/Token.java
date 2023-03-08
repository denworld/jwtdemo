package com.denworld.jwtdemo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ywang Email:denworld@gmail.com
 * @since 2023/03/08 15:26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Token {
    String jwtToken;

    long loginTime;
}
