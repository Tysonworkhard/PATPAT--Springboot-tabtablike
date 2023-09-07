package com.app.taptap.pojo;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class LoginRequestByPhoneNum {
    @NotBlank
    @Schema(description = "密码")
    @NotNull(message = "密码不能为空")
    private String password;

    @NotBlank
    @Schema(description = "电话号码")
    @NotNull(message = "电话号码不能为空")
    private String phoneNumber;

    //默认构造函数
    public LoginRequestByPhoneNum(){
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
