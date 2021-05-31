package com.RiceTree.tcp.server;

import java.io.*;
import java.net.*;
import java.util.concurrent.ConcurrentHashMap;



public class Server {

    //建立一个线程安全的哈希表
    //用于保存服务器当前连接的所有客户端ID和socket
    private ConcurrentHashMap<Integer,Socket> clientSocketMap=new ConcurrentHashMap<Integer, Socket>();
    private ConcurrentHashMap<String, Session> sessionHashmap=new ConcurrentHashMap<String, Session>();
    //服务器用于监听socket连接请求的serverSocket
    private static ServerSocket serverSocket=null;

    //服务器单例模式
    private static Server server=new Server();
    private Server(){}
    public static Server getServer() {
        return server;
    }


    //服务器方法
    public Socket getSocketByID(int ID){
        return clientSocketMap.getOrDefault(ID,null);
    }

    public ConcurrentHashMap<String, Session> getSessionHashmap() {
        return sessionHashmap;
    }

    public Session getSessionByClientID(int ID1,int ID2){
        String ID01=ID1+""+ID2;
        String ID02=ID2+""+ID1;
        Session session;
        session=this.sessionHashmap.getOrDefault(ID01,null);
        if(session==null){
            session=this.sessionHashmap.getOrDefault(ID02,null);
        }
        return session;

    }

    public void addSocket(int ID, Socket socket){
        clientSocketMap.put(ID,socket);
    }

    public void startListen(int port) throws IOException {
        serverSocket=new ServerSocket(port);
        Thread socketAdder=new Thread(new SocketAdder());
        socketAdder.start();

    }

    public ConcurrentHashMap<Integer, Socket> getClientSocketMap() {
        return clientSocketMap;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }



    public static void main(String[] args) throws IOException {
        try{
            Server serverTest=Server.getServer();
            serverTest.startListen(8080);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
