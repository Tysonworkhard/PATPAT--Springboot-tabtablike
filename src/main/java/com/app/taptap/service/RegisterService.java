package com.app.taptap.service;

import com.app.taptap.mapper.RegisterMapper;
import com.app.taptap.pojo.RegistrationRequest;
import com.app.taptap.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class RegisterService {
    @Resource
    private RegisterMapper registerMapper;
    public boolean isUser(String phoneNumber){
        //判断电话号码是否重复
        User user = registerMapper.isUser(phoneNumber);
        return user != null;
    }

    //插入操作
    public boolean isInsertUser(RegistrationRequest registrationRequest){
        //判断是否插入成功
        return registerMapper.insertUser(registrationRequest) != 0;
    }
}
