package com.app.taptap.mapper;

import com.app.taptap.pojo.Game;
import com.app.taptap.pojo.PersonInfoChangeRequest;
import com.app.taptap.pojo.Tag;
import com.app.taptap.pojo.User;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface PersonInfoMapper {
    User getAllUserInfo(String UID);
    List<Game> getUserAllGame(String UID);
    List<Tag> getUserAllLikeTag(String UID);
    Game getGameInfo(Game game);
    Tag getTagInfo(Tag tag);
    //7.1
    User getUserUIDByPhoneNum(String phoneNumber);//通过电话号码查出UID
    int updateUserInfoByUID(String UID, PersonInfoChangeRequest personInfoChangeRequest);//修改PersonInfoChangeRequest里面的数据类型

    int updateUserProfile(String UID, String ProfileAddress);

}
