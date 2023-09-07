package com.app.taptap.controller;

import com.app.taptap.mapper.FindGamesMapper;
import com.app.taptap.mapper.RankMapper;
import com.app.taptap.pojo.CommentRequest;
import com.app.taptap.pojo.CommentResponse;
import com.app.taptap.service.FindGameService;
import com.app.taptap.service.RankService;
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

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Tag(name = "FindGameController", description = "发现游戏接口")
@Controller
@RequestMapping("/Find")
public class FindGameController {
    @Resource
    private RankService rankService;
    @Resource
    private FindGamesMapper findGamesMapper;
    @Resource
    private FindGameService findGameService;
    //admin和free的区别
    @Operation(summary = "拿取标签游戏接口",description = "无需登录访问")
    @RequestMapping(value = "/free/getGamesWithTag", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getGamesWithTag(@RequestParam("tag") @Parameter(description = "标签") @Valid @Schema(description = "标签") @NotBlank(message = "不能为空") String smallTag){
       return ResponseEntity.ok().body(rankService.getSortedGamesByType("hot",smallTag));
    }

    @Operation(summary = "拿取游戏详情接口",description = "无需登录访问")
    @RequestMapping(value = "/free/getGameDetail", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getGameDetail(@RequestParam("gameID") @Parameter(description = "游戏ID") @Valid @Schema(description = "游戏ID") @NotBlank(message = "不能为空") String gameID){
        return ResponseEntity.ok().body(findGamesMapper.getGameDetailInfo(gameID));
    }

    @Operation(summary = "拿取游戏评价接口",description = "无需登录访问")
    @RequestMapping(value = "/free/getGameComment", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getGameComment(@RequestParam("gameID") @Parameter(description = "游戏ID") @Valid @Schema(description = "游戏ID") @NotBlank(message = "不能为空") String gameID){
        return ResponseEntity.ok().body(findGameService.sortCommentResponseByDate(gameID));
    }

    @Operation(summary = "游戏评价接口",description = "需登录访问", security = { @SecurityRequirement(name = "Authorization")})
    @RequestMapping(value = "/free/GameComment", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> GameComment(HttpServletRequest request, @Parameter(description = "评价内容") @Valid @RequestBody CommentRequest commentRequest){
        //获取token和请求体
        String UID = getUIDFromToken(request);
        String commentID = findGameService.isStoreComment(commentRequest, UID);
        if (commentID != null){
            return ResponseEntity.ok().body(commentID);
        }else {
            return ResponseEntity.badRequest().body("失败");
        }
    }

    @Operation(summary = "游戏是否收藏接口",description = "需登录访问", security = { @SecurityRequirement(name = "Authorization")})
    @RequestMapping(value = "/free/isGameCollection", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> isGameCollection(HttpServletRequest request, @RequestParam("gameID") @Parameter(description = "游戏ID") @Valid @Schema(description = "游戏ID") @NotBlank(message = "不能为空") String gameID){
        //获取token和请求体
        String UID = getUIDFromToken(request);
        if (findGamesMapper.isRelative(UID, gameID) == null){
            return ResponseEntity.ok().body("未收藏");
        }else {
            return ResponseEntity.ok().body("已收藏");
        }

    }

    @Operation(summary = "游戏收藏接口",description = "需登录访问", security = { @SecurityRequirement(name = "Authorization")})
    @RequestMapping(value = "/free/GameCollection", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> GameCollection(HttpServletRequest request, @RequestParam("gameID") @Parameter(description = "游戏ID") @Valid @Schema(description = "游戏ID") @NotBlank(message = "不能为空") String gameID){
        //获取token和请求体
        String UID = getUIDFromToken(request);
        if (findGamesMapper.insertGameCollection(UID, gameID) != 0){
            return ResponseEntity.ok().body("收藏成功");
        }else {
            return ResponseEntity.badRequest().body("bad");
        }
    }

    @Operation(summary = "游戏取消收藏接口",description = "需登录访问", security = { @SecurityRequirement(name = "Authorization")})
    @RequestMapping(value = "/free/GameCancelCollection", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> GameCancelCollection(HttpServletRequest request, @RequestParam("gameID") @Parameter(description = "游戏ID") @Valid @Schema(description = "游戏ID") @NotBlank(message = "不能为空") String gameID){
        //获取token和请求体
        String UID = getUIDFromToken(request);
        if (findGamesMapper.deleteGameCollection(UID, gameID) != 0){
            return ResponseEntity.ok().body("ok");
        }else {
            return ResponseEntity.badRequest().body("bad");
        }
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
