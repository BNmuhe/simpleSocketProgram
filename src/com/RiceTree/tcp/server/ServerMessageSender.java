package com.RiceTree.tcp.server;

import com.RiceTree.tcp.message.Message;

import java.io.IOException;
import java.io.OutputStream;

public class ServerMessageSender {
    public static void sendMessage(Message message, OutputStream outputStream) throws IOException {
        outputStream.write(message.toJSONString().getBytes());
    }

}
