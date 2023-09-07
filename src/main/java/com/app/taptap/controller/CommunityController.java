package com.app.taptap.controller;

import com.app.taptap.mapper.CommunityMapper;
import com.app.taptap.pojo.CommentRequest;
import com.app.taptap.pojo.SubmitTagRequest;
import com.app.taptap.service.CommunityService;
import com.app.taptap.utils.TokenCheck;
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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Tag(name = "CommunityController", description = "社区接口")
@Controller
@RequestMapping("/Community")
public class CommunityController {
    @Resource
    private CommunityService communityService;
    @Resource
    private CommunityMapper communityMapper;
    ///Community/free/**这个是不需要登录控制\/Community/admin/**这个需要登陆控制
    @Operation(summary = "社区推荐接口",description = "无需登录访问")
    @RequestMapping(value = "/free/propose", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> communityPropose(){
        return ResponseEntity.ok().body(communityService.useProposeGetTags());
    }

    @Operation(summary = "社区用户关注帖子接口",description = "需登录访问", security = { @SecurityRequirement(name = "Authorization")})
    @RequestMapping(value = "/admin/collectionTags", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> communityCollectionTags(HttpServletRequest request){
        //获取token
        String UID = getUIDFromToken(request);
        return ResponseEntity.ok().body(communityService.userCollectionTags(UID));
    }
    //7.3 @RequestParam("pictureFiles") @Parameter(description = "图片字节流") @Schema(description = "图片字节流，可以为空") List<MultipartFile> pictureFiles
    @Operation(summary = "发布帖子",description = "需登录访问", security = { @SecurityRequirement(name = "Authorization")})
    @RequestMapping(value = "/admin/submitTag", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> communitySubmitTags(HttpServletRequest request, @Parameter(description = "帖子内容") @Valid @RequestBody SubmitTagRequest submitTagRequest) throws IOException {
        //获取token和请求体
        String UID = getUIDFromToken(request);
        //存储tag
        String tagID = communityService.isStoreTagByUser(UID, submitTagRequest);
        if(tagID != null){
            return ResponseEntity.ok().body(tagID);
        }
        return ResponseEntity.badRequest().body("失败");
    }

    @Operation(summary = "选择游戏_所有游戏",description = "需登录访问", security = { @SecurityRequirement(name = "Authorization")})
    @RequestMapping(value = "/admin/AllGames", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> communityGetAllGames(){
        //返回所有图片
        return ResponseEntity.ok().body(communityService.getAllGames());
    }

    @Operation(summary = "选择游戏_我的游戏",description = "需登录访问", security = { @SecurityRequirement(name = "Authorization")})
    @RequestMapping(value = "/admin/MyGames", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> communityGetMyGames(HttpServletRequest request){
        //获取token
        String UID = getUIDFromToken(request);
        //返回我的图片
        return ResponseEntity.ok().body(communityService.getUserGames(UID));
    }
    //7.3 ok
    @Operation(summary = "帖子",description = "不需登录访问")
    @RequestMapping(value = "/free/getTag", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> communityGetTag(@RequestParam("tagID") @Parameter(description = "帖子ID") @Valid @Schema(description = "帖子ID") @NotBlank(message = "id不能为空") String tagID){
        //返回帖子，见UI
        return ResponseEntity.ok().body(communityService.getTagByTagID(tagID));
    }

    //上传图片
    @Operation(summary = "上传帖子图片",description = "需登录访问", security = { @SecurityRequirement(name = "Authorization")})
    @RequestMapping(value = "/admin/tagPicture", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> communityTagPicture(@RequestParam("tag") @Parameter(description = "帖子的ID") @Schema(description = "不可以为空") @NotNull(message = "不可以为空") String TagID, @RequestParam("file") @Parameter(description = "图片字节流") @Schema(description = "图片字节流，可以为空") List<MultipartFile> pictureFiles) throws IOException {
        if (communityService.isStoreTagPictures(pictureFiles, TagID)){
            return ResponseEntity.ok().body("存储成功");
        }else {
            return ResponseEntity.badRequest().body("失败");
        }
    }
    @Operation(summary = "点赞并且返回点赞数量",description = "需登录访问", security = { @SecurityRequirement(name = "Authorization")})
    @RequestMapping(value = "/admin/good", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> communityGetGoodCount(HttpServletRequest request, @RequestParam("tagID") @Parameter(description = "帖子ID") @Valid @Schema(description = "帖子ID") @NotBlank(message = "id不能为空") String tagID){
        //返回点赞数
        //获取token
        String UID = getUIDFromToken(request);
        if(communityService.good(UID, tagID)){
            return ResponseEntity.ok().body(communityMapper.getGoodCount(tagID));
        }else {
            return ResponseEntity.badRequest().body("点赞失败");
        }

    }

    @Operation(summary = "是否点赞",description = "需登录访问", security = { @SecurityRequirement(name = "Authorization")})
    @RequestMapping(value = "/admin/IsGood", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> communityIsGood(HttpServletRequest request, @RequestParam("tagID") @Parameter(description = "帖子ID") @Valid @Schema(description = "帖子ID") @NotBlank(message = "id不能为空") String tagID){
        //返回点赞数
        //获取token
        String UID = getUIDFromToken(request);
        if(communityService.isGood(UID, tagID)){
            return ResponseEntity.ok().body("已点赞");
        }else {
            return ResponseEntity.ok().body("未点赞");
        }

    }

    @Operation(summary = "取消点赞并且返回点赞数量",description = "需登录访问", security = { @SecurityRequirement(name = "Authorization")})
    @RequestMapping(value = "/admin/CancelGood", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> communityCancelGood(HttpServletRequest request, @RequestParam("tagID") @Parameter(description = "帖子ID") @Valid @Schema(description = "帖子ID") @NotBlank(message = "id不能为空") String tagID){
        //返回点赞数
        //获取token
        String UID = getUIDFromToken(request);
        if(communityService.CancelGood(UID, tagID)){
            return ResponseEntity.ok().body(communityMapper.getGoodCount(tagID));
        }else {
            return ResponseEntity.badRequest().body("异常");
        }

    }

    @Operation(summary = "浏览数+1并且返回",description = "无需登录访问")
    @RequestMapping(value = "/free/scan", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> communityGetScanCount(@RequestParam("tagID") @Parameter(description = "帖子ID") @Valid @Schema(description = "帖子ID") @NotBlank(message = "id不能为空") String tagID){
        //返回浏览数
        if(communityService.Scan(tagID)){
            return ResponseEntity.ok().body(communityMapper.getScanCount(tagID));
        }else {
            return ResponseEntity.badRequest().body("获取浏览数失败");
        }

    }

    @Operation(summary = "是否收藏",description = "需登录访问", security = { @SecurityRequirement(name = "Authorization")})
    @RequestMapping(value = "/admin/isCollection", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> communityIsCollection(@RequestParam("tagID") @Parameter(description = "帖子ID") @Valid @Schema(description = "帖子ID") @NotBlank(message = "id不能为空") String tagID, HttpServletRequest request){
        //获取token
        String UID = getUIDFromToken(request);
        //自适应加到用户的收藏
        if(communityService.isCollection(tagID, UID)){
            return ResponseEntity.ok().body("已收藏");
        }else {
            return ResponseEntity.ok().body("未收藏");
        }
    }

    @Operation(summary = "收藏",description = "需登录访问", security = { @SecurityRequirement(name = "Authorization")})
    @RequestMapping(value = "/admin/collection", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> communityCollection(@RequestParam("tagID") @Parameter(description = "帖子ID") @Valid @Schema(description = "帖子ID") @NotBlank(message = "id不能为空") String tagID, HttpServletRequest request){
        //获取token
        String UID = getUIDFromToken(request);
        //自适应加到用户的收藏
        if(communityService.Collection(tagID, UID)){
            return ResponseEntity.ok().body("收藏成功");
        }else {
            return ResponseEntity.badRequest().body("收藏失败");
        }
    }

    @Operation(summary = "取消收藏",description = "需登录访问", security = { @SecurityRequirement(name = "Authorization")})
    @RequestMapping(value = "/admin/CancelCollection", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> communityCancelCollection(@RequestParam("tagID") @Parameter(description = "帖子ID") @Valid @Schema(description = "帖子ID") @NotBlank(message = "id不能为空") String tagID, HttpServletRequest request){
        //获取token
        String UID = getUIDFromToken(request);
        //自适应加到用户的收藏
        if(communityService.CancelCollection(tagID, UID)){
            return ResponseEntity.ok().body("取消成功");
        }else {
            return ResponseEntity.badRequest().body("异常");
        }
    }

    //7.3 ok
    @Operation(summary = "评论",description = "需登录访问", security = { @SecurityRequirement(name = "Authorization")})
    @RequestMapping(value = "/admin/comment", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> communityComment(HttpServletRequest request, @Parameter(description = "帖子内容") @Valid @RequestBody CommentRequest commentRequest) throws IOException {
        //获取token和请求体
        String UID = getUIDFromToken(request);
        //上传评论
        String commentID = communityService.isStoreComment(commentRequest, UID);
        if(commentID != null){
            return ResponseEntity.ok().body(commentID);
        }
        return ResponseEntity.badRequest().body("失败");
    }

    //上传图片
    @Operation(summary = "上传评论图片",description = "需登录访问", security = { @SecurityRequirement(name = "Authorization")})
    @RequestMapping(value = "/admin/commentPicture", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> communityCommentPicture(@RequestParam("commentID") @Parameter(description = "评论的ID") @Schema(description = "不可以为空") @NotBlank(message = "不可以为空") String commentID, @RequestParam("file") @Parameter(description = "图片字节流") @Schema(description = "图片字节流，可以为空") List<MultipartFile> pictureFiles) throws IOException {
        if (communityService.isStoreCommentPictures(pictureFiles, commentID)){
            return ResponseEntity.ok().body("存储成功");
        }else {
            return ResponseEntity.badRequest().body("失败");
        }
    }
    //7.3 ok
    @Operation(summary = "返回帖子的评论",description = "无需需登录访问")
    @RequestMapping(value = "/free/getComment", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> communityGetComments(@RequestParam("tagID") @Parameter(description = "帖子ID") @Valid @Schema(description = "帖子ID") @NotBlank(message = "id不能为空") String tagID){
        //上传图片
        return ResponseEntity.ok().body(communityService.getTagComments(tagID));
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
