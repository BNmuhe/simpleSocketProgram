package com.RiceTree.test;

import com.RiceTree.tcp.client.Client;

import java.io.IOException;

public class test {

    public static void main(String[] args) throws IOException {
        Client client=new Client();
        client.initialize(1002,1001);
        try{
            client.startSession();

            //创建一个用于接受消息的线程
            client.buildMessageListener();

            client.inputContent();

        }catch (IOException e){
            e.printStackTrace();
        }
        finally {
            client.getSocket().close();
        }

    }

}
