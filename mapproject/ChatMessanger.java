package com.example.shivam.mapproject;

import java.util.Date;

/**
 * Created by shivam on 3/22/2018.
 */

public class ChatMessanger {

    String messageText;
    String messageUser;
    long time;

    public ChatMessanger(String messageText, String messageUser) {
        this.messageText = messageText;
        this.messageUser = messageUser;
        this.time=new Date().getTime();
    }

    public ChatMessanger() {
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageUser() {
        return messageUser;
    }

    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
