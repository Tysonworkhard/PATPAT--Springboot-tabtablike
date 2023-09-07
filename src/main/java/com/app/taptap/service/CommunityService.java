package com.app.taptap.service;

import com.app.taptap.mapper.CommunityMapper;
import com.app.taptap.mapper.PersonInfoMapper;
import com.app.taptap.mapper.SidebarMapper;
import com.app.taptap.pojo.*;
import com.app.taptap.utils.GenerateUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
public class CommunityService {
    @Resource
    private CommunityMapper communityMapper;
    @Resource
    private ImageService imageService;
    //推荐算法——三因素加权
    public List<TagsResponse> useProposeGetTags(){
        //获取三因素——点赞收藏+浏览
        List<TagsResponse> tags = communityMapper.selectTagsWithUserCount();
        //加权352
        for (TagsResponse tag : tags) {
            tag.setProposedMetrics(0.3 * tag.getGood() +
                    0.5 * tag.getUserCount() + 0.2 * tag.getScanCount());
        }

        //按从高到低排序
        tags.sort(new proposedMetricsComparator());

        List<String> UIDs = new ArrayList<>();
        for (TagsResponse tag : tags){
            UIDs.add(tag.getUserID());
        }
        //赋予帖子用户信息
        List<User> users = new ArrayList<>();
        for (String UID : UIDs){
            users.add(communityMapper.getUserInfo_simple(UID));
        }

        for (int i = 0;i < users.size(); i++){
            tags.get(i).setUsername(users.get(i).getUsername());
            tags.get(i).setUserProfile(imageService.changePictureAddress(users.get(i).getProfile()));
        }
        return tags;
    }

    //获取用户关注的帖子,默认自己写的帖子置顶
    public List<TagsResponse> userCollectionTags(String UID){
        List<TagsResponse> tagsResponses = communityMapper.selectTagsWriteByUser(UID);
        tagsResponses.addAll(communityMapper.selectTagsByUID(UID));

        List<String> UIDs = new ArrayList<>();
        for (TagsResponse tag : tagsResponses){
            UIDs.add(tag.getUserID());
        }
        //赋予帖子用户信息
        List<User> users = new ArrayList<>();
        for (String UID_temp : UIDs){
            users.add(communityMapper.getUserInfo_simple(UID_temp));
        }
        for (int i = 0;i < users.size(); i++){
            tagsResponses.get(i).setUsername(users.get(i).getUsername());
            tagsResponses.get(i).setUserProfile(users.get(i).getProfile());
        }
        return tagsResponses;
    }
    //存储发布帖子
    public String isStoreTagByUser(String UID, SubmitTagRequest submitTagRequest) throws IOException {
        GenerateUID generateUID = new GenerateUID();
        //生成帖子id
        String TagID = generateUID.generateUid();
        Tag tag = new Tag();
        //存储图片并且转化存储地址;
//        List<String> pictureAddresses = new ArrayList<>();
//        for(MultipartFile file : pictureFiles){
//            pictureAddresses.add(imageService.receiveImage(file));
//        }
//        //封装tag
//        tag.setPictureAddress(pictureAddresses);
        tag.setTagID(TagID);
        tag.setTime(submitTagRequest.getTagDate());
        tag.setTitle(submitTagRequest.getTagTitle());
        tag.setText(submitTagRequest.getTagText());
        tag.setGameID(submitTagRequest.getGameID());

        //存储数据库
        if (communityMapper.insertTagsByUID(tag, UID) != 0){
            return TagID;
        }else {
            return null;
        }

    }

    public List<Game> getAllGames(){
        return communityMapper.getAllGames_simple();
    }

    //拿取帖子
    @Resource
    private SidebarMapper sidebarMapper;
    public TagsResponse getTagByTagID(String TagID){
        TagsResponse tagsResponse = communityMapper.getTagByTagID(TagID);
        //转换图片路径
        List<String> pictures;
        if (!tagsResponse.getPictureAddress().isEmpty()){
            pictures = imageService.changePictureAddresses(tagsResponse.getPictureAddress());
            tagsResponse.setPictureAddress(pictures);
        }
        User user = communityMapper.getUserInfo_simple(tagsResponse.getUserID());
        //补充tag
        if (user.getProfile() != null){
            tagsResponse.setProfile(imageService.changePictureAddress(user.getProfile()));
        }
        tagsResponse.setUsername(user.getUsername());

        tagsResponse.setGameName(sidebarMapper.getSingleGameInfo(tagsResponse.getGameID()).getGameName());
        return tagsResponse;
    }
    @Resource
    private PersonInfoMapper personInfoMapper;
    public List<Game> getUserGames(String UID){
        //复用个人信息接口
        List<Game> games = personInfoMapper.getUserAllGame(UID);
        List<Game> games_result = new ArrayList<>();
        for (Game game : games){
            games_result.add(personInfoMapper.getGameInfo(game));
        }
        return games_result;
    }

    //点赞
    public boolean good(String UID, String tagID){
        if(communityMapper.insertGoodRelative(tagID, UID) != 0){
            return communityMapper.updateGoodCount(tagID) != 0;
        }else {
            return false;
        }
    }
    //是否点赞
    public boolean isGood(String UID, String tagID){
        return communityMapper.isGood(tagID, UID) != null;
    }
    //取消点赞
    public boolean CancelGood(String UID, String tagID){
        if(communityMapper.deleteGoodRelative(tagID, UID) != 0){
            return communityMapper.goodCountDown1(tagID) != 0;
        }else {
            return false;
        }
    }
    //浏览
    public boolean Scan(String tagID){
        return communityMapper.updateScanCount(tagID) != 0;
    }
    //收藏
    public boolean Collection(String tagID, String UID){
        return communityMapper.updateCollectRelative(tagID, UID) != 0;
    }

    //是否收藏
    public boolean isCollection(String tagID, String UID){
        return communityMapper.isCollectRelative(tagID, UID) != null;
    }

    //取消收藏
    public boolean CancelCollection(String tagID, String UID){
        return communityMapper.deleteCollectRelative(tagID, UID) != 0;
    }
    //返回评论
    public List<CommentResponse> getTagComments(String tagID){

        List<CommentResponse> comments = communityMapper.getCommentByTagID(tagID);
         for (CommentResponse comment : comments){
             //转换图片路径
             List<String> pictures = new ArrayList<>();
             if (comment.getPictureAddress().size() != 0){
                 pictures = imageService.changePictureAddresses(comment.getPictureAddress());
             }
             comment.setPictureAddress(pictures);
             User user = communityMapper.getUserInfo_simple(comment.getUID());
             comment.setUsername(user.getUsername());
             if (user.getProfile() != null){
                 comment.setProfile(imageService.changePictureAddress(user.getProfile()));
             }
         }
        return comments;
    }
    //上传评论
    public String isStoreComment(CommentRequest commentRequest, String UID) throws IOException {
        GenerateUID generateUID = new GenerateUID();
        //生成帖子id
        String commentID = generateUID.generateUid();
        Comment comment = new Comment();

//        //存储图片并且转化存储地址
//        List<String> pictureAddresses = new ArrayList<>();
//        for(MultipartFile file : pictureFiles){
//            pictureAddresses.add(imageService.receiveImage(file));
//        }
        comment.setCommentID(commentID);
        comment.setCommentDate(commentRequest.getCommentDate());
        comment.setText(commentRequest.getCommentText());
        comment.setTagID(commentRequest.getTagID());
//        comment.setPictureAddress(pictureAddresses);
        if (communityMapper.insertCommentsByUID(comment, UID) != 0){
            return commentID;
        }else {
            return null;
        }
    }

    //上传tag图片
    public boolean isStoreTagPictures(List<MultipartFile> files, String tagID) throws IOException {
        //存储图片并且转化存储地址
        List<String> pictureAddresses = new ArrayList<>();
        for(MultipartFile file : files){
            pictureAddresses.add(imageService.receiveImage(file));
        }
        return communityMapper.updateTagPictureAddress(tagID, pictureAddresses.get(0)) != 0;
    }

    //上传comment图片
    public boolean isStoreCommentPictures(List<MultipartFile> files, String commentID) throws IOException {
        //存储图片并且转化存储地址
        List<String> pictureAddresses = new ArrayList<>();
        for(MultipartFile file : files){
            pictureAddresses.add(imageService.receiveImage(file));
        }
        return communityMapper.updateCommentPictureAddress(commentID, pictureAddresses.get(0)) != 0;
    }
    //定义比较器
    public class proposedMetricsComparator implements Comparator<TagsResponse> {
        @Override
        public int compare(TagsResponse tag1, TagsResponse tag2) {
            // 根据 rankNum 进行降序排序
            return Double.compare(tag1.getProposedMetrics(), tag2.getProposedMetrics());
        }
    }

}
