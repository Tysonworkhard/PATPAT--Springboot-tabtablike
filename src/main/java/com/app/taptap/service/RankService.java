package com.app.taptap.service;

import com.app.taptap.mapper.RankMapper;
import com.app.taptap.pojo.RankAllGame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
@Slf4j
public class RankService {
    @Resource
    private RankMapper rankMapper;

    //返回对应的大小榜条目
    public List<RankAllGame> getSortedGamesByType(String bigTag, String smallTag){
        List<RankAllGame> games = rankMapper.getAllGamesByType_detail(bigTag);

        List<String[]> tags = new ArrayList<>();
        for (RankAllGame game : games){
            tags.add(game.getTag().split("\\s+"));
        }
        //给出对应游戏
        List<RankAllGame> result = new ArrayList<>();
        for (int i = 0; i < tags.size(); i++){
            for(String tag : tags.get(i)){
                if(Objects.equals(tag, smallTag)){
                    result.add(games.get(i));
                    break;
                }
            }
        }

        //排序
        result.sort(new RankScoreComparator());
        return result;
    }

    //定义比较器
    public class RankScoreComparator implements Comparator<RankAllGame> {
        @Override
        public int compare(RankAllGame game1, RankAllGame game2) {
            // 根据 rankNum 进行降序排序
            return Float.compare(game2.getRankScore(), game1.getRankScore());
        }
    }
}
