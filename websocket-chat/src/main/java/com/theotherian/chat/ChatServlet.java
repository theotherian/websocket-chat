package com.theotherian.chat;

import javax.servlet.annotation.WebServlet;

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

@WebServlet(name = "ChatServlet", urlPatterns = "/events")
public class ChatServlet extends WebSocketServlet {

  @Override
  public void configure(WebSocketServletFactory factory) {
    factory.register(ChatSocket.class);
  }

}
