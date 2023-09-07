package com.app.taptap.service;

import com.app.taptap.mapper.MainPageMapper;
import com.app.taptap.pojo.Game;
import com.app.taptap.pojo.RankAllGame;
import com.app.taptap.utils.DateChange;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
@Slf4j
public class MainPageService {
    @Resource
    private MainPageMapper mainPageMapper;
    //对数组按照ranknum排序
    public List<Game> sortGameByRankNum(List<RankAllGame> rankAllGame){
        //使用自定义比较器进行比较
        rankAllGame.sort(new RankScoreComparator());
        log.debug("rank第一、第二名结果--->"+rankAllGame.get(0).getRankScore());

        //转化数据类型
        List<Game> games = new ArrayList<>();
        for(RankAllGame rankAllGame_temp:rankAllGame){
            games.add(new Game(rankAllGame_temp.getGameID(),rankAllGame_temp.getProfile(),rankAllGame_temp.getGameName(),rankAllGame_temp.getScore(),rankAllGame_temp.getTag(), rankAllGame_temp.getBackground()));
        }

        return games;
    }

    public List<Game> sortGameByDate(){
        List<Game> games =  mainPageMapper.getAllGamesDateAndGames();
        DateChange dateChange = new DateChange();
        int cnt = 0;
        for(Game game:games){
            games.get(cnt).setTimeSelector(dateChange.parseDate(game.getGameDate()));
            cnt++;
        }
        Date currentTime = new Date();

        // 计算一个月前的时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentTime);
        calendar.add(Calendar.MONTH, -1);
        Date oneMonthAgo = calendar.getTime();

        List<Game> selectedGames = new ArrayList<>();

        //筛选在一个月内的游戏
        for(Game game:games){
            if(game.getTimeSelector().after(oneMonthAgo) && game.getTimeSelector().before(currentTime)){
                selectedGames.add(game);
            }
        }

        //按照时间排序
        selectedGames.sort(new TimeComparator(currentTime));

        return selectedGames;
    }

    public List<Game> sortGamesByAllRank(){
        List<RankAllGame> rankAllGames = mainPageMapper.getAllHotGames();
        //口碑计算公式：首先得是热门，再赋权值给game评分
        for (int i = 0; i < rankAllGames.size(); i++){
            rankAllGames.get(i).setRankScore(rankAllGames.get(i).getRankScore() * rankAllGames.get(i).getScore());//计算新的权值
        }
        return sortGameByRankNum(rankAllGames);
    }

    //定义比较器
    public class RankScoreComparator implements Comparator<RankAllGame> {
        @Override
        public int compare(RankAllGame game1, RankAllGame game2) {
            // 根据 rankNum 进行降序排序
            return Float.compare(game2.getRankScore(), game1.getRankScore());
        }
    }

    public class TimeComparator implements Comparator<Game> {
        private Date currentTime;

        public TimeComparator(Date currentTime) {
            this.currentTime = currentTime;
        }

        @Override
        public int compare(Game game1, Game game2) {
            long diff1 = Math.abs(currentTime.getTime() - game1.getTimeSelector().getTime());
            long diff2 = Math.abs(currentTime.getTime() - game2.getTimeSelector().getTime());

            return Long.compare(diff1, diff2);
        }
    }
}
