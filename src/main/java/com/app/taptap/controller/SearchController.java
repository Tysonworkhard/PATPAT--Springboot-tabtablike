package com.app.taptap.controller;

import com.app.taptap.mapper.SearchMapper;
import com.app.taptap.service.SearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Tag(name = "SearchController", description = "搜索接口")
@Controller
@RequestMapping("/Search")
public class SearchController {
    @Resource
    private SearchService searchService;
    @Resource
    private SearchMapper searchMapper;

    @Operation(summary = "游戏搜索接口",description = "无需登录访问")
    @RequestMapping(value = "/getGamesInfo", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> searchGames(@RequestParam("gameName") @Parameter(description = "游戏名字") @Valid @Schema(description = "游戏名字") @NotBlank(message = "游戏名不能为空") String gameName){
        return ResponseEntity.ok().body(searchService.searchGameByGameName(gameName));
    }

    @Operation(summary = "对应游戏搜索数量加1接口",description = "无需登录访问")
    @RequestMapping(value = "/addGameSearchCount", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> addGameSearchCountByGameName(@RequestParam("gameName") @Parameter(description = "游戏名字") @Valid @Schema(description = "游戏名字") @NotBlank(message = "游戏名不能为空") String gameName){
        if (searchMapper.updateGameSearchCount(gameName) != 0){
            return ResponseEntity.ok().body("成功");
        }else {
            return ResponseEntity.badRequest().body("失败");
        }
    }
}
