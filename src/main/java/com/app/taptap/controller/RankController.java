package com.app.taptap.controller;

import com.app.taptap.mapper.RankMapper;
import com.app.taptap.service.RankService;
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

@Tag(name = "RankController", description = "排行版接口")
@Controller
@RequestMapping("/Rank")
public class RankController {
    @Resource
    private RankService rankService;

    @Operation(summary = "排行榜接口_给我大小榜",description = "无需登录访问")
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> RankDetail(@RequestParam("bigTag") @Parameter(description = "大榜标签") @Valid @Schema(description = "大榜标签") @NotBlank(message = "不能为空") String bigTag, @RequestParam("smallTag") @Parameter(description = "小榜标签") @Valid @Schema(description = "小榜标签") @NotBlank(message = "不能为空") String smallTag){
        return ResponseEntity.ok().body(rankService.getSortedGamesByType(bigTag,smallTag));
    }
}
