package com.ruoyi.bsapi.utils;

import com.alibaba.fastjson2.JSON;
import com.ruoyi.bsapi.domain.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class TokenUtils {


    static String secretKey = "mySecretKey";


    /**
     * 生成一个 token
     * @param user
     * @return
     */
    public static String createClientToken(User user) {
        String userString = JSON.toJSONString(user);
        String token = Jwts.builder()
                .setSubject(userString)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
        return token;
    }

}
