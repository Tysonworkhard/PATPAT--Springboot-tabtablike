package com.app.taptap.pojo;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class LoginRequestByEmail {
    @NotBlank
    @Schema(description = "密码")
    @NotNull(message = "密码不能为空")
    private String password;

    @NotBlank
    @Schema(description = "邮箱")
    @NotNull(message = "邮箱不能为空")
    private String email;

    //默认构造函数
    public LoginRequestByEmail(){

    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
