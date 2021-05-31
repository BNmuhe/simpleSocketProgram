package com.RiceTree.tcp.server;

import com.RiceTree.tcp.message.Message;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ServerMessageListener implements Runnable{
    private Server server=Server.getServer();
    private InputStream inputStream;
    private Message message=new Message();
    private byte[] bytes=new byte[1024];
    private String buffer;
    public void initialization(Socket socket) throws IOException {
        inputStream=socket.getInputStream();
    }
    //    socket用于与服务器进行数据传输
    public void messageAccept() throws IOException {
        buffer=new String(bytes,0,inputStream.read(bytes));
        message=message.parseObject(buffer);
    }

    private void messageTypeDispose() throws IOException {
        switch (message.getType()){
            case 2://建立session
                Session session=new Session();
                if(server.getClientSocketMap().getOrDefault(message.getAcceptID(),null)==null){
                    while(server.getClientSocketMap().getOrDefault(message.getAcceptID(),null)==null){}
                }
                session.initialize(message.getSendID(),message.getAcceptID(),message.getSendID()+""+message.getAcceptID());
                server.getSessionHashmap().put(session.getSessionID(),session);
                System.out.println("log: 会话建立成功，会话信息: 申请方:"+message.getSendID()+"接收方:"+message.getAcceptID());
                break;
            case 3://删除session
                server.getSessionHashmap().remove(server.getSessionByClientID(message.getSendID(),message.getAcceptID()).getSessionID());
                System.out.println("log: 会话删除成功，会话信息: 申请方:"+message.getSendID()+"接收方:"+message.getAcceptID());
                break;
            case 4://发送普通消息
                Session session1;
                session1= server.getSessionByClientID(message.getSendID(),message.getAcceptID());
                if(session1.getSendID()==message.getAcceptID()){
                    session1.exchangeID();
                }
                session1.sendMessage(message);

                break;
            default://错误消息处理
                break;
        }
    }

    @Override
    public void run() {
        try {
            while(true){
                //获取消息
                messageAccept();
                //消息处理
                messageTypeDispose();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
