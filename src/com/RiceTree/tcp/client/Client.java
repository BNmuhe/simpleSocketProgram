package com.RiceTree.tcp.client;

import com.RiceTree.tcp.message.Message;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;



public class Client{




//    webSocket用于客户端与前端进行交互





//    socket用于与服务器进行数据传输
    private Message messageSend=new Message();
    private int ID;
    private String content;
    private int targetID;
    private int type;

    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    private OutputStream outputStream;
    private Socket socket;

    public Socket getSocket() {
        return socket;
    }

    public void initialize(int ID, int targetID){
        this.ID=ID;
        this.targetID=targetID;
    }

    //建立客户端到服务器的会话，同时初始化IO流，并将自身信息提交到服务器,并显示是否连接成功
    public void startSession() throws IOException {
        socket=new Socket("192.168.89.87",8888);
        outputStream=socket.getOutputStream();
        //对消息进行设置
        setType(1);
        sendMessage();
    }

    public void inputContent() throws IOException {
        Scanner scanner=new Scanner(System.in);
        type=4;
        while(true){
            content=scanner.nextLine();
            sendMessage();
        }
    }

    //客户端发送消息的内容
    public void sendMessage() throws IOException {
        messageSend.setMessage(type,ID,targetID,simpleDateFormat.format(new Date()),content);
        outputStream.write((messageSend.toJSONString()).getBytes());
    }
    //建立一个线程用于接收消息
    public void buildMessageListener(){
        ClientMessageListener clientMessageAccept=new ClientMessageListener();
        clientMessageAccept.initialize(this);
        Thread messageAcceptThread=new Thread(clientMessageAccept);
        messageAcceptThread.start();
    }

    //向服务器发送客户端之间的连接请求,并且返回显示的结果
    public void buildLinkTo() throws IOException{
        //发送连接请求
        type=2;
        //ID不变
        setTargetID(1002);
        content="";

        sendMessage();

    }

    public int getID() {
        return ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }
    public void setType(int type) {
        this.type = type;
    }
    public void setTargetID(int targetID) {
        this.targetID = targetID;
    }


    public static void main(String[] args) throws IOException {
        Client client=new Client();

        client.initialize(1001,1002);


        try{
            //连接到数据库
            client.startSession();

            //创建一个用于接受消息的线程
            client.buildMessageListener();

            //建立客户端之间的会话
            client.buildLinkTo();

            client.inputContent();

        }catch (IOException e){
            e.printStackTrace();
        }
        finally {
            client.getSocket().close();
        }
    }

}




