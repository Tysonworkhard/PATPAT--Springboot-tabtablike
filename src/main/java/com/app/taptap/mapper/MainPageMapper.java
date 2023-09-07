package com.app.taptap.mapper;

import com.app.taptap.pojo.Game;
import com.app.taptap.pojo.RankAllGame;
import com.app.taptap.pojo.Tag;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MainPageMapper {
    //7.1 主页接口
    List<Game> getRecentlyGames();//通过游戏的日期挑选出最新的三个给我，三个就可以了，要拿出头像、id，游戏名字
    List<RankAllGame> getAllHotGames();//拿取所有游戏rank_type为热门榜的rank_num(就是分数)还有游戏的头像id等等有用信息,要注意这里需要把game_score拿出来——口碑和热门
    List<RankAllGame> getAllReservationGames();//拿取所有游戏rank_type为预约榜的rank_num(就是分数)还有游戏的头像id等等有用信息——预约
    List<Game> getAllGamesDateAndGames();//拿取所有游戏，要时间，上面的可以不要时间——新游

    //上面的接口加上游戏评分score和tag游戏标签出来 7/1 16.07
}
