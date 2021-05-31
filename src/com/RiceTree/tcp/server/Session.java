package com.RiceTree.tcp.server;

import com.RiceTree.tcp.message.Message;


import java.io.IOException;
import java.io.OutputStream;

public class Session{
    private Server server=Server.getServer();
    private String sessionID;
    private int sendID;
    private int acceptID;
    private Message message=new Message();
    private OutputStream outputStream;

    public void initialize(int sendID,int acceptID,String sessionID) throws IOException {
        this.acceptID=acceptID;
        this.sendID=sendID;
        this.sessionID=sessionID;
    }
    public String getSessionID() {
        return sessionID;
    }
    public int getSendID() {
        return sendID;
    }
    public void exchangeID(){
        int temp=this.acceptID;
        this.acceptID=this.sendID;
        this.sendID=temp;
    }
    public void sendMessage(Message message) throws IOException {
        System.out.println("log:消息转发:  From"+sendID+"To"+acceptID);
        outputStream=server.getSocketByID(this.acceptID).getOutputStream();
        ServerMessageSender.sendMessage(message,outputStream);
    }

}
