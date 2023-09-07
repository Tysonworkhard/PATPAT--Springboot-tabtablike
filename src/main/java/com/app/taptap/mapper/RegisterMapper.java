package com.app.taptap.mapper;

import com.app.taptap.pojo.RegistrationRequest;
import com.app.taptap.pojo.User;
import org.springframework.stereotype.Repository;

@Repository
public interface RegisterMapper {
    User isUser(String phoneNumber);//通过电话号码找人，查看是否重复
    int insertUser(RegistrationRequest registrationRequest);//插入用户
    User getNewUser(String UID);//通过uid找到新用户返回
}
