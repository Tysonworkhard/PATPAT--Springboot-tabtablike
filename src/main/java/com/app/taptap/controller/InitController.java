package com.app.taptap.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@Tag(name = "InitController", description = "项目服务器自动部署反馈")
@RestController
@RequestMapping("/Init")
public class InitController {
    @Operation(summary = "初始化反馈",description = "无需登录访问")
    @GetMapping("/first")
    public String first() {
        return "成功";
    }
    //测试
}
