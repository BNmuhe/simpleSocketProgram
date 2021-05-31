package com.RiceTree.tcp.client;

import com.RiceTree.tcp.message.Message;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ClientMessageListener implements Runnable{
    private Client client;
    private Socket socket;
    private InputStream inputStream;
    private Message messageAccept=new Message();
    private byte[] bytes=new byte[1024];



    public void initialize(Client client){
        this.client=client;
        socket=this.client.getSocket();
    }

    public void acceptMessage() throws IOException{

        String buffer=new String(bytes,0,inputStream.read(bytes));
        messageAccept=messageAccept.parseObject(buffer);

        if(messageAccept.getType()==0){//系统消息
            System.out.println(messageAccept.getContent());
        }else {
            System.out.println( messageAccept.getTime()+"-->"+client.getID()+"接收到来自"+messageAccept.getSendID()+"的消息:"+messageAccept.getContent());
        }


    }

    @Override
    public void run() {
        try {
            inputStream=socket.getInputStream();
            while (true){
                acceptMessage();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
