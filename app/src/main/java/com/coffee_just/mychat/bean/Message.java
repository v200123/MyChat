package com.coffee_just.mychat.bean;

import java.io.Serializable;
import java.util.UUID;

public class Message implements Serializable {
    public static final int TYPE_RECEIVED = 0;
    public static final int TYPE_SENT = 1;
    private String content;
    private UUID mUUID;
    private int type;
    public Message(String content, int type) {
        System.out.println();
        this.content = content;
        this.type = type;
        mUUID = User.InstanceUser().getId();
    }
    public String getContent() {
        return content;
    }
    public int getType() {
        return type;
    }
}
