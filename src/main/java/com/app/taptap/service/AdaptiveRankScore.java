package com.app.taptap.service;

import com.app.taptap.mapper.RankMapper;
import com.app.taptap.pojo.Game;
import com.app.taptap.pojo.RankAllGame;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.hk2.api.Rank;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class AdaptiveRankScore {
    //基础分数由我们自己评定
    @Resource
    private RankMapper rankMapper;
    @Scheduled(cron = "0 0 0 * * *") // 每天晚上12点执行
    public void adaptiveRankScore(){
        //修改游戏评分
        List<Game> games = rankMapper.getAllGameID();
        for (Game game : games){
            rankMapper.updateGameScoreByAvgTheCommentScoreByGameID(game.getGameID());
        }
        //设定默认值为5
        rankMapper.setNullGameScoreTo5();
        //修改对应榜单的rank分数
        List<RankAllGame> hotGameRank= rankMapper.getAllGamesByType("hot");
        List<RankAllGame> reservationGameRank= rankMapper.getAllGamesByType("reservation");
        List<RankAllGame> playGameRank= rankMapper.getAllGamesByType("play");

        //对热门榜进行操作
        for(RankAllGame rankAllGame : hotGameRank){
            float newRankScore = (float) (rankAllGame.getRankScore() * 0.65 + 0.35 * (rankAllGame.getScore() * rankAllGame.getUserCount()));
            rankAllGame.setRankScore(newRankScore);
        }
        rankMapper.updateAllRankScore(hotGameRank);

        //对预约榜进行操作
        for(RankAllGame rankAllGame : reservationGameRank){
            float newRankScore = (float) (rankAllGame.getRankScore() * 0.65 + 0.35 * (rankAllGame.getScore() * rankAllGame.getReservationCount()));
            rankAllGame.setRankScore(newRankScore);
        }
        rankMapper.updateAllRankScore(reservationGameRank);

        //对热玩榜进行操作
        for(RankAllGame rankAllGame : playGameRank){
            float newRankScore = (float) (rankAllGame.getRankScore() * 0.65 + 0.35 * (rankAllGame.getUserCount()));
            rankAllGame.setRankScore(newRankScore);
        }
        rankMapper.updateAllRankScore(playGameRank);
    }

}
