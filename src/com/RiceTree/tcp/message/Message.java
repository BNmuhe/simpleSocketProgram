package com.RiceTree.tcp.message;

import com.alibaba.fastjson.JSON;

public class Message {
    //         0:系统消息
    //type类型：1：添加socket到服务器，type+sendID
    //         2：建立session，type+sendID+acceptID
    //         3：删除session，type+sendID+acceptID
    //         4：聊天信息，type+time+sendID+acceptID+content
    private int type;
    private int sendID;
    private int acceptID;
    private String time;
    private String content;

    public void setType(int type) {
        this.type = type;
    }

    public void setAcceptID(int acceptID) {
        this.acceptID = acceptID;
    }

    public void setSendID(int sendID) {
        this.sendID = sendID;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public int getAcceptID() {
        return acceptID;
    }

    public int getSendID() {
        return sendID;
    }

    public String getContent() {
        return content;
    }

    public String getTime() {
        return time;
    }


    public void setMessage(int type,int sendID,int acceptID,String time,String content){
        setType(type);
        setSendID(sendID);
        setAcceptID(acceptID);
        setTime(time);
        setContent(content);
    }

    public String toJSONString(){
        return JSON.toJSONString(this);
    }
    public Message parseObject(String jsonString){
        return JSON.parseObject(jsonString,Message.class);
    }


}
