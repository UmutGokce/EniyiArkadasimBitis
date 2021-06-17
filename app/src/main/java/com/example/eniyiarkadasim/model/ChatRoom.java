package com.example.eniyiarkadasim.model;

import android.text.Editable;

import java.util.ArrayList;
import java.util.List;

public class ChatRoom {

    private String chatName;
    private String creatorId;
    private String level;
    private String roomId;
     List<ChatMsg> chat_room_messages = new ArrayList<ChatMsg>();
    public ChatRoom() {
    }

    public List<ChatMsg> getChat_room_messages() {
        return chat_room_messages;
    }

    public void setChat_room_messages(List<ChatMsg> chat_room_messages) {
        this.chat_room_messages = chat_room_messages;
    }

    public ChatRoom(String chatName, String creatorId, String level, String roomId, List<ChatMsg> chat_room_messages) {
        this.chatName = chatName;
        this.creatorId = creatorId;
        this.level = level;
        this.roomId = roomId;
        this.chat_room_messages = chat_room_messages;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
}
