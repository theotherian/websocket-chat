package com.theotherian.chat;

import java.io.File;
import java.net.URI;
import java.net.URL;

import javax.websocket.server.ServerContainer;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.util.resource.FileResource;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;
import org.eclipse.jetty.websocket.server.WebSocketHandler;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

public class EventServer {
  public static void main(String[] args) throws Exception {
//    Server server = new Server();
//    ServerConnector connector = new ServerConnector(server);
//    connector.setPort(8080);
//    server.addConnector(connector);
//
//    // Setup the basic application "context" for this application at "/"
//    // This is also known as the handler tree (in jetty speak)
//    ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
//    context.setContextPath("/");
//    server.setHandler(context);
//
//    try {
//      // Initialize javax.websocket layer
//      ServerContainer wscontainer = WebSocketServerContainerInitializer.configureContext(context);
//
//      // Add WebSocket endpoint to javax.websocket layer
//      wscontainer.addEndpoint(EventSocket.class);
//
//      server.start();
//      server.dump(System.err);
//      server.join();
//    } catch (Throwable t) {
//      t.printStackTrace(System.err);
//    }
    Server server = new Server(8080);
    HandlerList handlerList = new HandlerList();
    WebSocketHandler wsHandler = new WebSocketHandler() {
        @Override
        public void configure(WebSocketServletFactory factory) {
//          WebSocketCreator creator;
//          factory.setCreator(creator);
          factory.register(ChatSocket.class);
        }
    };
    WebAppContext contextHandler = new WebAppContext(handlerList, "Chat Webapp", "/chat");
    URL homepage = EventServer.class.getResource("/index.html");
//    URI homepage = new File("index.html").getAbsoluteFile().toURI();
    Resource homepageResource = new FileResource(homepage);
    contextHandler.setBaseResource(homepageResource);
//    ServletHandler servletHandler;
//    contextHandler.setServletHandler(servletHandler);
    ServletContextHandler socketHandler = new ServletContextHandler(handlerList, "/events");
//    handlerList.addHandler(wsHandler);
    socketHandler.setHandler(wsHandler);
    server.setHandler(handlerList);
    server.start();
    server.join();
  }
}