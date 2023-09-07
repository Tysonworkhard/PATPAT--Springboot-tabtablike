package com.app.taptap.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

public class PersonInfoChangeRequest {
    @NotBlank
    @Schema(description = "用户名")
    @NotNull(message = "用户名不能为空")
    private String username;

    @NotBlank
    @Schema(description = "邮箱")
    @NotNull(message = "邮箱不能为空")
    private String email;

    @NotBlank
    @Schema(description = "电话号码")
    @NotNull(message = "电话号码不能为空")
    private String phoneNumber;//唯一

    private String profile;
    //默认构造函数
    public PersonInfoChangeRequest(){

    }

    //get\set

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getProfile() {
        return profile;
    }
}
