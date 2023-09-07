package com.app.taptap.service;

import com.app.taptap.mapper.LoginMapper;
import com.app.taptap.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

@Service
@Slf4j
public class LoginService {
    @Resource
    LoginMapper loginMapper;
    //通过电话号码判断密码
    public boolean UserPwdCheckByPhoneNum(String pwd, String PhoneNum){
        User user = loginMapper.getPwdByPhone(PhoneNum);
        if(user == null){
            return false;
        }
        return Objects.equals(user.getPassword(), pwd);
    }
    //通过邮箱判断密码
    public boolean UserPwdCheckByEmail(String pwd, String Email){
        User user = loginMapper.getPwdByEmail(Email);
        if (user == null){
            return false;
        }
        return Objects.equals(user.getPassword(), pwd);
    }
}
