package com.app.taptap.mapper;

import com.app.taptap.pojo.*;
import org.springframework.stereotype.Repository;

import javax.sql.rowset.serial.SerialStruct;
import java.util.List;

@Repository
public interface FindGamesMapper {
    //初始页面用热门榜的排行榜
    Game getGameDetailInfo(String gameID);//拿取game表的全部信息
    List<CommentResponse> getGameCommentByGameID(String gameID);//通过gameID拿取全部评论，包括用户名和用户头像
    int insertCommentByGameID(Comment comment);//插入gameID和commentID和评分和图片

    //7.3 11.00
    User isRelative(String UID, String gameID);
    int insertGameCollection(String UID, String gameID);//收藏
    int deleteGameCollection(String UID, String gameID);//取消收藏
}
