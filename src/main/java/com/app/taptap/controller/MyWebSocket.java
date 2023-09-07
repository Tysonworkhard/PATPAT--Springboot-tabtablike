package com.app.taptap.controller;

import com.app.taptap.mapper.ChatMapper;
import com.app.taptap.pojo.RequestMessage;
import com.app.taptap.pojo.SocketMessage;
import com.app.taptap.utils.GenerateUID;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(value = "/websocket/{UID}")
@Component
@Slf4j
public class MyWebSocket {
    /*@Autowired
    private ChatMapper chatMapper;*/
    private static ApplicationContext applicationContext;

    public static void setApplicationContext(ApplicationContext context) {
        applicationContext = context;
    }
    //用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<MyWebSocket> webSocketSet = new CopyOnWriteArraySet<MyWebSocket>();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    private String UID;

    //用来记录sessionId和该session进行绑定
    private static Map<String,Session> map = new HashMap<String, Session>();
    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("UID") String UID) {
        this.session = session;
        this.UID=UID;

        map.put(UID, session);

        webSocketSet.add(this);     //加入set中
        log.info("有新连接加入:"+UID+",当前在线人数为" + webSocketSet.size());
        this.session.getAsyncRemote().sendText("恭喜"+UID+"成功连接上WebSocket-->当前在线人数为："+webSocketSet.size());
    }
    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  //从set中删除
        log.info("有一连接关闭！当前在线人数为" + webSocketSet.size());
    }
    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息*/
    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("来自客户端的消息-->"+UID+": " + message);

        //从客户端传过来的数据是json数据，所以这里使用jackson进行转换为SocketMsg对象，
        // 然后通过socketMsg的type进行判断是单聊还是群聊，进行相应的处理:
        ObjectMapper objectMapper = new ObjectMapper();
        SocketMessage socketMsg;

        try {
            socketMsg = objectMapper.readValue(message, SocketMessage.class);
            //将消息存进数据库
            GenerateUID generateUID = new GenerateUID();
            String messageID = generateUID.generateUid();
            RequestMessage requestMessage = new RequestMessage();
            requestMessage.setSenderUID(socketMsg.getFromUser());
            requestMessage.setContent(socketMsg.getMsg());
            requestMessage.setRoom(socketMsg.getRoomID());
            requestMessage.setTime(socketMsg.getTime());
            if ( applicationContext.getBean(ChatMapper.class).insertMessage(requestMessage, socketMsg.getToUser(), messageID) != 0){
                if(socketMsg.getType() == 1){
                    //单聊.需要找到发送者和接受者.
                    Session fromSession = map.get(socketMsg.getFromUser());
                    Session toSession = map.get(socketMsg.getToUser());
                    //发送给接受者.
                    if(toSession != null){
                        //发送给发送者.
                        fromSession.getAsyncRemote().sendText(UID+"@"+socketMsg.getMsg());
                        toSession.getAsyncRemote().sendText(UID+"@"+socketMsg.getMsg());
                    }else{
                        //发送给发送者.
//                        RemoteEndpoint.Async remoteEndpoint = fromSession.getAsyncRemote();
                        fromSession.getAsyncRemote().sendText(UID+"@"+socketMsg.getMsg());
//                        remoteEndpoint.sendText(UID+"@"+socketMsg.getMsg());

                    }
                }else{
                    //群发消息
                    broadcast(UID+": "+socketMsg.getMsg());
                }
            }else {
                Session fromSession = map.get(socketMsg.getFromUser());
                fromSession.getAsyncRemote().sendText("系统消息：系统繁忙，请重试！");
            }



        } catch (IOException e) {
            e.printStackTrace();
        }


//        //群发消息
//        broadcast(nickname+": "+message);
    }
    /**
     * 发生错误时调用
     *
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.info("发生错误");
        error.printStackTrace();
    }
    /**
     * 群发自定义消息
     * */
    public  void broadcast(String message){
        for (MyWebSocket item : webSocketSet) {
            //this.session.getBasicRemote().sendText(message);
            item.session.getAsyncRemote().sendText(message);//异步发送消息.
        }
    }
}
