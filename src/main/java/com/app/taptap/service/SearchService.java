package com.app.taptap.service;

import com.app.taptap.mapper.SearchMapper;
import com.app.taptap.pojo.Game;
import com.app.taptap.pojo.TagProposed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
public class SearchService {
    @Resource
    private SearchMapper searchMapper;
    public List<Game> searchGameByGameName(String gameName){
       List<Game> games = searchMapper.searchGamesByGameName(gameName);

       games.sort(new gameScoreComparator());

       return games;
    }
    //定义比较器
    public class gameScoreComparator implements Comparator<Game> {
        @Override
        public int compare(Game game1, Game game2) {
            // 根据 rankNum 进行降序排序
            return Float.compare(game1.getScore(), game2.getScore());
        }
    }
}
