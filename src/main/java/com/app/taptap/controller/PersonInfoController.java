package com.app.taptap.controller;


import com.app.taptap.mapper.PersonInfoMapper;
import com.app.taptap.pojo.Game;
import com.app.taptap.pojo.PersonInfoChangeRequest;
import com.app.taptap.pojo.PersonInfoResponse;
import com.app.taptap.pojo.User;
import com.app.taptap.service.ImageService;
import com.app.taptap.utils.TokenCheck;
import idworker.Id;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Tag(name = "PersonInfoController", description = "个人信息接口")
@Controller
@RequestMapping("/PersonInfo")
public class PersonInfoController {
    @Resource
    private ImageService imageService;
    @Resource
    private PersonInfoMapper personInfoMapper;//个人信息接口
    //拿取个人信息接口
    @Operation(summary = "个人信息拿取接口",description = "需登录访问",security = { @SecurityRequirement(name = "Authorization")})
    @RequestMapping(value = "/getPersonInfo", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<PersonInfoResponse> getUserInfo(HttpServletRequest request){
        String token = null;
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // 提取令牌部分
            token = authorizationHeader.substring("Bearer ".length());
        }else{
            token = authorizationHeader;
        }
        TokenCheck tokenCheck = new TokenCheck();
        Claims claims = tokenCheck.analyseToken(token);
        String UID = claims.getSubject();

        //整合个人信息发送前端
        User userInfo = personInfoMapper.getAllUserInfo(UID);
        userInfo.setProfile(imageService.changePictureAddress(userInfo.getProfile()));
        List<Game> games = personInfoMapper.getUserAllGame(userInfo.getUID());
        List<com.app.taptap.pojo.Tag> tags = personInfoMapper.getUserAllLikeTag(userInfo.getUID());
        List<Game> games_result = new ArrayList<>();
        List<com.app.taptap.pojo.Tag> tag_result = new ArrayList<>();
        if (!games.isEmpty()){
            for (Game game : games){
                games_result.add(personInfoMapper.getGameInfo(game));
            }
        }
        if (!tags.isEmpty()){
            for (com.app.taptap.pojo.Tag tag : tags){
                tag_result.add(personInfoMapper.getTagInfo(tag));
            }
        }
        PersonInfoResponse personInfoResponse = new PersonInfoResponse(userInfo, games_result, tag_result);
        return ResponseEntity.ok().body(personInfoResponse);
    }

    //修改个人信息接口
    @Operation(summary = "个人信息修改接口",description = "需登录访问",security = { @SecurityRequirement(name = "Authorization")})
    @RequestMapping(value = "/updatePersonInfo", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> updateUserInfo(@Parameter(description = "更新User基本信息") @Valid @RequestBody PersonInfoChangeRequest personInfoChangeRequest, HttpServletRequest request) throws IOException {
        //提取UID
        String UID = getUIDFromToken(request);
        //验证电话号码唯一性，并执行修改
        User oldUser = personInfoMapper.getUserUIDByPhoneNum(personInfoChangeRequest.getPhoneNumber());
        if(oldUser.getUID() == null){
            int result = personInfoMapper.updateUserInfoByUID(UID, personInfoChangeRequest);
            if(result != 0){
                return ResponseEntity.ok().body("更新成功");
            }else{
                return ResponseEntity.badRequest().body("更新失败，修改电话号码数据库执行处");
            }//Objects.equals(oldUser.getUID(), UID)不允许null值，被迫写两个
        }else if(Objects.equals(oldUser.getUID(), UID)){
            int result = personInfoMapper.updateUserInfoByUID(UID, personInfoChangeRequest);
            if(result != 0){
                return ResponseEntity.ok().body("更新成功");
            }else{
                return ResponseEntity.badRequest().body("更新失败，修改电话号码数据库执行处");
            }
        }else {
            return ResponseEntity.badRequest().body("该电话号码已被使用");
        }
    }

    //修改图片
    @Operation(summary = "个人信息图片修改接口",description = "需登录访问",security = { @SecurityRequirement(name = "Authorization")})
    @RequestMapping(value = "/updateUseProfileAddress", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> updateUserInfo(@RequestParam("file") @Parameter(description = "图片字节流") @Schema(description = "图片字节流，可以为空") MultipartFile pictureFile, HttpServletRequest request) throws IOException {
        //提取UID
        String UID = getUIDFromToken(request);
        //图片处理
        if (pictureFile.isEmpty()){
            return ResponseEntity.ok().body("空的访问啥呢？");
        }
        //删除原有的图片
        User user = personInfoMapper.getAllUserInfo(UID);
        String deletePath = imageService.changePictureAddress(user.getProfile());
        imageService.deleteFile(deletePath);

        int result = personInfoMapper.updateUserProfile(UID, imageService.receiveImage(pictureFile));
        if (result != 0){
            return ResponseEntity.ok().body("更新成功");
        }else {
            return ResponseEntity.badRequest().body("更新失败");
        }
    }
    public String getUIDFromToken(HttpServletRequest request){
        String token = null;
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // 提取令牌部分
            token = authorizationHeader.substring("Bearer ".length());
        }else{
            token = authorizationHeader;
        }
        TokenCheck tokenCheck = new TokenCheck();
        Claims claims = tokenCheck.analyseToken(token);
        return claims.getSubject();
    }
}
