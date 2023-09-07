package com.app.taptap.utils;

import com.app.taptap.mapper.RegisterMapper;
import com.app.taptap.pojo.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;

public class TokenCheck {
    public Claims analyseToken(String token){
        try {
            //解析Token信息
            if(token == null){
                return null;
            }
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(Constants.SECRET_KEY)
                    .build()
                    .parseClaimsJws(token);


            return claimsJws.getBody();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
