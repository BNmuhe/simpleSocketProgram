package com.RiceTree.tcp.server;

import com.RiceTree.tcp.message.Message;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SocketAdder implements Runnable{

    private Message message=new Message();
    private String buffer;
    private byte[] bytes=new byte[1024];
    private Server server=Server.getServer();
    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;


    private void acceptSocket() throws IOException {
        socket=server.getServerSocket().accept();
        inputStream=socket.getInputStream();
        outputStream=socket.getOutputStream();
        buffer=new String(bytes,0,inputStream.read(bytes));
        message= message.parseObject(buffer);

        if(message.getType()==1){
            System.out.println("log:"+message.getSendID()+"成功连接服务器");
            server.addSocket(message.getSendID(),socket);

            //建立对该socket的监听
            ServerMessageListener serverMessageListener=new ServerMessageListener();
            serverMessageListener.initialization(socket);
            Thread thread=new Thread(serverMessageListener);
            thread.start();

            message.setType(0);
            message.setAcceptID(message.getSendID());
            message.setSendID(0);
            message.setContent("系统反馈：您以连接服务器.");


            outputStream.write((message.toJSONString()).getBytes());
        }
    }



    @Override
    public void run() {
        try {
            while (true){
                acceptSocket();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
