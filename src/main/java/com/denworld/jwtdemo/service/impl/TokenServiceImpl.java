package com.denworld.jwtdemo.service.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.denworld.jwtdemo.entity.LoginUser;
import com.denworld.jwtdemo.entity.Token;
import com.denworld.jwtdemo.service.TokenService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author ywang Email:denworld@gmail.com
 * @since 2023/03/08 15:03
 */
@Service
@Slf4j
public class TokenServiceImpl implements TokenService {

    private static final String TOKEN_KEY = "TOKEN_KEY";
    private static final String secretKey = "abcdefghi";
    private static final long expireSeconds = 3600*1000;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Token saveToken(LoginUser loginUser) {

        loginUser.setTokenKey(UUID.randomUUID().toString());
        // 将用户信息存入redis
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(loginUser.getLoginTime() + expireSeconds);
        // 根据uuid将loginUser缓存
        redisTemplate.boundValueOps(loginUser.getTokenKey())
                     .set(loginUser, expireSeconds, TimeUnit.SECONDS);
        // 使用jwt生成token
        String jwtToken = createJWTToken(loginUser);
        return new Token(jwtToken, loginUser.getLoginTime());
    }

    @Override
    public LoginUser getLoginUser(String jwtToken) {

        String uuid = getUUIDFromJWT(jwtToken);
        if (uuid != null) {
            return (LoginUser) redisTemplate.boundValueOps(uuid).get();
        }
        return null;
    }

    private String getUUIDFromJWT(String jwtToken) {

        if (StrUtil.isBlank(jwtToken)) {
            return null;
        }

        try {
            Map<String, Object> jwtClaims = Jwts.parser()
                                                .setSigningKey(secretKey)
                                                .parseClaimsJws(jwtToken)
                                                .getBody();
            return MapUtil.getStr(jwtClaims, TOKEN_KEY);
        } catch (ExpiredJwtException e) {
            log.error("{}已过期", jwtToken);
        } catch (Exception e) {
            log.error("{}",e.getMessage());
        }
        return null;
    }

    private String createJWTToken(LoginUser loginUser) {

        Map<String, Object> claims = new HashMap<>();
        claims.put(TOKEN_KEY, loginUser.getTokenKey());
        return Jwts.builder()
                   .setClaims(claims)
                   .signWith(SignatureAlgorithm.HS256, secretKey)
                   .compact();
    }

}
