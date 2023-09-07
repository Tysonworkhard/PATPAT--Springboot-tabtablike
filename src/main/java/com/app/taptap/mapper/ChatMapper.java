package com.app.taptap.mapper;

import com.app.taptap.pojo.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMapper {
    List<UserChatRoom> getFriends(String UID);//通过UID拿取其好友的UID，名称和头像和roomID
    List<User> searchFriends(String username);//头像、姓名、ID
    int addFriends_waitAgree(String UID, String friendID, String roomID);
    List<User> getFriendApplicationList(String UID);//agree = 0和好友id
    int AgreeToBeFriend(String UID, String friendID);//变1
    UserChatRoom getRoomID(String UID, String friendID);
    int AgreeToBeFriend_createRelative(String UID, String friendID, String roomID);
    int disagreeFriendApplication(String UID, String friendID);
    int deleteFriend(String UID, String friendID);

    //信息存储
    User getFriendID(String roomID, String UID);
    RequestMessage getLatestMessageByRoomID(String roomID);
    int insertMessage(RequestMessage requestMessage, String friendID, String messageID);
    List<Message> getAllMessages(String roomID);//发送人的头像/UID和信息/roomID
}