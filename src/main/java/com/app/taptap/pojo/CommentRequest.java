package com.app.taptap.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public class CommentRequest {
    @NotBlank
    @Schema(description = "发评论的时间形式字符串——2023-07-04 21:39:52")
    @NotNull(message = "不能为空")
    private String commentDate;

    @NotBlank
    @Schema(description = "正文")
    @NotNull(message = "不能为空")
    private String commentText;

    @Schema(description = "帖子的评论还是游戏？2选一填")
    private String tagID;

    @Schema(description = "帖子的评论还是游戏？2选一填")
    private String gameID;

    @Schema(description = "游戏评分")
    private float commentScore;

    private String UID;
    //默认构造函数
    public CommentRequest(){

    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getUID() {
        return UID;
    }

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public String getTagID() {
        return tagID;
    }

    public void setTagID(String tagID) {
        this.tagID = tagID;
    }

    public float getCommentScore() {
        return commentScore;
    }

    public void setCommentScore(float commentScore) {
        this.commentScore = commentScore;
    }

    public void setCommentDate(String commentDate) {
        this.commentDate = commentDate;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public String getCommentDate() {
        return commentDate;
    }

    public String getCommentText() {
        return commentText;
    }
}

