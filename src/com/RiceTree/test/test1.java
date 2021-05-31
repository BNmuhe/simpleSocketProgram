package com.RiceTree.test;

import com.RiceTree.tcp.client.ClientMessageListener;
import com.RiceTree.tcp.client.Client;

import java.io.IOException;

public class test1 {

    public static void main(String[] args) throws IOException {
        Client client=new Client();
        client.initialize(1003,1001);


        try{
            client.startSession();

            //创建一个用于接受消息的线程
            ClientMessageListener clientMessageAccept=new ClientMessageListener();
            Thread messageAcceptThread=new Thread(clientMessageAccept);
            messageAcceptThread.start();

            //建立客户端之间的会话


            client.inputContent();

        }catch (IOException e){
            e.printStackTrace();
        }
        finally {
            client.getSocket().close();
        }






    }

}
