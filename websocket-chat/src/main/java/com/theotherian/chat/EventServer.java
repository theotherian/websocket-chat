package com.theotherian.chat;

import java.net.URL;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.resource.FileResource;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.websocket.server.WebSocketHandler;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

public class EventServer {
  public static void main(String[] args) throws Exception {
    Server server = new Server(8080);
    HandlerList handlerList = new HandlerList();
    
    // init webapp
    WebAppContext contextHandler = new WebAppContext(handlerList, "Chat Webapp", "/chat");
    URL homepage = EventServer.class.getResource("/index.html");
    Resource homepageResource = new FileResource(homepage);
    contextHandler.setBaseResource(homepageResource);
    ServletContextHandler socketHandler = new ServletContextHandler(handlerList, "/events");
    
    // init web socket
    WebSocketHandler wsHandler = new WebSocketHandler() {
      @Override
      public void configure(WebSocketServletFactory factory) {
//        WebSocketCreator creator;
//        factory.setCreator(creator);
        factory.register(ChatSocket.class);
      }
    };
    socketHandler.setHandler(wsHandler);
    server.setHandler(handlerList);
    server.start();
    server.join();
  }
}