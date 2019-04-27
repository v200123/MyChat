package com.coffee_just.mychat.bean;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

public class Message extends LitePalSupport implements Serializable {
    public static final int TYPE_RECEIVED = 0;
    public static final int TYPE_SENT = 1;
    private String content;


    public String getFromUUID() {
        return fromUUID;
    }

    public void setFromUUID(String fromUUID) {
        this.fromUUID = fromUUID;
    }

    private String fromUUID;
    private int type;
    public Message(String content, int type) {
        System.out.println();
        this.content = content;
        this.type = type;
    }
    public String getContent() {
        return content;
    }
    public int getType() {
        return type;
    }
}
