package com.app.taptap.utils;

import com.app.taptap.pojo.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class GenerateTokenByUser {
    // 将过期时间设置为1小时
    Date expiration = new Date(System.currentTimeMillis() + 3600 * 1000 * 24); // 当前时间 + 1小时的毫秒数

    public String generateToken(User user) {
        // 使用secretKeyFor方法创建安全的密钥
        Key key = Constants.SECRET_KEY;

        String token = Jwts.builder()
                .setSubject(user.getUID())
                .setExpiration(expiration)
                .signWith(key)
                .compact();

        return token;
    }
}






