package com.app.taptap.controller;

import com.app.taptap.mapper.LoginMapper;
import com.app.taptap.mapper.RegisterMapper;
import com.app.taptap.pojo.LoginRequestByEmail;
import com.app.taptap.pojo.LoginRequestByPhoneNum;
import com.app.taptap.pojo.RegistrationRequest;
import com.app.taptap.pojo.User;
import com.app.taptap.service.LoginService;
import com.app.taptap.service.RegisterService;
import com.app.taptap.utils.GenerateTokenByUser;
import com.app.taptap.utils.GenerateUID;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.concurrent.PriorityBlockingQueue;

@Tag(name = "RegisterAndLoginController", description = "登录注册接口")
@Controller
@RequestMapping("/RAL")
public class RegisterAndLoginController {
    @Resource
    private RegisterService registerService;//注册服务
    @Resource
    private LoginService loginService;//登录服务
    private String Token;
    @Resource
    private RegisterMapper registerMapper;//注册DB
    @Resource
    private LoginMapper loginMapper;//登录DB
    //注册接口
    @Operation(summary = "注册接口",description = "无需登录访问")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> registerUser(@Parameter(description = "注册请求") @Valid @RequestBody RegistrationRequest request) {
        //如果没有电话号码重复才允许添加
        if(!registerService.isUser(request.getPhoneNumber())){
            //生成UID
            GenerateUID generateUID = new GenerateUID();
            request.setUID(generateUID.generateUid());
            //插入数据库操作
            if(registerService.isInsertUser(request)){
                GenerateTokenByUser generateTokenByUser = new GenerateTokenByUser();
                Token = generateTokenByUser.generateToken(registerMapper.getNewUser(request.getUID()));
                return ResponseEntity.ok().body(Token);
            }else {
                return ResponseEntity.badRequest().body("数据库插入失败！");
            }
        }else{
            //返回错误
            return ResponseEntity.badRequest().body("该用户电话号码已经注册！");
        }
    }

    //登录接口_电话号码
    @Operation(summary = "登录接口——电话号码",description = "无需登录访问")
    @RequestMapping(value = "/loginByPhoneNum", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> userLoginByPhoneNum(@Parameter(description = "登录请求") @Valid @RequestBody LoginRequestByPhoneNum request) {
        //判断逻辑
        if(loginService.UserPwdCheckByPhoneNum(request.getPassword(), request.getPhoneNumber())){
            GenerateTokenByUser generateTokenByUser = new GenerateTokenByUser();
            Token = generateTokenByUser.generateToken(loginMapper.getUserByPhone(request.getPhoneNumber()));
            return ResponseEntity.ok().body(Token);
        }else {
            return ResponseEntity.badRequest().body("电话号码或密码错误");
        }
    }

    //登录接口_邮箱
    @Operation(summary = "登录接口——邮箱",description = "无需登录访问")
    @RequestMapping(value = "/loginByEmail", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> userLoginByEmail(@Parameter(description = "登录请求") @Valid @RequestBody LoginRequestByEmail request) {
        //判断逻辑
        if(loginService.UserPwdCheckByEmail(request.getPassword(), request.getEmail())){
            GenerateTokenByUser generateTokenByUser = new GenerateTokenByUser();
            Token = generateTokenByUser.generateToken(loginMapper.getUserByEmail(request.getEmail()));
            return ResponseEntity.ok().body(Token);
        }else {
            return ResponseEntity.badRequest().body("邮箱或密码错误");
        }
    }
}
