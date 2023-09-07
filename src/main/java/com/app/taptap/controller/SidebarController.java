package com.app.taptap.controller;

import com.app.taptap.mapper.SidebarMapper;
import com.app.taptap.service.SidebarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.concurrent.PriorityBlockingQueue;

@Tag(name = "SidebarController", description = "侧边栏接口")
@Controller
@RequestMapping("/Sidebar")
public class SidebarController {
    @Resource
    private SidebarMapper sidebarMapper;
    @Resource
    private SidebarService sidebarService;

    @Operation(summary = "侧边栏热门游戏接口",description = "无需登录访问")
    @RequestMapping(value = "/hotGames", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> sidebarHotGames(){
        return ResponseEntity.ok().body(sidebarMapper.getTenHotGames());
    }

    @Operation(summary = "社区侧边栏拿取游戏信息接口",description = "无需登录访问")
    @RequestMapping(value = "/getSimpleGameInfo", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> sidebarGetGameInfo(@RequestParam("gameID") @Parameter(description = "游戏ID") @Valid @Schema(description = "游戏ID") @NotBlank(message = "id不能为空") String gameID){
        return ResponseEntity.ok().body(sidebarMapper.getSingleGameInfo(gameID));
    }

    @Operation(summary = "侧边栏帖子相关推荐接口",description = "无需登录访问")
    @RequestMapping(value = "/proposedTags", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> sidebarProposedTags(@RequestParam("gameID") @Parameter(description = "游戏ID") @Valid @Schema(description = "游戏ID") @NotBlank(message = "id不能为空") String gameID){
        return ResponseEntity.ok().body(sidebarService.sort10ProposedTags(gameID));
    }

    @Operation(summary = "侧边栏热搜榜接口",description = "无需登录访问")
    @RequestMapping(value = "/HotSearch", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> sidebarHotSearchTags(){
        return ResponseEntity.ok().body(sidebarMapper.get10GamesByGameSearchCount());
    }
}
