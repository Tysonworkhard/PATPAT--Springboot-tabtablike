package com.app.taptap.controller;

import com.app.taptap.mapper.ChatMapper;
import com.app.taptap.service.WeChatService;
import com.app.taptap.utils.TokenCheck;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.rmi.server.UID;
import java.util.List;

@Tag(name = "WeChatController", description = "聊天接口")
@Controller
@RequestMapping("/WeChat")
public class WechatController {
    @Resource
    private ChatMapper chatMapper;
    @Resource
    private WeChatService weChatService;

    @Operation(summary = "拿取好友列表和聊天室roomID接口",description = "需登录访问", security = { @SecurityRequirement(name = "Authorization")})
    @RequestMapping(value = "/getFriends", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getFriends(HttpServletRequest request) throws IOException {
        //获取token
        String UID = getUIDFromToken(request);
        return ResponseEntity.ok().body(weChatService.modifyProfile(chatMapper.getFriends(UID)));
    }

    @Operation(summary = "搜索好友名字接口",description = "需登录访问", security = { @SecurityRequirement(name = "Authorization")})
    @RequestMapping(value = "/findFriends", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> findFriends(@RequestParam("username") @Parameter(description = "用户名") @Valid @Schema(description = "用户名") @NotBlank(message = "不能为空") String username) throws IOException {
        //拿取该用户名用户列表
        return ResponseEntity.ok().body(weChatService.modifyProfile_user(chatMapper.searchFriends(username)));
    }

    @Operation(summary = "发送好友申请接口",description = "需登录访问", security = { @SecurityRequirement(name = "Authorization")})
    @RequestMapping(value = "/addFriends_waitAgree", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> addFriends_waitAgree(HttpServletRequest request, @RequestParam("friendID") @Parameter(description = "好友ID") @Valid @Schema(description = "好友ID") @NotBlank(message = "不能为空") String friendID) throws IOException {
        //拿取该用户名用户列表
        //获取token
        String UID = getUIDFromToken(request);
        if (weChatService.isSendApplication(UID, friendID)){
            return ResponseEntity.ok().body("ok");
        }else {
            return ResponseEntity.badRequest().body("false");
        }
    }

    @Operation(summary = "好友申请列表接口",description = "需登录访问", security = { @SecurityRequirement(name = "Authorization")})
    @RequestMapping(value = "/getFriendApplicationList", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getFriendApplicationList(HttpServletRequest request) throws IOException {
        //拿取该用户名用户列表
        //获取token
        String UID = getUIDFromToken(request);
        return ResponseEntity.ok().body(weChatService.modifyProfile_user(chatMapper.getFriendApplicationList(UID)));
    }

    @Operation(summary = "同意好友申请接口",description = "需登录访问", security = { @SecurityRequirement(name = "Authorization")})
    @RequestMapping(value = "/agreeFriendApplication", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> agreeFriendApplication(HttpServletRequest request, @RequestParam("friendID") @Parameter(description = "好友ID") @Valid @Schema(description = "好友ID") @NotBlank(message = "不能为空") String friendID) throws IOException {

        //获取token
        String UID = getUIDFromToken(request);
        if (chatMapper.AgreeToBeFriend(UID, friendID) != 0){
            if (weChatService.agreeFriendApplication(UID, friendID)){
                return ResponseEntity.ok().body("ok");
            }else {
                return ResponseEntity.badRequest().body("false");
            }
        }else {
            return ResponseEntity.badRequest().body("false");
        }
    }

    @Operation(summary = "不同意好友申请接口",description = "需登录访问", security = { @SecurityRequirement(name = "Authorization")})
    @RequestMapping(value = "/disagreeFriendApplication", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> disagreeFriendApplication(HttpServletRequest request, @RequestParam("friendID") @Parameter(description = "好友ID") @Valid @Schema(description = "好友ID") @NotBlank(message = "不能为空") String friendID) throws IOException {
        //获取token
        String UID = getUIDFromToken(request);
        if (chatMapper.disagreeFriendApplication(UID, friendID) != 0){
            return ResponseEntity.ok().body("ok");
        }else {
            return ResponseEntity.badRequest().body("false");
        }
    }

    @Operation(summary = "删除好友申请接口",description = "需登录访问", security = { @SecurityRequirement(name = "Authorization")})
    @RequestMapping(value = "/deleteFriend", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> deleteFriend(HttpServletRequest request, @RequestParam("friendID") @Parameter(description = "好友ID") @Valid @Schema(description = "好友ID") @NotBlank(message = "不能为空") String friendID) throws IOException {
        //获取token
        String UID = getUIDFromToken(request);
        if (chatMapper.deleteFriend(UID, friendID) != 0){
            return ResponseEntity.ok().body("ok");
        }else {
            return ResponseEntity.badRequest().body("false");
        }
    }

    @Operation(summary = "拿取最新的聊天记录申请接口",description = "需登录访问", security = { @SecurityRequirement(name = "Authorization")})
    @RequestMapping(value = "/getLatestMessage", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getLatestMessage(@RequestParam("roomID") @Parameter(description = "roomID") @Valid @Schema(description = "roomID") @NotBlank(message = "不能为空") String roomID) throws IOException {
        return ResponseEntity.ok().body(chatMapper.getLatestMessageByRoomID(roomID));
    }

    @Operation(summary = "拿取全部的聊天记录接口",description = "需登录访问", security = { @SecurityRequirement(name = "Authorization")})
    @RequestMapping(value = "/getAllMessages", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getAllMessages(@RequestParam("roomID") @Parameter(description = "roomID") @Valid @Schema(description = "roomID") @NotBlank(message = "不能为空") String roomID) throws IOException {
        return ResponseEntity.ok().body(weChatService.modifyProfile_message(chatMapper.getAllMessages(roomID)));
    }
    //token的处理
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
