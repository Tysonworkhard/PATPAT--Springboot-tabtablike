package com.app.taptap.mapper;

import com.app.taptap.pojo.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunityMapper {
    //7.1 18.01 点赞数和浏览数也拿出来
    List<TagsResponse> selectTagsWithUserCount();//拿取所有帖子以及对应游戏的有关信息（具体的见ui），同时需要对其的关注人数进行统计，放回的东西时继承于game和tag的所以这两个里面的变量都可以用__三个count
    List<TagsResponse> selectTagsByUID(String UID);//拿取对应用户的UID关注的帖子，具体拿什么见UI
    List<TagsResponse> selectTagsWriteByUser(String UID);//拿取对应用户UID写的帖子，拿取的内容同第一条

    //7.1 18.36
    int insertTagsByUID(Tag tag, String UID);//用户发布新帖子,注意图片是list的形式注意foreach的使用
    //getUserAllGame在个人信息接口里面 复用
    List<Game> getAllGames_simple();//拿取所有游戏，只需要头像、gameID和名字
    //优化是加上时间以供排序

    //7.1 18.46 评论
    int insertCommentsByUID(Comment comment, String UID);//用户发布评论，有图片的，注意图片是list要小心

    //
    int updateScanCount(String TagID);//浏览
    int updateGoodCount(String TagID);//点赞
    int updateCollectRelative(String TagID, String UID);//建立uid和tag的联系

    User getUserInfo_simple(String UID);

    //7.2 11.00
    TagsResponse getTagByTagID(String tagID);//帖子的所有内容，只需要用户的用户ID
    List<CommentResponse> getCommentByTagID(String tagID);//拿取帖子的评论

    //7.2 14.20
    Tag getGoodCount(String tagID);//拿取点赞数
    Tag getScanCount(String tagID);//拿取浏览数

    //7.3
    int updateTagPictureAddress(String tagID,String PictureAddresses);
    int updateCommentPictureAddress(String commentID, String PictureAddresses);

    //7.4
    User isCollectRelative(String tagID, String UID);//是否收藏，是，返回uid，反之null
    int deleteCollectRelative(String tagID, String UID);//取消收藏
    User isGood(String tagID, String UID);//是否点赞，是，返回uid，反之null
    int insertGoodRelative(String tagID, String UID);//建立用户和点赞的关系
    int deleteGoodRelative(String tagID, String UID);//取消收藏
    int goodCountDown1(String tagID);//d点赞数减1
}
