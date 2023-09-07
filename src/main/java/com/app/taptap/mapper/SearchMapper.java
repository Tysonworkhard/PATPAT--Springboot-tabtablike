package com.app.taptap.mapper;

import com.app.taptap.pojo.Game;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchMapper {
    List<Game> searchGamesByGameName(String gameName);//初始版本——无模糊搜索
    int updateGameSearchCount(String gameName);//将对应游戏的搜索数量+1
}
