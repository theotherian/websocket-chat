package com.theotherian.chat;


import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketException;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

@WebSocket
public class ChatSocket {
    public Session session;

    @OnWebSocketConnect
    public void onConnect(Session session) {
        this.session = session;
        InetSocketAddress remoteAddress = session.getRemoteAddress();
        InetAddress address = remoteAddress.getAddress();
        try {
          NetworkInterface networkInterface = NetworkInterface.getByInetAddress(address);
          byte[] mac = networkInterface.getHardwareAddress();
          if (mac != null) {
            StringBuilder macAddress = new StringBuilder();
            for (int i = 0; i < mac.length; i++) {
              macAddress.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));    
            }
            System.out.println(macAddress.toString());
          }
        } catch (SocketException e) {
          e.printStackTrace();
        }
        ChatRoom.getInstance().join(this);
    }

    @OnWebSocketMessage
    public void onText(String message) {
      System.out.println("Message received: " + message);
        ChatRoom.getInstance().writeAllMembers("Hello all");
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        ChatRoom.getInstance().part(this);
    }
}