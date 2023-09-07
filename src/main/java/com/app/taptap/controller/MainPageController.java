package com.app.taptap.controller;

import com.app.taptap.mapper.MainPageMapper;
import com.app.taptap.service.MainPageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Tag(name = "MainPageController", description = "主页接口")
@Controller
@RequestMapping("/Main")
public class MainPageController {
    @Resource
    private MainPageMapper mainPageMapper;
    @Resource
    private MainPageService mainPageService;
    //头部轮播图游戏
    @Operation(summary = "头部轮播图接口",description = "无需登录访问")
    @RequestMapping(value = "/HeadCarousel", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getHeadCarousel(){
        return ResponseEntity.ok().body(mainPageMapper.getRecentlyGames());
    }

    //热门榜
    @Operation(summary = "热门榜接口",description = "无需登录访问")
    @RequestMapping(value = "/HotGames", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getHotGames(){
        return ResponseEntity.ok().body(mainPageService.sortGameByRankNum(mainPageMapper.getAllHotGames()));
    }

    //预约榜
    @Operation(summary = "预约榜接口",description = "无需登录访问")
    @RequestMapping(value = "/Reservation", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getReservationGames(){
        return ResponseEntity.ok().body(mainPageService.sortGameByRankNum(mainPageMapper.getAllReservationGames()));
    }

    //新游榜
    @Operation(summary = "新游榜接口",description = "无需登录访问")
    @RequestMapping(value = "/NewGames", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getNewGames(){
        return ResponseEntity.ok().body(mainPageService.sortGameByDate());
    }

    //口碑榜
    @Operation(summary = "口碑榜接口",description = "无需登录访问")
    @RequestMapping(value = "/PublicPraise", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getPublicPraiseGames(){
        return ResponseEntity.ok().body(mainPageService.sortGamesByAllRank());
    }
}
