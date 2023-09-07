package com.app.taptap.mapper;

import com.app.taptap.pojo.Game;
import com.app.taptap.pojo.RankAllGame;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RankMapper {
    //7.2 16.27
    List<RankAllGame> getAllGamesByType(String type);//通过榜单类型查找出对应的所有游戏rank信息，和gamescore、预约人数、收藏该游戏的玩家，用count
    int updateAllRankScore(List<RankAllGame> rankAllGames);//修改rank值

    //7.3 18.00
    List<RankAllGame> getAllGamesByType_detail(String type);//通过榜单类型查找出对应的所有游戏rank信息,和游戏游戏信息，见ui，除此之外game_tag也需要提取出来
    List<Game> getAllGameID();//gameID
    int updateGameScoreByAvgTheCommentScoreByGameID(String gameID);//通过gameID搜索所有其comment评分，对所有评分求平均更新gameScore

    int setNullGameScoreTo5();//默认为5

}

