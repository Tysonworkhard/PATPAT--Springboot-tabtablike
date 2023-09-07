package com.app.taptap.controller;

import com.app.taptap.mapper.ChatMapper;
import com.app.taptap.pojo.RequestMessage;
import com.app.taptap.pojo.ResponseMessage;
import com.app.taptap.utils.GenerateUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
public class WebSocketController {
//    @Autowired
//    private SimpMessagingTemplate messagingTemplate;
//    @Resource
//    private ChatMapper chatMapper;

//    @CrossOrigin
//    @MessageMapping("/chat")
    public void messageHandling(RequestMessage requestMessage) throws Exception {
//        String destination = "/topic/" + HtmlUtils.htmlEscape(requestMessage.getRoom());
//
//        String sender = HtmlUtils.htmlEscape(requestMessage.getSenderUID());  //htmlEscape  转换为HTML转义字符表示
//        String type = HtmlUtils.htmlEscape(requestMessage.getType());
//        String content = HtmlUtils.htmlEscape(requestMessage.getContent());
//        ResponseMessage response = new ResponseMessage(sender, type, content);
//        String friendID = chatMapper.getFriendID(requestMessage.getRoom(), requestMessage.getSenderUID()).getUID();
//        GenerateUID generateUID = new GenerateUID();
//        String messageID = generateUID.generateUid();
//        chatMapper.insertMessage(requestMessage, friendID, messageID);
//        messagingTemplate.convertAndSend(destination, response);
    }
}
