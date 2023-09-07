package com.app.taptap.mapper;

import com.app.taptap.pojo.User;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginMapper {
    User getPwdByEmail(String email);//通过email找到密码返回
    User getPwdByPhone(String phoneNumber);//通过电话号码找到密码返回
    User getUserByEmail(String email);//通过邮箱找到个人信息
    User getUserByPhone(String phoneNumber);//通过电话号码找到个人信息
}
