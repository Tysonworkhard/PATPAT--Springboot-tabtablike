package com.app.taptap.service;

import com.app.taptap.mapper.ChatMapper;
import com.app.taptap.pojo.*;
import com.app.taptap.utils.DateChange;
import com.app.taptap.utils.GenerateUID;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class WeChatService {
    @Resource
    private ChatMapper chatMapper;
    @Resource
    private ImageService imageService;
    public List<UserChatRoom> modifyProfile(List<UserChatRoom> userChatRooms){
        for (UserChatRoom userChatRoom : userChatRooms){
            //解密图像
            userChatRoom.setProfile(imageService.changePictureAddress(userChatRoom.getProfile()));
            RequestMessage requestMessage = chatMapper.getLatestMessageByRoomID(userChatRoom.getRoomID());
            if (!Objects.equals(requestMessage, null)){
                userChatRoom.setSenderID(requestMessage.getSenderUID());
                userChatRoom.setTime(requestMessage.getTime());
                userChatRoom.setContent(requestMessage.getContent());
            }
        }

        return userChatRooms;
    }
    public List<User> modifyProfile_user(List<User> users){
        for (User user : users){
            //解密图像
            user.setProfile(imageService.changePictureAddress(user.getProfile()));
        }
        return users;
    }
    public List<Message> modifyProfile_message(List<Message> messages){
        int cnt = 0;
        for (Message message : messages){
            //解密图像
            message.setProfile(imageService.changePictureAddress(message.getProfile()));
            DateChange dateChange = new DateChange();
            messages.get(cnt).setTimeSelector(dateChange.parseDate(message.getTime()));
            cnt++;
        }
        Date currentTime = new Date();

        messages.sort(new TimeComparator(currentTime));
        return messages;
    }
    public boolean isSendApplication(String UID, String friendID){
        if (Objects.equals(chatMapper.getRoomID(UID, friendID), null) && Objects.equals(chatMapper.getRoomID(friendID, UID), null)){
            GenerateUID generateUID = new GenerateUID();
            String roomID = generateUID.generateUid();
            return chatMapper.addFriends_waitAgree(UID, friendID, roomID) != 0;
        }else if(!Objects.equals(chatMapper.getRoomID(friendID, UID), null)){
            if (chatMapper.AgreeToBeFriend(friendID, UID) != 0){
                return agreeFriendApplication(friendID, UID);
            }else {
                return false;
            }
        }else {
            return true;
        }
    }

    public boolean agreeFriendApplication(String UID, String friendID){
        String roomID = chatMapper.getRoomID(UID, friendID).getRoomID();
        return chatMapper.AgreeToBeFriend_createRelative(UID, friendID, roomID) != 0;
    }

    public class TimeComparator implements Comparator<Message> {
        private Date currentTime;

        public TimeComparator(Date currentTime) {
            this.currentTime = currentTime;
        }

        @Override
        public int compare(Message message1, Message message2) {
            long diff1 = Math.abs(currentTime.getTime() - message1.getTimeSelector().getTime());
            long diff2 = Math.abs(currentTime.getTime() - message2.getTimeSelector().getTime());

            return Long.compare(diff1, diff2);
        }
    }
}
