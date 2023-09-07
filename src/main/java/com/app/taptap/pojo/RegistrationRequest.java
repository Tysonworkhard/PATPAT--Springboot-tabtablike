package com.app.taptap.pojo;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class RegistrationRequest {
    @NotBlank
    @Schema(description = "用户名")
    @NotNull(message = "用户名不能为空")
    private String username;

    @NotBlank
    @Schema(description = "密码")
    @NotNull(message = "密码不能为空")
    private String password;

    @NotBlank
    @Schema(description = "电话号码")
    @NotNull(message = "电话号码不能为空")
    private String phoneNumber;
    private String UID;

    public RegistrationRequest() {
        // 默认构造函数
    }

    // 带参数的构造函数
    public RegistrationRequest(String username, String password, String phoneNumber) {
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }
    public RegistrationRequest(String username, String password, String phoneNumber, String UID) {
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.UID = UID;
    }
    //set get方法
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getUID() {
        return UID;
    }
}
