package com.app.taptap.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public class SubmitTagRequest {
    @NotBlank
    @Schema(description = "发帖时间形式字符串——2023-07-04 21:39:52")
    @NotNull(message = "不能为空")
    private String tagDate;

    @NotBlank
    @Schema(description = "标题")
    @NotNull(message = "不能为空")
    private String tagTitle;

    @NotBlank
    @Schema(description = "正文")
    @NotNull(message = "不能为空")
    private String tagText;

    @NotBlank
    @Schema(description = "游戏——先做只关联1个的")
    @NotNull(message = "不能为空")
    private String gameID;

    //默认构造函数
    public SubmitTagRequest(){

    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public void setTagDate(String tagDate) {
        this.tagDate = tagDate;
    }

    public void setTagTitle(String tagTitle) {
        this.tagTitle = tagTitle;
    }

    public void setTagText(String tagText) {
        this.tagText = tagText;
    }

    public String getGameID() {
        return gameID;
    }

    public String getTagDate() {
        return tagDate;
    }

    public String getTagText() {
        return tagText;
    }

    public String getTagTitle() {
        return tagTitle;
    }


}
