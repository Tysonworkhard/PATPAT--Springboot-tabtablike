package com.app.taptap.utils;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class Constants {
    public static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public static final String[] SMALL_TAG_CONST;

    static {
        SMALL_TAG_CONST = new String[] {"单机", "动作", "多人", "角色扮演", "休闲", "赛博朋克", "美食", "射击", "二次元", "音乐"};
    }
}