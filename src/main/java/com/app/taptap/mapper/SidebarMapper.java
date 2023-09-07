package com.app.taptap.mapper;

import com.app.taptap.pojo.*;
import org.glassfish.hk2.api.Rank;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SidebarMapper {
    List<Game> getTenHotGames();//拿出游戏头像、评分、名字和类型，通过rankscore拿前十
    List<TagsResponse> get20TagsWithGameIDByGoodCount(String gameID);//通过游戏Id搜索出与它相关的tag点赞前20的帖子
    List<Game> get10GamesByGameSearchCount();//拿取搜索数前10的游戏id和名字
    Game getSingleGameInfo(String gameID);//拿取游戏头像、名字、id和game_score
}
