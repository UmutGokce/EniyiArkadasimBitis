package com.example.eniyiarkadasim.model;

public class ChatMsg {


    private String message;
    private String userId;
    private String timeStamp;
    private String pp;
    private String name;

    public ChatMsg() {
    }

    public ChatMsg(String message, String userId, String timeStamp, String pp, String name) {
        this.message = message;
        this.userId = userId;
        this.timeStamp = timeStamp;
        this.pp = pp;
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getPp() {
        return pp;
    }

    public void setPp(String pp) {
        this.pp = pp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
