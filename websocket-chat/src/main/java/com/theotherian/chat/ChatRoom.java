package com.theotherian.chat;

import java.util.ArrayList;
import java.util.List;

public class ChatRoom {
  private static final ChatRoom INSTANCE = new ChatRoom();

  public static ChatRoom getInstance() {
    return INSTANCE;
  }

  private List<ChatSocket> members = new ArrayList<>();

  public void join(ChatSocket socket) {
    members.add(socket);
  }

  public void part(ChatSocket socket) {
    members.remove(socket);
  }

  public void writeAllMembers(String message) {
    for (ChatSocket member : members) {
      member.session.getRemote().sendStringByFuture(message);
    }
  }

  public void writeSpecificMember(String memberName, String message) {
    ChatSocket member = findMemberByName(memberName);
    member.session.getRemote().sendStringByFuture(message);
  }

  public ChatSocket findMemberByName(String memberName) {
    // FIXME
    return members.get(0);
  }
}