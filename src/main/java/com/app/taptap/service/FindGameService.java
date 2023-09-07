package com.app.taptap.service;

import com.app.taptap.mapper.FindGamesMapper;
import com.app.taptap.pojo.Comment;
import com.app.taptap.pojo.CommentRequest;
import com.app.taptap.pojo.CommentResponse;
import com.app.taptap.pojo.Game;
import com.app.taptap.utils.DateChange;
import com.app.taptap.utils.GenerateUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;

@Service
@Slf4j
public class FindGameService {
    @Resource
    private FindGamesMapper findGamesMapper;
    @Resource
    private ImageService imageService;
    public List<CommentResponse> sortCommentResponseByDate(String gameID){
        List<CommentResponse> commentResponses = findGamesMapper.getGameCommentByGameID(gameID);
        DateChange dateChange = new DateChange();
        for(CommentResponse commentResponse : commentResponses){
            commentResponse.setTimeSelector(dateChange.parseDate(commentResponse.getCommentDate()));
            commentResponse.setProfile(imageService.changePictureAddress(commentResponse.getProfile()));
        }
        Date currentTime = new Date();
        commentResponses.sort(new TimeComparator(currentTime));
        return commentResponses;
    }

    //上传评价
    public String isStoreComment(CommentRequest commentRequest, String UID){
        GenerateUID generateUID = new GenerateUID();
        //生成帖子id
        String commentID = generateUID.generateUid();
        Comment comment = new Comment();
        comment.setCommentID(commentID);
        comment.setCommentDate(commentRequest.getCommentDate());
        comment.setText(commentRequest.getCommentText());
        comment.setGameID(commentRequest.getGameID());
        comment.setScore(commentRequest.getCommentScore());
        comment.setUID(UID);
//        comment.setPictureAddress(pictureAddresses);
        if (findGamesMapper.insertCommentByGameID(comment) != 0){
            return commentID;
        }else {
            return null;
        }
    }

    public class TimeComparator implements Comparator<CommentResponse> {
        private Date currentTime;

        public TimeComparator(Date currentTime) {
            this.currentTime = currentTime;
        }

        @Override
        public int compare(CommentResponse commentResponse1, CommentResponse commentResponse2) {
            long diff1 = Math.abs(currentTime.getTime() - commentResponse1.getTimeSelector().getTime());
            long diff2 = Math.abs(currentTime.getTime() - commentResponse2.getTimeSelector().getTime());

            return Long.compare(diff1, diff2);
        }
    }
}
